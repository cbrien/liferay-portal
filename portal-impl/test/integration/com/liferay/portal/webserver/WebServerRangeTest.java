/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.webserver;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.webdav.methods.Method;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Alexander Chow
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class WebServerRangeTest extends BaseWebServerTestCase {

	@Test
	public void testBasic() throws Exception {
		testRange(null);
	}

	@Test
	public void testMultipartRange() throws Exception {
		String[] ranges = {"0-9", "25-25", "30-49", "70-79"};

		String rangeHeader = "bytes=";

		for (int i = 0; i < ranges.length; i++) {
			rangeHeader += ranges[i];

			if (i != (ranges.length - 1)) {
				rangeHeader += StringPool.COMMA;
			}
		}

		MockHttpServletResponse response = testRange(rangeHeader);

		String contentType = response.getContentType();

		Assert.assertTrue(contentType.startsWith(_BOUNDARY_PREFACE));

		String boundary = contentType.substring(_BOUNDARY_PREFACE.length());

		String responseBody = response.getContentAsString();

		Assert.assertTrue(
			responseBody.startsWith("\r\n--" + boundary + "\r\n"));
		Assert.assertTrue(responseBody.endsWith("--" + boundary + "--\r\n"));

		String[] responseBodies = StringUtil.split(responseBody, boundary);

		Assert.assertEquals(ranges.length + 2, responseBodies.length);
		Assert.assertEquals(StringPool.DOUBLE_DASH, responseBodies[0]);
		Assert.assertEquals(
			StringPool.DOUBLE_DASH, responseBodies[ranges.length + 1]);

		for (int i = 0; i < ranges.length; i++) {
			String[] lines = StringUtil.split(
				responseBodies[i + 1], StringPool.RETURN_NEW_LINE);

			Assert.assertEquals("Content-Type: text/plain", lines[0]);
			Assert.assertEquals(
				"Content-Range: bytes " + ranges[i] + "/80", lines[1]);
			Assert.assertTrue(Validator.isNull(lines[2]));

			String[] rangePair = StringUtil.split(ranges[i], StringPool.DASH);
			int start = GetterUtil.getInteger(rangePair[0]);
			int end = GetterUtil.getInteger(rangePair[1]);

			byte[] bytes = ArrayUtil.subset(
				_SAMPLE_DATA.getBytes(), start, end + 1);

			Assert.assertArrayEquals(bytes, lines[3].getBytes("UTF-8"));
			Assert.assertEquals(StringPool.DOUBLE_DASH, lines[4]);
		}
	}

	@Test
	public void testSingleRangeByte() throws Exception {
		MockHttpServletResponse response = testRange("bytes=10-10");

		Assert.assertEquals(
			"1", response.getHeader(HttpHeaders.CONTENT_LENGTH));
		Assert.assertEquals(
			"bytes 10-10/80", response.getHeader(HttpHeaders.CONTENT_RANGE));
		Assert.assertEquals("B", response.getContentAsString());
	}

	@Test
	public void testSingleRangeFirst() throws Exception {
		MockHttpServletResponse response = testRange("bytes=0-9");

		Assert.assertEquals(
			"10", response.getHeader(HttpHeaders.CONTENT_LENGTH));
		Assert.assertEquals(
			"bytes 0-9/80", response.getHeader(HttpHeaders.CONTENT_RANGE));
		Assert.assertEquals("A123456789", response.getContentAsString());
	}

	@Test
	public void testSingleRangeLast() throws Exception {
		MockHttpServletResponse response = testRange("bytes=70-79");

		Assert.assertEquals(
			"10", response.getHeader(HttpHeaders.CONTENT_LENGTH));
		Assert.assertEquals(
			"bytes 70-79/80", response.getHeader(HttpHeaders.CONTENT_RANGE));
		Assert.assertArrayEquals(
			_UNICODE_DATA.getBytes(), response.getContentAsByteArray());
	}

	@Override
	protected HttpServlet getServlet() {
		return new WebServerServlet() {

			@Override
			protected boolean isSupportsRangeHeader(String contentType) {
				return true;
			}

		};
	}

	protected MockHttpServletResponse testRange(String rangeHeader)
		throws Exception {

		String fileName = "Test Range.txt";

		FileEntry fileEntry = addFileEntry(
			parentFolder.getFolderId(), fileName, fileName,
			_SAMPLE_DATA.getBytes());

		String path =
			fileEntry.getGroupId() + "/" + fileEntry.getFolderId() + "/" +
				fileEntry.getTitle();

		Map<String, String> headers = new HashMap<String, String>();

		if (Validator.isNotNull(rangeHeader)) {
			headers.put(HttpHeaders.RANGE, rangeHeader);
		}

		MockHttpServletResponse response = service(
			Method.GET, path, headers, null, null, null);

		int status = response.getStatus();

		Assert.assertTrue(response.containsHeader(HttpHeaders.ACCEPT_RANGES));

		if (Validator.isNotNull(rangeHeader)) {
			Assert.assertEquals(HttpServletResponse.SC_PARTIAL_CONTENT, status);
		}
		else {
			Assert.assertEquals(HttpServletResponse.SC_OK, status);
		}

		String contentType = response.getContentType();

		if (Validator.isNotNull(rangeHeader) &&
			rangeHeader.contains(StringPool.COMMA)) {

			Assert.assertTrue(contentType.startsWith("multipart/byteranges"));
		}
		else {
			Assert.assertEquals(ContentTypes.TEXT_PLAIN, contentType);
		}

		return response;
	}

	private static final String _BOUNDARY_PREFACE =
		"multipart/byteranges; boundary=";

	private static final String _SAMPLE_DATA =
		"A123456789B123456789C123456789D123456789" +
			"E123456789F123456789G123456789" + WebServerRangeTest._UNICODE_DATA;

	private static final String _UNICODE_DATA = "H\u4e2d\u00e9\u00fc\u00f1";

}
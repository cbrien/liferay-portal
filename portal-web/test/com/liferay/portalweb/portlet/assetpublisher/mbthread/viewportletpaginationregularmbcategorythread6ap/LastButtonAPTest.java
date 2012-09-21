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

package com.liferay.portalweb.portlet.assetpublisher.mbthread.viewportletpaginationregularmbcategorythread6ap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class LastButtonAPTest extends BaseTestCase {
	public void testLastButtonAP() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Asset Publisher Test Page");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Last"),
			selenium.getText("//a[@class='last']"));
		selenium.clickAt("//a[@class='last']", RuntimeVariables.replace("Last"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("First"),
			selenium.getText("//a[@class='first']"));
		assertEquals(RuntimeVariables.replace("Previous"),
			selenium.getText("//a[@class='previous']"));
		assertTrue(selenium.isElementNotPresent("//a[@class='next']"));
		assertTrue(selenium.isElementNotPresent("//a[@class='last']"));
		assertEquals("3", selenium.getSelectedLabel("//select"));
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Asset Publisher Test Page");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select", RuntimeVariables.replace("2"));
		selenium.waitForPageToLoad("30000");
		assertEquals("2", selenium.getSelectedLabel("//select"));
		assertEquals(RuntimeVariables.replace("Last"),
			selenium.getText("//a[@class='last']"));
		selenium.clickAt("//a[@class='last']", RuntimeVariables.replace("Last"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("First"),
			selenium.getText("//a[@class='first']"));
		assertEquals(RuntimeVariables.replace("Previous"),
			selenium.getText("//a[@class='previous']"));
		assertTrue(selenium.isElementNotPresent("//a[@class='next']"));
		assertTrue(selenium.isElementNotPresent("//a[@class='last']"));
		assertEquals("3", selenium.getSelectedLabel("//select"));
	}
}
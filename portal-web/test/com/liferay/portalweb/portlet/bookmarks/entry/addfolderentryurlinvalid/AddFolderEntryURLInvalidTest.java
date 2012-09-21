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

package com.liferay.portalweb.portlet.bookmarks.entry.addfolderentryurlinvalid;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddFolderEntryURLInvalidTest extends BaseTestCase {
	public void testAddFolderEntryURLInvalid() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Bookmarks Test Page");
		selenium.clickAt("link=Bookmarks Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//a/strong", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//div[2]/ul/li[5]/a", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_28_name", RuntimeVariables.replace("Test Folder Entry"));
		selenium.type("_28_url", RuntimeVariables.replace("www.liferay.com"));
		selenium.type("_28_description",
			RuntimeVariables.replace("This is a test folder entry."));
		assertFalse(selenium.isTextPresent("Please enter a valid URL."));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		assertEquals(RuntimeVariables.replace("Please enter a valid URL."),
			selenium.getText("//div[@class='aui-form-validator-message url']"));
	}
}
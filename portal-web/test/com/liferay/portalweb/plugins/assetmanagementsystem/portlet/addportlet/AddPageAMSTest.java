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

package com.liferay.portalweb.plugins.assetmanagementsystem.portlet.addportlet;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPageAMSTest extends BaseTestCase {
	public void testAddPageAMS() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//li[@id='_145_addContent']/a/span"));
		selenium.mouseOver("//li[@id='_145_addContent']/a/span");
		selenium.waitForVisible("//a[@id='addPage']");
		assertEquals(RuntimeVariables.replace("Page"),
			selenium.getText("//a[@id='addPage']"));
		selenium.clickAt("//a[@id='addPage']", RuntimeVariables.replace("Page"));
		selenium.waitForVisible("//input[@type='text']");
		selenium.type("//input[@type='text']",
			RuntimeVariables.replace("Asset Management System Test Page"));
		selenium.clickAt("//button[@id='save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible("link=Asset Management System Test Page");
		selenium.clickAt("link=Asset Management System Test Page",
			RuntimeVariables.replace("Asset Management System Test Page"));
		selenium.waitForPageToLoad("30000");
	}
}
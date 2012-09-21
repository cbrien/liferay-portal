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

package com.liferay.portalweb.portal.controlpanel.organizations.organizationemailaddress.addorganizationemailaddress;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddOrganizationEmailAddressTest extends BaseTestCase {
	public void testAddOrganizationEmailAddress() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("Selenium"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Selenium"),
			selenium.getText("//a[2]/strong"));
		selenium.clickAt("//a[2]/strong", RuntimeVariables.replace("Selenium"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//a[@id='_125_additionalEmailAddressesLink']");
		selenium.clickAt("//a[@id='_125_additionalEmailAddressesLink']",
			RuntimeVariables.replace("Additional Email Addresses"));
		selenium.waitForVisible("//input[@id='_125_emailAddressAddress0']");
		selenium.type("//input[@id='_125_emailAddressAddress0']",
			RuntimeVariables.replace("Selenium@Selenium.com"));
		selenium.select("//select[@id='_125_emailAddressTypeId0']",
			RuntimeVariables.replace("label=Email Address"));
		selenium.clickAt("//input[@id='_125_emailAddressPrimary0']",
			RuntimeVariables.replace("Primary Button"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("Selenium@Selenium.com",
			selenium.getValue("//input[@id='_125_emailAddressAddress0']"));
		assertEquals("Email Address",
			selenium.getSelectedLabel(
				"//select[@id='_125_emailAddressTypeId0']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_125_emailAddressPrimary0']"));
	}
}
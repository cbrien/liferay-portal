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

package com.liferay.portal.kernel.format;

/**
 * @author Brian Wing Shun Chan
 * @author Manuel de la Peña
 */
public class PhoneNumberFormatWrapper implements PhoneNumberFormat {

	public PhoneNumberFormatWrapper(PhoneNumberFormat phoneNumberFormat) {
		_originalPhoneNumberFormat = phoneNumberFormat;
		_phoneNumberFormat = phoneNumberFormat;
	}

	public String format(String phoneNumber) {
		return _phoneNumberFormat.format(phoneNumber);
	}

	public void setPhoneNumberFormat(PhoneNumberFormat phoneNumberFormat) {
		if (phoneNumberFormat == null) {
			_phoneNumberFormat = _originalPhoneNumberFormat;
		}
		else {
			_phoneNumberFormat = phoneNumberFormat;
		}
	}

	public String strip(String phoneNumber) {
		return _phoneNumberFormat.strip(phoneNumber);
	}

	public boolean validate(String phoneNumber) {
		return _phoneNumberFormat.validate(phoneNumber);
	}

	private PhoneNumberFormat _originalPhoneNumberFormat;
	private PhoneNumberFormat _phoneNumberFormat;

}
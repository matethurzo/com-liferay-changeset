/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.changeset.rest.internal.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Daniel Kocsis
 */
@XmlRootElement(name = "errorResponse")
public class ErrorResponseDTO extends ResponseDTO {

	public ErrorResponseDTO() {
	}

	public ErrorResponseDTO(Exception exception) {
		_exception = exception;
	}

	@XmlElement(name = "message")
	public String getError() {
		return _exception.getLocalizedMessage();
	}

	@XmlElement(name = "details")
	public String getStackTrace() {
		return _exception.toString();
	}

	public void setError(Exception e) {
		_exception = e;
	}

	private Exception _exception;

}
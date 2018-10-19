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

import com.liferay.changeset.model.ChangesetCollection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Daniel Kocsis
 */
@XmlRootElement(name = "changeset")
public class ChangesetCollectionDTO {

	public static ChangesetCollectionDTO of(
		ChangesetCollection changesetCollection) {

		ChangesetCollectionDTO changesetCollectionDTO =
			new ChangesetCollectionDTO();

		changesetCollectionDTO._id =
			changesetCollection.getChangesetCollectionId();
		changesetCollectionDTO._name = changesetCollection.getName();

		return changesetCollectionDTO;
	}

	@XmlElement(name = "changesetId")
	public long getId() {
		return _id;
	}

	@XmlElement(name = "changesetName")
	public String getName() {
		return _name;
	}

	private ChangesetCollectionDTO() {
	}

	private long _id;
	private String _name;

}
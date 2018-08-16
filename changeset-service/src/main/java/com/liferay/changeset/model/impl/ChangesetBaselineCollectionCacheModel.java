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

package com.liferay.changeset.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.changeset.model.ChangesetBaselineCollection;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ChangesetBaselineCollection in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetBaselineCollection
 * @generated
 */
@ProviderType
public class ChangesetBaselineCollectionCacheModel implements CacheModel<ChangesetBaselineCollection>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ChangesetBaselineCollectionCacheModel)) {
			return false;
		}

		ChangesetBaselineCollectionCacheModel changesetBaselineCollectionCacheModel =
			(ChangesetBaselineCollectionCacheModel)obj;

		if (changesetBaselineCollectionId == changesetBaselineCollectionCacheModel.changesetBaselineCollectionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, changesetBaselineCollectionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{changesetBaselineCollectionId=");
		sb.append(changesetBaselineCollectionId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ChangesetBaselineCollection toEntityModel() {
		ChangesetBaselineCollectionImpl changesetBaselineCollectionImpl = new ChangesetBaselineCollectionImpl();

		changesetBaselineCollectionImpl.setChangesetBaselineCollectionId(changesetBaselineCollectionId);
		changesetBaselineCollectionImpl.setCompanyId(companyId);
		changesetBaselineCollectionImpl.setUserId(userId);

		if (userName == null) {
			changesetBaselineCollectionImpl.setUserName("");
		}
		else {
			changesetBaselineCollectionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			changesetBaselineCollectionImpl.setCreateDate(null);
		}
		else {
			changesetBaselineCollectionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			changesetBaselineCollectionImpl.setModifiedDate(null);
		}
		else {
			changesetBaselineCollectionImpl.setModifiedDate(new Date(
					modifiedDate));
		}

		if (name == null) {
			changesetBaselineCollectionImpl.setName("");
		}
		else {
			changesetBaselineCollectionImpl.setName(name);
		}

		changesetBaselineCollectionImpl.resetOriginalValues();

		return changesetBaselineCollectionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		changesetBaselineCollectionId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(changesetBaselineCollectionId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long changesetBaselineCollectionId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
}
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

import com.liferay.changeset.model.ChangesetBaselineEntry;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ChangesetBaselineEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetBaselineEntry
 * @generated
 */
@ProviderType
public class ChangesetBaselineEntryCacheModel implements CacheModel<ChangesetBaselineEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ChangesetBaselineEntryCacheModel)) {
			return false;
		}

		ChangesetBaselineEntryCacheModel changesetBaselineEntryCacheModel = (ChangesetBaselineEntryCacheModel)obj;

		if (changesetBaselineEntryId == changesetBaselineEntryCacheModel.changesetBaselineEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, changesetBaselineEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{changesetBaselineEntryId=");
		sb.append(changesetBaselineEntryId);
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
		sb.append(", changesetBaselineCollectionId=");
		sb.append(changesetBaselineCollectionId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", version=");
		sb.append(version);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ChangesetBaselineEntry toEntityModel() {
		ChangesetBaselineEntryImpl changesetBaselineEntryImpl = new ChangesetBaselineEntryImpl();

		changesetBaselineEntryImpl.setChangesetBaselineEntryId(changesetBaselineEntryId);
		changesetBaselineEntryImpl.setCompanyId(companyId);
		changesetBaselineEntryImpl.setUserId(userId);

		if (userName == null) {
			changesetBaselineEntryImpl.setUserName("");
		}
		else {
			changesetBaselineEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			changesetBaselineEntryImpl.setCreateDate(null);
		}
		else {
			changesetBaselineEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			changesetBaselineEntryImpl.setModifiedDate(null);
		}
		else {
			changesetBaselineEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		changesetBaselineEntryImpl.setChangesetBaselineCollectionId(changesetBaselineCollectionId);
		changesetBaselineEntryImpl.setClassNameId(classNameId);
		changesetBaselineEntryImpl.setClassPK(classPK);
		changesetBaselineEntryImpl.setVersion(version);

		changesetBaselineEntryImpl.resetOriginalValues();

		return changesetBaselineEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		changesetBaselineEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		changesetBaselineCollectionId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		version = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(changesetBaselineEntryId);

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

		objectOutput.writeLong(changesetBaselineCollectionId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeDouble(version);
	}

	public long changesetBaselineEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long changesetBaselineCollectionId;
	public long classNameId;
	public long classPK;
	public double version;
}
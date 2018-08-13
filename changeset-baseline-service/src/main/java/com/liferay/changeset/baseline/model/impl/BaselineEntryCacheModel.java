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

package com.liferay.changeset.baseline.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.changeset.baseline.model.BaselineEntry;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing BaselineEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see BaselineEntry
 * @generated
 */
@ProviderType
public class BaselineEntryCacheModel implements CacheModel<BaselineEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BaselineEntryCacheModel)) {
			return false;
		}

		BaselineEntryCacheModel baselineEntryCacheModel = (BaselineEntryCacheModel)obj;

		if (baselineEntryId == baselineEntryCacheModel.baselineEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, baselineEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{baselineEntryId=");
		sb.append(baselineEntryId);
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
		sb.append(", baselineInformationId=");
		sb.append(baselineInformationId);
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
	public BaselineEntry toEntityModel() {
		BaselineEntryImpl baselineEntryImpl = new BaselineEntryImpl();

		baselineEntryImpl.setBaselineEntryId(baselineEntryId);
		baselineEntryImpl.setCompanyId(companyId);
		baselineEntryImpl.setUserId(userId);

		if (userName == null) {
			baselineEntryImpl.setUserName("");
		}
		else {
			baselineEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			baselineEntryImpl.setCreateDate(null);
		}
		else {
			baselineEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			baselineEntryImpl.setModifiedDate(null);
		}
		else {
			baselineEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		baselineEntryImpl.setBaselineInformationId(baselineInformationId);
		baselineEntryImpl.setClassNameId(classNameId);
		baselineEntryImpl.setClassPK(classPK);
		baselineEntryImpl.setVersion(version);

		baselineEntryImpl.resetOriginalValues();

		return baselineEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		baselineEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		baselineInformationId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		version = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(baselineEntryId);

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

		objectOutput.writeLong(baselineInformationId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeDouble(version);
	}

	public long baselineEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long baselineInformationId;
	public long classNameId;
	public long classPK;
	public double version;
}
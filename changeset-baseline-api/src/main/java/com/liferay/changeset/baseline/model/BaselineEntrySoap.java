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

package com.liferay.changeset.baseline.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class BaselineEntrySoap implements Serializable {
	public static BaselineEntrySoap toSoapModel(BaselineEntry model) {
		BaselineEntrySoap soapModel = new BaselineEntrySoap();

		soapModel.setBaselineEntryId(model.getBaselineEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setBaselineInformationId(model.getBaselineInformationId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setVersion(model.getVersion());

		return soapModel;
	}

	public static BaselineEntrySoap[] toSoapModels(BaselineEntry[] models) {
		BaselineEntrySoap[] soapModels = new BaselineEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static BaselineEntrySoap[][] toSoapModels(BaselineEntry[][] models) {
		BaselineEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new BaselineEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new BaselineEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static BaselineEntrySoap[] toSoapModels(List<BaselineEntry> models) {
		List<BaselineEntrySoap> soapModels = new ArrayList<BaselineEntrySoap>(models.size());

		for (BaselineEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new BaselineEntrySoap[soapModels.size()]);
	}

	public BaselineEntrySoap() {
	}

	public long getPrimaryKey() {
		return _baselineEntryId;
	}

	public void setPrimaryKey(long pk) {
		setBaselineEntryId(pk);
	}

	public long getBaselineEntryId() {
		return _baselineEntryId;
	}

	public void setBaselineEntryId(long baselineEntryId) {
		_baselineEntryId = baselineEntryId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getBaselineInformationId() {
		return _baselineInformationId;
	}

	public void setBaselineInformationId(long baselineInformationId) {
		_baselineInformationId = baselineInformationId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public double getVersion() {
		return _version;
	}

	public void setVersion(double version) {
		_version = version;
	}

	private long _baselineEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _baselineInformationId;
	private long _classNameId;
	private long _classPK;
	private double _version;
}
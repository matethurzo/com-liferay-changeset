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

import com.liferay.changeset.baseline.model.BaselineInformation;
import com.liferay.changeset.baseline.model.BaselineInformationModel;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.sql.Types;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the BaselineInformation service. Represents a row in the &quot;BaselineInformation&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link BaselineInformationModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link BaselineInformationImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BaselineInformationImpl
 * @see BaselineInformation
 * @see BaselineInformationModel
 * @generated
 */
@ProviderType
public class BaselineInformationModelImpl extends BaseModelImpl<BaselineInformation>
	implements BaselineInformationModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a baseline information model instance should use the {@link BaselineInformation} interface instead.
	 */
	public static final String TABLE_NAME = "BaselineInformation";
	public static final Object[][] TABLE_COLUMNS = {
			{ "baselineInformationId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "name", Types.VARCHAR }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("baselineInformationId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE = "create table BaselineInformation (baselineInformationId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table BaselineInformation";
	public static final String ORDER_BY_JPQL = " ORDER BY baselineInformation.baselineInformationId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY BaselineInformation.baselineInformationId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.changeset.baseline.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.changeset.baseline.model.BaselineInformation"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.changeset.baseline.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.changeset.baseline.model.BaselineInformation"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.changeset.baseline.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.changeset.baseline.model.BaselineInformation"),
			true);
	public static final long NAME_COLUMN_BITMASK = 1L;
	public static final long BASELINEINFORMATIONID_COLUMN_BITMASK = 2L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.changeset.baseline.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.changeset.baseline.model.BaselineInformation"));

	public BaselineInformationModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _baselineInformationId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setBaselineInformationId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _baselineInformationId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return BaselineInformation.class;
	}

	@Override
	public String getModelClassName() {
		return BaselineInformation.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("baselineInformationId", getBaselineInformationId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long baselineInformationId = (Long)attributes.get(
				"baselineInformationId");

		if (baselineInformationId != null) {
			setBaselineInformationId(baselineInformationId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	@Override
	public long getBaselineInformationId() {
		return _baselineInformationId;
	}

	@Override
	public void setBaselineInformationId(long baselineInformationId) {
		_baselineInformationId = baselineInformationId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@Override
	public String getName() {
		if (_name == null) {
			return "";
		}
		else {
			return _name;
		}
	}

	@Override
	public void setName(String name) {
		_columnBitmask |= NAME_COLUMN_BITMASK;

		if (_originalName == null) {
			_originalName = _name;
		}

		_name = name;
	}

	public String getOriginalName() {
		return GetterUtil.getString(_originalName);
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			BaselineInformation.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public BaselineInformation toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (BaselineInformation)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		BaselineInformationImpl baselineInformationImpl = new BaselineInformationImpl();

		baselineInformationImpl.setBaselineInformationId(getBaselineInformationId());
		baselineInformationImpl.setCompanyId(getCompanyId());
		baselineInformationImpl.setUserId(getUserId());
		baselineInformationImpl.setUserName(getUserName());
		baselineInformationImpl.setCreateDate(getCreateDate());
		baselineInformationImpl.setModifiedDate(getModifiedDate());
		baselineInformationImpl.setName(getName());

		baselineInformationImpl.resetOriginalValues();

		return baselineInformationImpl;
	}

	@Override
	public int compareTo(BaselineInformation baselineInformation) {
		long primaryKey = baselineInformation.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BaselineInformation)) {
			return false;
		}

		BaselineInformation baselineInformation = (BaselineInformation)obj;

		long primaryKey = baselineInformation.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		BaselineInformationModelImpl baselineInformationModelImpl = this;

		baselineInformationModelImpl._setModifiedDate = false;

		baselineInformationModelImpl._originalName = baselineInformationModelImpl._name;

		baselineInformationModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<BaselineInformation> toCacheModel() {
		BaselineInformationCacheModel baselineInformationCacheModel = new BaselineInformationCacheModel();

		baselineInformationCacheModel.baselineInformationId = getBaselineInformationId();

		baselineInformationCacheModel.companyId = getCompanyId();

		baselineInformationCacheModel.userId = getUserId();

		baselineInformationCacheModel.userName = getUserName();

		String userName = baselineInformationCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			baselineInformationCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			baselineInformationCacheModel.createDate = createDate.getTime();
		}
		else {
			baselineInformationCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			baselineInformationCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			baselineInformationCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		baselineInformationCacheModel.name = getName();

		String name = baselineInformationCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			baselineInformationCacheModel.name = null;
		}

		return baselineInformationCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{baselineInformationId=");
		sb.append(getBaselineInformationId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", name=");
		sb.append(getName());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(25);

		sb.append("<model><model-name>");
		sb.append("com.liferay.changeset.baseline.model.BaselineInformation");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>baselineInformationId</column-name><column-value><![CDATA[");
		sb.append(getBaselineInformationId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = BaselineInformation.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			BaselineInformation.class, ModelWrapper.class
		};
	private long _baselineInformationId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private String _name;
	private String _originalName;
	private long _columnBitmask;
	private BaselineInformation _escapedModel;
}
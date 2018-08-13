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
import com.liferay.changeset.baseline.model.BaselineEntryModel;

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
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.sql.Types;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the BaselineEntry service. Represents a row in the &quot;BaselineEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link BaselineEntryModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link BaselineEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BaselineEntryImpl
 * @see BaselineEntry
 * @see BaselineEntryModel
 * @generated
 */
@ProviderType
public class BaselineEntryModelImpl extends BaseModelImpl<BaselineEntry>
	implements BaselineEntryModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a baseline entry model instance should use the {@link BaselineEntry} interface instead.
	 */
	public static final String TABLE_NAME = "BaselineEntry";
	public static final Object[][] TABLE_COLUMNS = {
			{ "baselineEntryId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "baselineInformationId", Types.BIGINT },
			{ "classNameId", Types.BIGINT },
			{ "classPK", Types.BIGINT },
			{ "version", Types.DOUBLE }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("baselineEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("baselineInformationId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("version", Types.DOUBLE);
	}

	public static final String TABLE_SQL_CREATE = "create table BaselineEntry (baselineEntryId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,baselineInformationId LONG,classNameId LONG,classPK LONG,version DOUBLE)";
	public static final String TABLE_SQL_DROP = "drop table BaselineEntry";
	public static final String ORDER_BY_JPQL = " ORDER BY baselineEntry.baselineEntryId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY BaselineEntry.baselineEntryId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.changeset.baseline.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.changeset.baseline.model.BaselineEntry"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.changeset.baseline.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.changeset.baseline.model.BaselineEntry"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = false;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.changeset.baseline.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.changeset.baseline.model.BaselineEntry"));

	public BaselineEntryModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _baselineEntryId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setBaselineEntryId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _baselineEntryId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return BaselineEntry.class;
	}

	@Override
	public String getModelClassName() {
		return BaselineEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("baselineEntryId", getBaselineEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("baselineInformationId", getBaselineInformationId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("version", getVersion());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long baselineEntryId = (Long)attributes.get("baselineEntryId");

		if (baselineEntryId != null) {
			setBaselineEntryId(baselineEntryId);
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

		Long baselineInformationId = (Long)attributes.get(
				"baselineInformationId");

		if (baselineInformationId != null) {
			setBaselineInformationId(baselineInformationId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Double version = (Double)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}
	}

	@Override
	public long getBaselineEntryId() {
		return _baselineEntryId;
	}

	@Override
	public void setBaselineEntryId(long baselineEntryId) {
		_baselineEntryId = baselineEntryId;
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
	public long getBaselineInformationId() {
		return _baselineInformationId;
	}

	@Override
	public void setBaselineInformationId(long baselineInformationId) {
		_baselineInformationId = baselineInformationId;
	}

	@Override
	public String getClassName() {
		if (getClassNameId() <= 0) {
			return "";
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	@Override
	public void setClassName(String className) {
		long classNameId = 0;

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		setClassNameId(classNameId);
	}

	@Override
	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	@Override
	public long getClassPK() {
		return _classPK;
	}

	@Override
	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	@Override
	public double getVersion() {
		return _version;
	}

	@Override
	public void setVersion(double version) {
		_version = version;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			BaselineEntry.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public BaselineEntry toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (BaselineEntry)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		BaselineEntryImpl baselineEntryImpl = new BaselineEntryImpl();

		baselineEntryImpl.setBaselineEntryId(getBaselineEntryId());
		baselineEntryImpl.setCompanyId(getCompanyId());
		baselineEntryImpl.setUserId(getUserId());
		baselineEntryImpl.setUserName(getUserName());
		baselineEntryImpl.setCreateDate(getCreateDate());
		baselineEntryImpl.setModifiedDate(getModifiedDate());
		baselineEntryImpl.setBaselineInformationId(getBaselineInformationId());
		baselineEntryImpl.setClassNameId(getClassNameId());
		baselineEntryImpl.setClassPK(getClassPK());
		baselineEntryImpl.setVersion(getVersion());

		baselineEntryImpl.resetOriginalValues();

		return baselineEntryImpl;
	}

	@Override
	public int compareTo(BaselineEntry baselineEntry) {
		long primaryKey = baselineEntry.getPrimaryKey();

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

		if (!(obj instanceof BaselineEntry)) {
			return false;
		}

		BaselineEntry baselineEntry = (BaselineEntry)obj;

		long primaryKey = baselineEntry.getPrimaryKey();

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
		BaselineEntryModelImpl baselineEntryModelImpl = this;

		baselineEntryModelImpl._setModifiedDate = false;
	}

	@Override
	public CacheModel<BaselineEntry> toCacheModel() {
		BaselineEntryCacheModel baselineEntryCacheModel = new BaselineEntryCacheModel();

		baselineEntryCacheModel.baselineEntryId = getBaselineEntryId();

		baselineEntryCacheModel.companyId = getCompanyId();

		baselineEntryCacheModel.userId = getUserId();

		baselineEntryCacheModel.userName = getUserName();

		String userName = baselineEntryCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			baselineEntryCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			baselineEntryCacheModel.createDate = createDate.getTime();
		}
		else {
			baselineEntryCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			baselineEntryCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			baselineEntryCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		baselineEntryCacheModel.baselineInformationId = getBaselineInformationId();

		baselineEntryCacheModel.classNameId = getClassNameId();

		baselineEntryCacheModel.classPK = getClassPK();

		baselineEntryCacheModel.version = getVersion();

		return baselineEntryCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{baselineEntryId=");
		sb.append(getBaselineEntryId());
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
		sb.append(", baselineInformationId=");
		sb.append(getBaselineInformationId());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", classPK=");
		sb.append(getClassPK());
		sb.append(", version=");
		sb.append(getVersion());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(34);

		sb.append("<model><model-name>");
		sb.append("com.liferay.changeset.baseline.model.BaselineEntry");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>baselineEntryId</column-name><column-value><![CDATA[");
		sb.append(getBaselineEntryId());
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
			"<column><column-name>baselineInformationId</column-name><column-value><![CDATA[");
		sb.append(getBaselineInformationId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classPK</column-name><column-value><![CDATA[");
		sb.append(getClassPK());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>version</column-name><column-value><![CDATA[");
		sb.append(getVersion());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = BaselineEntry.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			BaselineEntry.class, ModelWrapper.class
		};
	private long _baselineEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _baselineInformationId;
	private long _classNameId;
	private long _classPK;
	private double _version;
	private BaselineEntry _escapedModel;
}
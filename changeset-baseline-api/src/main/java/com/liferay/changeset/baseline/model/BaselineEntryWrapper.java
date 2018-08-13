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

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link BaselineEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BaselineEntry
 * @generated
 */
@ProviderType
public class BaselineEntryWrapper implements BaselineEntry,
	ModelWrapper<BaselineEntry> {
	public BaselineEntryWrapper(BaselineEntry baselineEntry) {
		_baselineEntry = baselineEntry;
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
	public Object clone() {
		return new BaselineEntryWrapper((BaselineEntry)_baselineEntry.clone());
	}

	@Override
	public int compareTo(BaselineEntry baselineEntry) {
		return _baselineEntry.compareTo(baselineEntry);
	}

	/**
	* Returns the baseline entry ID of this baseline entry.
	*
	* @return the baseline entry ID of this baseline entry
	*/
	@Override
	public long getBaselineEntryId() {
		return _baselineEntry.getBaselineEntryId();
	}

	/**
	* Returns the baseline information ID of this baseline entry.
	*
	* @return the baseline information ID of this baseline entry
	*/
	@Override
	public long getBaselineInformationId() {
		return _baselineEntry.getBaselineInformationId();
	}

	/**
	* Returns the fully qualified class name of this baseline entry.
	*
	* @return the fully qualified class name of this baseline entry
	*/
	@Override
	public String getClassName() {
		return _baselineEntry.getClassName();
	}

	/**
	* Returns the class name ID of this baseline entry.
	*
	* @return the class name ID of this baseline entry
	*/
	@Override
	public long getClassNameId() {
		return _baselineEntry.getClassNameId();
	}

	/**
	* Returns the class pk of this baseline entry.
	*
	* @return the class pk of this baseline entry
	*/
	@Override
	public long getClassPK() {
		return _baselineEntry.getClassPK();
	}

	/**
	* Returns the company ID of this baseline entry.
	*
	* @return the company ID of this baseline entry
	*/
	@Override
	public long getCompanyId() {
		return _baselineEntry.getCompanyId();
	}

	/**
	* Returns the create date of this baseline entry.
	*
	* @return the create date of this baseline entry
	*/
	@Override
	public Date getCreateDate() {
		return _baselineEntry.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _baselineEntry.getExpandoBridge();
	}

	/**
	* Returns the modified date of this baseline entry.
	*
	* @return the modified date of this baseline entry
	*/
	@Override
	public Date getModifiedDate() {
		return _baselineEntry.getModifiedDate();
	}

	/**
	* Returns the primary key of this baseline entry.
	*
	* @return the primary key of this baseline entry
	*/
	@Override
	public long getPrimaryKey() {
		return _baselineEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _baselineEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this baseline entry.
	*
	* @return the user ID of this baseline entry
	*/
	@Override
	public long getUserId() {
		return _baselineEntry.getUserId();
	}

	/**
	* Returns the user name of this baseline entry.
	*
	* @return the user name of this baseline entry
	*/
	@Override
	public String getUserName() {
		return _baselineEntry.getUserName();
	}

	/**
	* Returns the user uuid of this baseline entry.
	*
	* @return the user uuid of this baseline entry
	*/
	@Override
	public String getUserUuid() {
		return _baselineEntry.getUserUuid();
	}

	/**
	* Returns the version of this baseline entry.
	*
	* @return the version of this baseline entry
	*/
	@Override
	public double getVersion() {
		return _baselineEntry.getVersion();
	}

	@Override
	public int hashCode() {
		return _baselineEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _baselineEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _baselineEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _baselineEntry.isNew();
	}

	@Override
	public void persist() {
		_baselineEntry.persist();
	}

	/**
	* Sets the baseline entry ID of this baseline entry.
	*
	* @param baselineEntryId the baseline entry ID of this baseline entry
	*/
	@Override
	public void setBaselineEntryId(long baselineEntryId) {
		_baselineEntry.setBaselineEntryId(baselineEntryId);
	}

	/**
	* Sets the baseline information ID of this baseline entry.
	*
	* @param baselineInformationId the baseline information ID of this baseline entry
	*/
	@Override
	public void setBaselineInformationId(long baselineInformationId) {
		_baselineEntry.setBaselineInformationId(baselineInformationId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_baselineEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(String className) {
		_baselineEntry.setClassName(className);
	}

	/**
	* Sets the class name ID of this baseline entry.
	*
	* @param classNameId the class name ID of this baseline entry
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_baselineEntry.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this baseline entry.
	*
	* @param classPK the class pk of this baseline entry
	*/
	@Override
	public void setClassPK(long classPK) {
		_baselineEntry.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this baseline entry.
	*
	* @param companyId the company ID of this baseline entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_baselineEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this baseline entry.
	*
	* @param createDate the create date of this baseline entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_baselineEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_baselineEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_baselineEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_baselineEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this baseline entry.
	*
	* @param modifiedDate the modified date of this baseline entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_baselineEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_baselineEntry.setNew(n);
	}

	/**
	* Sets the primary key of this baseline entry.
	*
	* @param primaryKey the primary key of this baseline entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_baselineEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_baselineEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this baseline entry.
	*
	* @param userId the user ID of this baseline entry
	*/
	@Override
	public void setUserId(long userId) {
		_baselineEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this baseline entry.
	*
	* @param userName the user name of this baseline entry
	*/
	@Override
	public void setUserName(String userName) {
		_baselineEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this baseline entry.
	*
	* @param userUuid the user uuid of this baseline entry
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_baselineEntry.setUserUuid(userUuid);
	}

	/**
	* Sets the version of this baseline entry.
	*
	* @param version the version of this baseline entry
	*/
	@Override
	public void setVersion(double version) {
		_baselineEntry.setVersion(version);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<BaselineEntry> toCacheModel() {
		return _baselineEntry.toCacheModel();
	}

	@Override
	public BaselineEntry toEscapedModel() {
		return new BaselineEntryWrapper(_baselineEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _baselineEntry.toString();
	}

	@Override
	public BaselineEntry toUnescapedModel() {
		return new BaselineEntryWrapper(_baselineEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _baselineEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BaselineEntryWrapper)) {
			return false;
		}

		BaselineEntryWrapper baselineEntryWrapper = (BaselineEntryWrapper)obj;

		if (Objects.equals(_baselineEntry, baselineEntryWrapper._baselineEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public BaselineEntry getWrappedModel() {
		return _baselineEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _baselineEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _baselineEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_baselineEntry.resetOriginalValues();
	}

	private final BaselineEntry _baselineEntry;
}
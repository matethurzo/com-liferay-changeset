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
 * This class is a wrapper for {@link BaselineInformation}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BaselineInformation
 * @generated
 */
@ProviderType
public class BaselineInformationWrapper implements BaselineInformation,
	ModelWrapper<BaselineInformation> {
	public BaselineInformationWrapper(BaselineInformation baselineInformation) {
		_baselineInformation = baselineInformation;
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
	public Object clone() {
		return new BaselineInformationWrapper((BaselineInformation)_baselineInformation.clone());
	}

	@Override
	public int compareTo(BaselineInformation baselineInformation) {
		return _baselineInformation.compareTo(baselineInformation);
	}

	/**
	* Returns the baseline information ID of this baseline information.
	*
	* @return the baseline information ID of this baseline information
	*/
	@Override
	public long getBaselineInformationId() {
		return _baselineInformation.getBaselineInformationId();
	}

	/**
	* Returns the company ID of this baseline information.
	*
	* @return the company ID of this baseline information
	*/
	@Override
	public long getCompanyId() {
		return _baselineInformation.getCompanyId();
	}

	/**
	* Returns the create date of this baseline information.
	*
	* @return the create date of this baseline information
	*/
	@Override
	public Date getCreateDate() {
		return _baselineInformation.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _baselineInformation.getExpandoBridge();
	}

	/**
	* Returns the modified date of this baseline information.
	*
	* @return the modified date of this baseline information
	*/
	@Override
	public Date getModifiedDate() {
		return _baselineInformation.getModifiedDate();
	}

	/**
	* Returns the name of this baseline information.
	*
	* @return the name of this baseline information
	*/
	@Override
	public String getName() {
		return _baselineInformation.getName();
	}

	/**
	* Returns the primary key of this baseline information.
	*
	* @return the primary key of this baseline information
	*/
	@Override
	public long getPrimaryKey() {
		return _baselineInformation.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _baselineInformation.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this baseline information.
	*
	* @return the user ID of this baseline information
	*/
	@Override
	public long getUserId() {
		return _baselineInformation.getUserId();
	}

	/**
	* Returns the user name of this baseline information.
	*
	* @return the user name of this baseline information
	*/
	@Override
	public String getUserName() {
		return _baselineInformation.getUserName();
	}

	/**
	* Returns the user uuid of this baseline information.
	*
	* @return the user uuid of this baseline information
	*/
	@Override
	public String getUserUuid() {
		return _baselineInformation.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _baselineInformation.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _baselineInformation.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _baselineInformation.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _baselineInformation.isNew();
	}

	@Override
	public void persist() {
		_baselineInformation.persist();
	}

	/**
	* Sets the baseline information ID of this baseline information.
	*
	* @param baselineInformationId the baseline information ID of this baseline information
	*/
	@Override
	public void setBaselineInformationId(long baselineInformationId) {
		_baselineInformation.setBaselineInformationId(baselineInformationId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_baselineInformation.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this baseline information.
	*
	* @param companyId the company ID of this baseline information
	*/
	@Override
	public void setCompanyId(long companyId) {
		_baselineInformation.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this baseline information.
	*
	* @param createDate the create date of this baseline information
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_baselineInformation.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_baselineInformation.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_baselineInformation.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_baselineInformation.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this baseline information.
	*
	* @param modifiedDate the modified date of this baseline information
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_baselineInformation.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this baseline information.
	*
	* @param name the name of this baseline information
	*/
	@Override
	public void setName(String name) {
		_baselineInformation.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_baselineInformation.setNew(n);
	}

	/**
	* Sets the primary key of this baseline information.
	*
	* @param primaryKey the primary key of this baseline information
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_baselineInformation.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_baselineInformation.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this baseline information.
	*
	* @param userId the user ID of this baseline information
	*/
	@Override
	public void setUserId(long userId) {
		_baselineInformation.setUserId(userId);
	}

	/**
	* Sets the user name of this baseline information.
	*
	* @param userName the user name of this baseline information
	*/
	@Override
	public void setUserName(String userName) {
		_baselineInformation.setUserName(userName);
	}

	/**
	* Sets the user uuid of this baseline information.
	*
	* @param userUuid the user uuid of this baseline information
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_baselineInformation.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<BaselineInformation> toCacheModel() {
		return _baselineInformation.toCacheModel();
	}

	@Override
	public BaselineInformation toEscapedModel() {
		return new BaselineInformationWrapper(_baselineInformation.toEscapedModel());
	}

	@Override
	public String toString() {
		return _baselineInformation.toString();
	}

	@Override
	public BaselineInformation toUnescapedModel() {
		return new BaselineInformationWrapper(_baselineInformation.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _baselineInformation.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BaselineInformationWrapper)) {
			return false;
		}

		BaselineInformationWrapper baselineInformationWrapper = (BaselineInformationWrapper)obj;

		if (Objects.equals(_baselineInformation,
					baselineInformationWrapper._baselineInformation)) {
			return true;
		}

		return false;
	}

	@Override
	public BaselineInformation getWrappedModel() {
		return _baselineInformation;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _baselineInformation.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _baselineInformation.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_baselineInformation.resetOriginalValues();
	}

	private final BaselineInformation _baselineInformation;
}
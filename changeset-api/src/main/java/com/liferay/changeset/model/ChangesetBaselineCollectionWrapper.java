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

package com.liferay.changeset.model;

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
 * This class is a wrapper for {@link ChangesetBaselineCollection}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetBaselineCollection
 * @generated
 */
@ProviderType
public class ChangesetBaselineCollectionWrapper
	implements ChangesetBaselineCollection,
		ModelWrapper<ChangesetBaselineCollection> {
	public ChangesetBaselineCollectionWrapper(
		ChangesetBaselineCollection changesetBaselineCollection) {
		_changesetBaselineCollection = changesetBaselineCollection;
	}

	@Override
	public Class<?> getModelClass() {
		return ChangesetBaselineCollection.class;
	}

	@Override
	public String getModelClassName() {
		return ChangesetBaselineCollection.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("changesetBaselineCollectionId",
			getChangesetBaselineCollectionId());
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
		Long changesetBaselineCollectionId = (Long)attributes.get(
				"changesetBaselineCollectionId");

		if (changesetBaselineCollectionId != null) {
			setChangesetBaselineCollectionId(changesetBaselineCollectionId);
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
		return new ChangesetBaselineCollectionWrapper((ChangesetBaselineCollection)_changesetBaselineCollection.clone());
	}

	@Override
	public int compareTo(
		ChangesetBaselineCollection changesetBaselineCollection) {
		return _changesetBaselineCollection.compareTo(changesetBaselineCollection);
	}

	/**
	* Returns the changeset baseline collection ID of this changeset baseline collection.
	*
	* @return the changeset baseline collection ID of this changeset baseline collection
	*/
	@Override
	public long getChangesetBaselineCollectionId() {
		return _changesetBaselineCollection.getChangesetBaselineCollectionId();
	}

	/**
	* Returns the company ID of this changeset baseline collection.
	*
	* @return the company ID of this changeset baseline collection
	*/
	@Override
	public long getCompanyId() {
		return _changesetBaselineCollection.getCompanyId();
	}

	/**
	* Returns the create date of this changeset baseline collection.
	*
	* @return the create date of this changeset baseline collection
	*/
	@Override
	public Date getCreateDate() {
		return _changesetBaselineCollection.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _changesetBaselineCollection.getExpandoBridge();
	}

	/**
	* Returns the modified date of this changeset baseline collection.
	*
	* @return the modified date of this changeset baseline collection
	*/
	@Override
	public Date getModifiedDate() {
		return _changesetBaselineCollection.getModifiedDate();
	}

	/**
	* Returns the name of this changeset baseline collection.
	*
	* @return the name of this changeset baseline collection
	*/
	@Override
	public String getName() {
		return _changesetBaselineCollection.getName();
	}

	/**
	* Returns the primary key of this changeset baseline collection.
	*
	* @return the primary key of this changeset baseline collection
	*/
	@Override
	public long getPrimaryKey() {
		return _changesetBaselineCollection.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _changesetBaselineCollection.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this changeset baseline collection.
	*
	* @return the user ID of this changeset baseline collection
	*/
	@Override
	public long getUserId() {
		return _changesetBaselineCollection.getUserId();
	}

	/**
	* Returns the user name of this changeset baseline collection.
	*
	* @return the user name of this changeset baseline collection
	*/
	@Override
	public String getUserName() {
		return _changesetBaselineCollection.getUserName();
	}

	/**
	* Returns the user uuid of this changeset baseline collection.
	*
	* @return the user uuid of this changeset baseline collection
	*/
	@Override
	public String getUserUuid() {
		return _changesetBaselineCollection.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _changesetBaselineCollection.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _changesetBaselineCollection.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _changesetBaselineCollection.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _changesetBaselineCollection.isNew();
	}

	@Override
	public void persist() {
		_changesetBaselineCollection.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_changesetBaselineCollection.setCachedModel(cachedModel);
	}

	/**
	* Sets the changeset baseline collection ID of this changeset baseline collection.
	*
	* @param changesetBaselineCollectionId the changeset baseline collection ID of this changeset baseline collection
	*/
	@Override
	public void setChangesetBaselineCollectionId(
		long changesetBaselineCollectionId) {
		_changesetBaselineCollection.setChangesetBaselineCollectionId(changesetBaselineCollectionId);
	}

	/**
	* Sets the company ID of this changeset baseline collection.
	*
	* @param companyId the company ID of this changeset baseline collection
	*/
	@Override
	public void setCompanyId(long companyId) {
		_changesetBaselineCollection.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this changeset baseline collection.
	*
	* @param createDate the create date of this changeset baseline collection
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_changesetBaselineCollection.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_changesetBaselineCollection.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_changesetBaselineCollection.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_changesetBaselineCollection.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this changeset baseline collection.
	*
	* @param modifiedDate the modified date of this changeset baseline collection
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_changesetBaselineCollection.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this changeset baseline collection.
	*
	* @param name the name of this changeset baseline collection
	*/
	@Override
	public void setName(String name) {
		_changesetBaselineCollection.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_changesetBaselineCollection.setNew(n);
	}

	/**
	* Sets the primary key of this changeset baseline collection.
	*
	* @param primaryKey the primary key of this changeset baseline collection
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_changesetBaselineCollection.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_changesetBaselineCollection.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this changeset baseline collection.
	*
	* @param userId the user ID of this changeset baseline collection
	*/
	@Override
	public void setUserId(long userId) {
		_changesetBaselineCollection.setUserId(userId);
	}

	/**
	* Sets the user name of this changeset baseline collection.
	*
	* @param userName the user name of this changeset baseline collection
	*/
	@Override
	public void setUserName(String userName) {
		_changesetBaselineCollection.setUserName(userName);
	}

	/**
	* Sets the user uuid of this changeset baseline collection.
	*
	* @param userUuid the user uuid of this changeset baseline collection
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_changesetBaselineCollection.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<ChangesetBaselineCollection> toCacheModel() {
		return _changesetBaselineCollection.toCacheModel();
	}

	@Override
	public ChangesetBaselineCollection toEscapedModel() {
		return new ChangesetBaselineCollectionWrapper(_changesetBaselineCollection.toEscapedModel());
	}

	@Override
	public String toString() {
		return _changesetBaselineCollection.toString();
	}

	@Override
	public ChangesetBaselineCollection toUnescapedModel() {
		return new ChangesetBaselineCollectionWrapper(_changesetBaselineCollection.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _changesetBaselineCollection.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ChangesetBaselineCollectionWrapper)) {
			return false;
		}

		ChangesetBaselineCollectionWrapper changesetBaselineCollectionWrapper = (ChangesetBaselineCollectionWrapper)obj;

		if (Objects.equals(_changesetBaselineCollection,
					changesetBaselineCollectionWrapper._changesetBaselineCollection)) {
			return true;
		}

		return false;
	}

	@Override
	public ChangesetBaselineCollection getWrappedModel() {
		return _changesetBaselineCollection;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _changesetBaselineCollection.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _changesetBaselineCollection.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_changesetBaselineCollection.resetOriginalValues();
	}

	private final ChangesetBaselineCollection _changesetBaselineCollection;
}
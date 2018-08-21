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
 * This class is a wrapper for {@link ChangesetBaselineEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetBaselineEntry
 * @generated
 */
@ProviderType
public class ChangesetBaselineEntryWrapper implements ChangesetBaselineEntry,
	ModelWrapper<ChangesetBaselineEntry> {
	public ChangesetBaselineEntryWrapper(
		ChangesetBaselineEntry changesetBaselineEntry) {
		_changesetBaselineEntry = changesetBaselineEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return ChangesetBaselineEntry.class;
	}

	@Override
	public String getModelClassName() {
		return ChangesetBaselineEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("changesetBaselineEntryId", getChangesetBaselineEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("changesetBaselineCollectionId",
			getChangesetBaselineCollectionId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("resourcePrimKey", getResourcePrimKey());
		attributes.put("version", getVersion());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long changesetBaselineEntryId = (Long)attributes.get(
				"changesetBaselineEntryId");

		if (changesetBaselineEntryId != null) {
			setChangesetBaselineEntryId(changesetBaselineEntryId);
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

		Long changesetBaselineCollectionId = (Long)attributes.get(
				"changesetBaselineCollectionId");

		if (changesetBaselineCollectionId != null) {
			setChangesetBaselineCollectionId(changesetBaselineCollectionId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long resourcePrimKey = (Long)attributes.get("resourcePrimKey");

		if (resourcePrimKey != null) {
			setResourcePrimKey(resourcePrimKey);
		}

		Double version = (Double)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}
	}

	@Override
	public Object clone() {
		return new ChangesetBaselineEntryWrapper((ChangesetBaselineEntry)_changesetBaselineEntry.clone());
	}

	@Override
	public int compareTo(ChangesetBaselineEntry changesetBaselineEntry) {
		return _changesetBaselineEntry.compareTo(changesetBaselineEntry);
	}

	/**
	* Returns the changeset baseline collection ID of this changeset baseline entry.
	*
	* @return the changeset baseline collection ID of this changeset baseline entry
	*/
	@Override
	public long getChangesetBaselineCollectionId() {
		return _changesetBaselineEntry.getChangesetBaselineCollectionId();
	}

	/**
	* Returns the changeset baseline entry ID of this changeset baseline entry.
	*
	* @return the changeset baseline entry ID of this changeset baseline entry
	*/
	@Override
	public long getChangesetBaselineEntryId() {
		return _changesetBaselineEntry.getChangesetBaselineEntryId();
	}

	/**
	* Returns the fully qualified class name of this changeset baseline entry.
	*
	* @return the fully qualified class name of this changeset baseline entry
	*/
	@Override
	public String getClassName() {
		return _changesetBaselineEntry.getClassName();
	}

	/**
	* Returns the class name ID of this changeset baseline entry.
	*
	* @return the class name ID of this changeset baseline entry
	*/
	@Override
	public long getClassNameId() {
		return _changesetBaselineEntry.getClassNameId();
	}

	/**
	* Returns the class pk of this changeset baseline entry.
	*
	* @return the class pk of this changeset baseline entry
	*/
	@Override
	public long getClassPK() {
		return _changesetBaselineEntry.getClassPK();
	}

	/**
	* Returns the company ID of this changeset baseline entry.
	*
	* @return the company ID of this changeset baseline entry
	*/
	@Override
	public long getCompanyId() {
		return _changesetBaselineEntry.getCompanyId();
	}

	/**
	* Returns the create date of this changeset baseline entry.
	*
	* @return the create date of this changeset baseline entry
	*/
	@Override
	public Date getCreateDate() {
		return _changesetBaselineEntry.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _changesetBaselineEntry.getExpandoBridge();
	}

	/**
	* Returns the modified date of this changeset baseline entry.
	*
	* @return the modified date of this changeset baseline entry
	*/
	@Override
	public Date getModifiedDate() {
		return _changesetBaselineEntry.getModifiedDate();
	}

	/**
	* Returns the primary key of this changeset baseline entry.
	*
	* @return the primary key of this changeset baseline entry
	*/
	@Override
	public long getPrimaryKey() {
		return _changesetBaselineEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _changesetBaselineEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the resource prim key of this changeset baseline entry.
	*
	* @return the resource prim key of this changeset baseline entry
	*/
	@Override
	public long getResourcePrimKey() {
		return _changesetBaselineEntry.getResourcePrimKey();
	}

	/**
	* Returns the user ID of this changeset baseline entry.
	*
	* @return the user ID of this changeset baseline entry
	*/
	@Override
	public long getUserId() {
		return _changesetBaselineEntry.getUserId();
	}

	/**
	* Returns the user name of this changeset baseline entry.
	*
	* @return the user name of this changeset baseline entry
	*/
	@Override
	public String getUserName() {
		return _changesetBaselineEntry.getUserName();
	}

	/**
	* Returns the user uuid of this changeset baseline entry.
	*
	* @return the user uuid of this changeset baseline entry
	*/
	@Override
	public String getUserUuid() {
		return _changesetBaselineEntry.getUserUuid();
	}

	/**
	* Returns the version of this changeset baseline entry.
	*
	* @return the version of this changeset baseline entry
	*/
	@Override
	public double getVersion() {
		return _changesetBaselineEntry.getVersion();
	}

	@Override
	public int hashCode() {
		return _changesetBaselineEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _changesetBaselineEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _changesetBaselineEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _changesetBaselineEntry.isNew();
	}

	@Override
	public boolean isResourceMain() {
		return _changesetBaselineEntry.isResourceMain();
	}

	@Override
	public void persist() {
		_changesetBaselineEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_changesetBaselineEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the changeset baseline collection ID of this changeset baseline entry.
	*
	* @param changesetBaselineCollectionId the changeset baseline collection ID of this changeset baseline entry
	*/
	@Override
	public void setChangesetBaselineCollectionId(
		long changesetBaselineCollectionId) {
		_changesetBaselineEntry.setChangesetBaselineCollectionId(changesetBaselineCollectionId);
	}

	/**
	* Sets the changeset baseline entry ID of this changeset baseline entry.
	*
	* @param changesetBaselineEntryId the changeset baseline entry ID of this changeset baseline entry
	*/
	@Override
	public void setChangesetBaselineEntryId(long changesetBaselineEntryId) {
		_changesetBaselineEntry.setChangesetBaselineEntryId(changesetBaselineEntryId);
	}

	@Override
	public void setClassName(String className) {
		_changesetBaselineEntry.setClassName(className);
	}

	/**
	* Sets the class name ID of this changeset baseline entry.
	*
	* @param classNameId the class name ID of this changeset baseline entry
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_changesetBaselineEntry.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this changeset baseline entry.
	*
	* @param classPK the class pk of this changeset baseline entry
	*/
	@Override
	public void setClassPK(long classPK) {
		_changesetBaselineEntry.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this changeset baseline entry.
	*
	* @param companyId the company ID of this changeset baseline entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_changesetBaselineEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this changeset baseline entry.
	*
	* @param createDate the create date of this changeset baseline entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_changesetBaselineEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_changesetBaselineEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_changesetBaselineEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_changesetBaselineEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this changeset baseline entry.
	*
	* @param modifiedDate the modified date of this changeset baseline entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_changesetBaselineEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_changesetBaselineEntry.setNew(n);
	}

	/**
	* Sets the primary key of this changeset baseline entry.
	*
	* @param primaryKey the primary key of this changeset baseline entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_changesetBaselineEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_changesetBaselineEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the resource prim key of this changeset baseline entry.
	*
	* @param resourcePrimKey the resource prim key of this changeset baseline entry
	*/
	@Override
	public void setResourcePrimKey(long resourcePrimKey) {
		_changesetBaselineEntry.setResourcePrimKey(resourcePrimKey);
	}

	/**
	* Sets the user ID of this changeset baseline entry.
	*
	* @param userId the user ID of this changeset baseline entry
	*/
	@Override
	public void setUserId(long userId) {
		_changesetBaselineEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this changeset baseline entry.
	*
	* @param userName the user name of this changeset baseline entry
	*/
	@Override
	public void setUserName(String userName) {
		_changesetBaselineEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this changeset baseline entry.
	*
	* @param userUuid the user uuid of this changeset baseline entry
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_changesetBaselineEntry.setUserUuid(userUuid);
	}

	/**
	* Sets the version of this changeset baseline entry.
	*
	* @param version the version of this changeset baseline entry
	*/
	@Override
	public void setVersion(double version) {
		_changesetBaselineEntry.setVersion(version);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<ChangesetBaselineEntry> toCacheModel() {
		return _changesetBaselineEntry.toCacheModel();
	}

	@Override
	public ChangesetBaselineEntry toEscapedModel() {
		return new ChangesetBaselineEntryWrapper(_changesetBaselineEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _changesetBaselineEntry.toString();
	}

	@Override
	public ChangesetBaselineEntry toUnescapedModel() {
		return new ChangesetBaselineEntryWrapper(_changesetBaselineEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _changesetBaselineEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ChangesetBaselineEntryWrapper)) {
			return false;
		}

		ChangesetBaselineEntryWrapper changesetBaselineEntryWrapper = (ChangesetBaselineEntryWrapper)obj;

		if (Objects.equals(_changesetBaselineEntry,
					changesetBaselineEntryWrapper._changesetBaselineEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public ChangesetBaselineEntry getWrappedModel() {
		return _changesetBaselineEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _changesetBaselineEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _changesetBaselineEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_changesetBaselineEntry.resetOriginalValues();
	}

	private final ChangesetBaselineEntry _changesetBaselineEntry;
}
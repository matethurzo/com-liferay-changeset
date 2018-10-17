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

package com.liferay.changeset.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ChangesetBaselineEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetBaselineEntryLocalService
 * @generated
 */
@ProviderType
public class ChangesetBaselineEntryLocalServiceWrapper
	implements ChangesetBaselineEntryLocalService,
		ServiceWrapper<ChangesetBaselineEntryLocalService> {
	public ChangesetBaselineEntryLocalServiceWrapper(
		ChangesetBaselineEntryLocalService changesetBaselineEntryLocalService) {
		_changesetBaselineEntryLocalService = changesetBaselineEntryLocalService;
	}

	/**
	* Adds the changeset baseline entry to the database. Also notifies the appropriate model listeners.
	*
	* @param changesetBaselineEntry the changeset baseline entry
	* @return the changeset baseline entry that was added
	*/
	@Override
	public com.liferay.changeset.model.ChangesetBaselineEntry addChangesetBaselineEntry(
		com.liferay.changeset.model.ChangesetBaselineEntry changesetBaselineEntry) {
		return _changesetBaselineEntryLocalService.addChangesetBaselineEntry(changesetBaselineEntry);
	}

	@Override
	public com.liferay.changeset.model.ChangesetBaselineEntry addChangesetBaselineEntry(
		long changesetBaselineCollectionId, long classNameId, long classPK,
		long resourcePrimKey, double version) {
		return _changesetBaselineEntryLocalService.addChangesetBaselineEntry(changesetBaselineCollectionId,
			classNameId, classPK, resourcePrimKey, version);
	}

	/**
	* Creates a new changeset baseline entry with the primary key. Does not add the changeset baseline entry to the database.
	*
	* @param changesetBaselineEntryId the primary key for the new changeset baseline entry
	* @return the new changeset baseline entry
	*/
	@Override
	public com.liferay.changeset.model.ChangesetBaselineEntry createChangesetBaselineEntry(
		long changesetBaselineEntryId) {
		return _changesetBaselineEntryLocalService.createChangesetBaselineEntry(changesetBaselineEntryId);
	}

	@Override
	public void deleteChangesetBaselineEntries(
		long changesetBaselineCollectionId) {
		_changesetBaselineEntryLocalService.deleteChangesetBaselineEntries(changesetBaselineCollectionId);
	}

	/**
	* Deletes the changeset baseline entry from the database. Also notifies the appropriate model listeners.
	*
	* @param changesetBaselineEntry the changeset baseline entry
	* @return the changeset baseline entry that was removed
	*/
	@Override
	public com.liferay.changeset.model.ChangesetBaselineEntry deleteChangesetBaselineEntry(
		com.liferay.changeset.model.ChangesetBaselineEntry changesetBaselineEntry) {
		return _changesetBaselineEntryLocalService.deleteChangesetBaselineEntry(changesetBaselineEntry);
	}

	/**
	* Deletes the changeset baseline entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changesetBaselineEntryId the primary key of the changeset baseline entry
	* @return the changeset baseline entry that was removed
	* @throws PortalException if a changeset baseline entry with the primary key could not be found
	*/
	@Override
	public com.liferay.changeset.model.ChangesetBaselineEntry deleteChangesetBaselineEntry(
		long changesetBaselineEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changesetBaselineEntryLocalService.deleteChangesetBaselineEntry(changesetBaselineEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changesetBaselineEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _changesetBaselineEntryLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _changesetBaselineEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.model.impl.ChangesetBaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _changesetBaselineEntryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.model.impl.ChangesetBaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _changesetBaselineEntryLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _changesetBaselineEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _changesetBaselineEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.changeset.model.ChangesetBaselineEntry fetchChangesetBaselineEntry(
		long changesetBaselineEntryId) {
		return _changesetBaselineEntryLocalService.fetchChangesetBaselineEntry(changesetBaselineEntryId);
	}

	@Override
	public com.liferay.changeset.model.ChangesetBaselineEntry fetchLatestChangesetBaselineEntry(
		long changesetBaselineCollectionId, long resourcePrimKey) {
		return _changesetBaselineEntryLocalService.fetchLatestChangesetBaselineEntry(changesetBaselineCollectionId,
			resourcePrimKey);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _changesetBaselineEntryLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the changeset baseline entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.model.impl.ChangesetBaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of changeset baseline entries
	* @param end the upper bound of the range of changeset baseline entries (not inclusive)
	* @return the range of changeset baseline entries
	*/
	@Override
	public java.util.List<com.liferay.changeset.model.ChangesetBaselineEntry> getChangesetBaselineEntries(
		int start, int end) {
		return _changesetBaselineEntryLocalService.getChangesetBaselineEntries(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.changeset.model.ChangesetBaselineEntry> getChangesetBaselineEntries(
		long changesetBaselineCollectionId) {
		return _changesetBaselineEntryLocalService.getChangesetBaselineEntries(changesetBaselineCollectionId);
	}

	/**
	* Returns the number of changeset baseline entries.
	*
	* @return the number of changeset baseline entries
	*/
	@Override
	public int getChangesetBaselineEntriesCount() {
		return _changesetBaselineEntryLocalService.getChangesetBaselineEntriesCount();
	}

	/**
	* Returns the changeset baseline entry with the primary key.
	*
	* @param changesetBaselineEntryId the primary key of the changeset baseline entry
	* @return the changeset baseline entry
	* @throws PortalException if a changeset baseline entry with the primary key could not be found
	*/
	@Override
	public com.liferay.changeset.model.ChangesetBaselineEntry getChangesetBaselineEntry(
		long changesetBaselineEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changesetBaselineEntryLocalService.getChangesetBaselineEntry(changesetBaselineEntryId);
	}

	@Override
	public com.liferay.changeset.model.ChangesetBaselineEntry getChangesetBaselineEntry(
		long changesetBaselineCollectionId, long classNameId, long classPK)
		throws com.liferay.changeset.exception.NoSuchBaselineEntryException {
		return _changesetBaselineEntryLocalService.getChangesetBaselineEntry(changesetBaselineCollectionId,
			classNameId, classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _changesetBaselineEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _changesetBaselineEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<?extends com.liferay.portal.kernel.model.PersistedModel> getPersistedModel(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changesetBaselineEntryLocalService.getPersistedModel(resourcePrimKey);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changesetBaselineEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the changeset baseline entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param changesetBaselineEntry the changeset baseline entry
	* @return the changeset baseline entry that was updated
	*/
	@Override
	public com.liferay.changeset.model.ChangesetBaselineEntry updateChangesetBaselineEntry(
		com.liferay.changeset.model.ChangesetBaselineEntry changesetBaselineEntry) {
		return _changesetBaselineEntryLocalService.updateChangesetBaselineEntry(changesetBaselineEntry);
	}

	@Override
	public ChangesetBaselineEntryLocalService getWrappedService() {
		return _changesetBaselineEntryLocalService;
	}

	@Override
	public void setWrappedService(
		ChangesetBaselineEntryLocalService changesetBaselineEntryLocalService) {
		_changesetBaselineEntryLocalService = changesetBaselineEntryLocalService;
	}

	private ChangesetBaselineEntryLocalService _changesetBaselineEntryLocalService;
}
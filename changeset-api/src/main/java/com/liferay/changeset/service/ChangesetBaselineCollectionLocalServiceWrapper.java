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
 * Provides a wrapper for {@link ChangesetBaselineCollectionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetBaselineCollectionLocalService
 * @generated
 */
@ProviderType
public class ChangesetBaselineCollectionLocalServiceWrapper
	implements ChangesetBaselineCollectionLocalService,
		ServiceWrapper<ChangesetBaselineCollectionLocalService> {
	public ChangesetBaselineCollectionLocalServiceWrapper(
		ChangesetBaselineCollectionLocalService changesetBaselineCollectionLocalService) {
		_changesetBaselineCollectionLocalService = changesetBaselineCollectionLocalService;
	}

	/**
	* Adds the changeset baseline collection to the database. Also notifies the appropriate model listeners.
	*
	* @param changesetBaselineCollection the changeset baseline collection
	* @return the changeset baseline collection that was added
	*/
	@Override
	public com.liferay.changeset.model.ChangesetBaselineCollection addChangesetBaselineCollection(
		com.liferay.changeset.model.ChangesetBaselineCollection changesetBaselineCollection) {
		return _changesetBaselineCollectionLocalService.addChangesetBaselineCollection(changesetBaselineCollection);
	}

	@Override
	public com.liferay.changeset.model.ChangesetBaselineCollection addChangesetBaselineCollection(
		long userId, String name) {
		return _changesetBaselineCollectionLocalService.addChangesetBaselineCollection(userId,
			name);
	}

	/**
	* Creates a new changeset baseline collection with the primary key. Does not add the changeset baseline collection to the database.
	*
	* @param changesetBaselineCollectionId the primary key for the new changeset baseline collection
	* @return the new changeset baseline collection
	*/
	@Override
	public com.liferay.changeset.model.ChangesetBaselineCollection createChangesetBaselineCollection(
		long changesetBaselineCollectionId) {
		return _changesetBaselineCollectionLocalService.createChangesetBaselineCollection(changesetBaselineCollectionId);
	}

	/**
	* Deletes the changeset baseline collection from the database. Also notifies the appropriate model listeners.
	*
	* @param changesetBaselineCollection the changeset baseline collection
	* @return the changeset baseline collection that was removed
	*/
	@Override
	public com.liferay.changeset.model.ChangesetBaselineCollection deleteChangesetBaselineCollection(
		com.liferay.changeset.model.ChangesetBaselineCollection changesetBaselineCollection) {
		return _changesetBaselineCollectionLocalService.deleteChangesetBaselineCollection(changesetBaselineCollection);
	}

	/**
	* Deletes the changeset baseline collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changesetBaselineCollectionId the primary key of the changeset baseline collection
	* @return the changeset baseline collection that was removed
	* @throws PortalException if a changeset baseline collection with the primary key could not be found
	*/
	@Override
	public com.liferay.changeset.model.ChangesetBaselineCollection deleteChangesetBaselineCollection(
		long changesetBaselineCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changesetBaselineCollectionLocalService.deleteChangesetBaselineCollection(changesetBaselineCollectionId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changesetBaselineCollectionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _changesetBaselineCollectionLocalService.dynamicQuery();
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
		return _changesetBaselineCollectionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.model.impl.ChangesetBaselineCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _changesetBaselineCollectionLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.model.impl.ChangesetBaselineCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _changesetBaselineCollectionLocalService.dynamicQuery(dynamicQuery,
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
		return _changesetBaselineCollectionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _changesetBaselineCollectionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.changeset.model.ChangesetBaselineCollection fetchChangesetBaselineCollection(
		long changesetBaselineCollectionId) {
		return _changesetBaselineCollectionLocalService.fetchChangesetBaselineCollection(changesetBaselineCollectionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _changesetBaselineCollectionLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the changeset baseline collection with the primary key.
	*
	* @param changesetBaselineCollectionId the primary key of the changeset baseline collection
	* @return the changeset baseline collection
	* @throws PortalException if a changeset baseline collection with the primary key could not be found
	*/
	@Override
	public com.liferay.changeset.model.ChangesetBaselineCollection getChangesetBaselineCollection(
		long changesetBaselineCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changesetBaselineCollectionLocalService.getChangesetBaselineCollection(changesetBaselineCollectionId);
	}

	@Override
	public java.util.Optional<com.liferay.changeset.model.ChangesetBaselineCollection> getChangesetBaselineCollectionByName(
		String name) {
		return _changesetBaselineCollectionLocalService.getChangesetBaselineCollectionByName(name);
	}

	/**
	* Returns a range of all the changeset baseline collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.model.impl.ChangesetBaselineCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of changeset baseline collections
	* @param end the upper bound of the range of changeset baseline collections (not inclusive)
	* @return the range of changeset baseline collections
	*/
	@Override
	public java.util.List<com.liferay.changeset.model.ChangesetBaselineCollection> getChangesetBaselineCollections(
		int start, int end) {
		return _changesetBaselineCollectionLocalService.getChangesetBaselineCollections(start,
			end);
	}

	/**
	* Returns the number of changeset baseline collections.
	*
	* @return the number of changeset baseline collections
	*/
	@Override
	public int getChangesetBaselineCollectionsCount() {
		return _changesetBaselineCollectionLocalService.getChangesetBaselineCollectionsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _changesetBaselineCollectionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _changesetBaselineCollectionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changesetBaselineCollectionLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the changeset baseline collection in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param changesetBaselineCollection the changeset baseline collection
	* @return the changeset baseline collection that was updated
	*/
	@Override
	public com.liferay.changeset.model.ChangesetBaselineCollection updateChangesetBaselineCollection(
		com.liferay.changeset.model.ChangesetBaselineCollection changesetBaselineCollection) {
		return _changesetBaselineCollectionLocalService.updateChangesetBaselineCollection(changesetBaselineCollection);
	}

	@Override
	public ChangesetBaselineCollectionLocalService getWrappedService() {
		return _changesetBaselineCollectionLocalService;
	}

	@Override
	public void setWrappedService(
		ChangesetBaselineCollectionLocalService changesetBaselineCollectionLocalService) {
		_changesetBaselineCollectionLocalService = changesetBaselineCollectionLocalService;
	}

	private ChangesetBaselineCollectionLocalService _changesetBaselineCollectionLocalService;
}
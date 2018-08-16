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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ChangesetBaselineCollection. This utility wraps
 * {@link com.liferay.changeset.service.impl.ChangesetBaselineCollectionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetBaselineCollectionLocalService
 * @see com.liferay.changeset.service.base.ChangesetBaselineCollectionLocalServiceBaseImpl
 * @see com.liferay.changeset.service.impl.ChangesetBaselineCollectionLocalServiceImpl
 * @generated
 */
@ProviderType
public class ChangesetBaselineCollectionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.changeset.service.impl.ChangesetBaselineCollectionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the changeset baseline collection to the database. Also notifies the appropriate model listeners.
	*
	* @param changesetBaselineCollection the changeset baseline collection
	* @return the changeset baseline collection that was added
	*/
	public static com.liferay.changeset.model.ChangesetBaselineCollection addChangesetBaselineCollection(
		com.liferay.changeset.model.ChangesetBaselineCollection changesetBaselineCollection) {
		return getService()
				   .addChangesetBaselineCollection(changesetBaselineCollection);
	}

	public static com.liferay.changeset.model.ChangesetBaselineCollection addChangesetBaselineCollection(
		long userId, String name) {
		return getService().addChangesetBaselineCollection(userId, name);
	}

	/**
	* Creates a new changeset baseline collection with the primary key. Does not add the changeset baseline collection to the database.
	*
	* @param changesetBaselineCollectionId the primary key for the new changeset baseline collection
	* @return the new changeset baseline collection
	*/
	public static com.liferay.changeset.model.ChangesetBaselineCollection createChangesetBaselineCollection(
		long changesetBaselineCollectionId) {
		return getService()
				   .createChangesetBaselineCollection(changesetBaselineCollectionId);
	}

	/**
	* Deletes the changeset baseline collection from the database. Also notifies the appropriate model listeners.
	*
	* @param changesetBaselineCollection the changeset baseline collection
	* @return the changeset baseline collection that was removed
	*/
	public static com.liferay.changeset.model.ChangesetBaselineCollection deleteChangesetBaselineCollection(
		com.liferay.changeset.model.ChangesetBaselineCollection changesetBaselineCollection) {
		return getService()
				   .deleteChangesetBaselineCollection(changesetBaselineCollection);
	}

	/**
	* Deletes the changeset baseline collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changesetBaselineCollectionId the primary key of the changeset baseline collection
	* @return the changeset baseline collection that was removed
	* @throws PortalException if a changeset baseline collection with the primary key could not be found
	*/
	public static com.liferay.changeset.model.ChangesetBaselineCollection deleteChangesetBaselineCollection(
		long changesetBaselineCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteChangesetBaselineCollection(changesetBaselineCollectionId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.changeset.model.ChangesetBaselineCollection fetchChangesetBaselineCollection(
		long changesetBaselineCollectionId) {
		return getService()
				   .fetchChangesetBaselineCollection(changesetBaselineCollectionId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the changeset baseline collection with the primary key.
	*
	* @param changesetBaselineCollectionId the primary key of the changeset baseline collection
	* @return the changeset baseline collection
	* @throws PortalException if a changeset baseline collection with the primary key could not be found
	*/
	public static com.liferay.changeset.model.ChangesetBaselineCollection getChangesetBaselineCollection(
		long changesetBaselineCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getChangesetBaselineCollection(changesetBaselineCollectionId);
	}

	public static java.util.Optional<com.liferay.changeset.model.ChangesetBaselineCollection> getChangesetBaselineCollectionByName(
		String name) {
		return getService().getChangesetBaselineCollectionByName(name);
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
	public static java.util.List<com.liferay.changeset.model.ChangesetBaselineCollection> getChangesetBaselineCollections(
		int start, int end) {
		return getService().getChangesetBaselineCollections(start, end);
	}

	/**
	* Returns the number of changeset baseline collections.
	*
	* @return the number of changeset baseline collections
	*/
	public static int getChangesetBaselineCollectionsCount() {
		return getService().getChangesetBaselineCollectionsCount();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the changeset baseline collection in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param changesetBaselineCollection the changeset baseline collection
	* @return the changeset baseline collection that was updated
	*/
	public static com.liferay.changeset.model.ChangesetBaselineCollection updateChangesetBaselineCollection(
		com.liferay.changeset.model.ChangesetBaselineCollection changesetBaselineCollection) {
		return getService()
				   .updateChangesetBaselineCollection(changesetBaselineCollection);
	}

	public static ChangesetBaselineCollectionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ChangesetBaselineCollectionLocalService, ChangesetBaselineCollectionLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ChangesetBaselineCollectionLocalService.class);

		ServiceTracker<ChangesetBaselineCollectionLocalService, ChangesetBaselineCollectionLocalService> serviceTracker =
			new ServiceTracker<ChangesetBaselineCollectionLocalService, ChangesetBaselineCollectionLocalService>(bundle.getBundleContext(),
				ChangesetBaselineCollectionLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
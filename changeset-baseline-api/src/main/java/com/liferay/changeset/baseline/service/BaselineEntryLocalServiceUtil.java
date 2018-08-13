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

package com.liferay.changeset.baseline.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for BaselineEntry. This utility wraps
 * {@link com.liferay.changeset.baseline.service.impl.BaselineEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see BaselineEntryLocalService
 * @see com.liferay.changeset.baseline.service.base.BaselineEntryLocalServiceBaseImpl
 * @see com.liferay.changeset.baseline.service.impl.BaselineEntryLocalServiceImpl
 * @generated
 */
@ProviderType
public class BaselineEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.changeset.baseline.service.impl.BaselineEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the baseline entry to the database. Also notifies the appropriate model listeners.
	*
	* @param baselineEntry the baseline entry
	* @return the baseline entry that was added
	*/
	public static com.liferay.changeset.baseline.model.BaselineEntry addBaselineEntry(
		com.liferay.changeset.baseline.model.BaselineEntry baselineEntry) {
		return getService().addBaselineEntry(baselineEntry);
	}

	public static com.liferay.changeset.baseline.model.BaselineEntry addBaselineEntry(
		long baselineInformationId, long classNameId, long classPK,
		double version) {
		return getService()
				   .addBaselineEntry(baselineInformationId, classNameId,
			classPK, version);
	}

	/**
	* Creates a new baseline entry with the primary key. Does not add the baseline entry to the database.
	*
	* @param baselineEntryId the primary key for the new baseline entry
	* @return the new baseline entry
	*/
	public static com.liferay.changeset.baseline.model.BaselineEntry createBaselineEntry(
		long baselineEntryId) {
		return getService().createBaselineEntry(baselineEntryId);
	}

	/**
	* Deletes the baseline entry from the database. Also notifies the appropriate model listeners.
	*
	* @param baselineEntry the baseline entry
	* @return the baseline entry that was removed
	*/
	public static com.liferay.changeset.baseline.model.BaselineEntry deleteBaselineEntry(
		com.liferay.changeset.baseline.model.BaselineEntry baselineEntry) {
		return getService().deleteBaselineEntry(baselineEntry);
	}

	/**
	* Deletes the baseline entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param baselineEntryId the primary key of the baseline entry
	* @return the baseline entry that was removed
	* @throws PortalException if a baseline entry with the primary key could not be found
	*/
	public static com.liferay.changeset.baseline.model.BaselineEntry deleteBaselineEntry(
		long baselineEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteBaselineEntry(baselineEntryId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.baseline.model.impl.BaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.baseline.model.impl.BaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.changeset.baseline.model.BaselineEntry fetchBaselineEntry(
		long baselineEntryId) {
		return getService().fetchBaselineEntry(baselineEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the baseline entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.baseline.model.impl.BaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of baseline entries
	* @param end the upper bound of the range of baseline entries (not inclusive)
	* @return the range of baseline entries
	*/
	public static java.util.List<com.liferay.changeset.baseline.model.BaselineEntry> getBaselineEntries(
		int start, int end) {
		return getService().getBaselineEntries(start, end);
	}

	/**
	* Returns the number of baseline entries.
	*
	* @return the number of baseline entries
	*/
	public static int getBaselineEntriesCount() {
		return getService().getBaselineEntriesCount();
	}

	/**
	* Returns the baseline entry with the primary key.
	*
	* @param baselineEntryId the primary key of the baseline entry
	* @return the baseline entry
	* @throws PortalException if a baseline entry with the primary key could not be found
	*/
	public static com.liferay.changeset.baseline.model.BaselineEntry getBaselineEntry(
		long baselineEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getBaselineEntry(baselineEntryId);
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
	* Updates the baseline entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param baselineEntry the baseline entry
	* @return the baseline entry that was updated
	*/
	public static com.liferay.changeset.baseline.model.BaselineEntry updateBaselineEntry(
		com.liferay.changeset.baseline.model.BaselineEntry baselineEntry) {
		return getService().updateBaselineEntry(baselineEntry);
	}

	public static BaselineEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<BaselineEntryLocalService, BaselineEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(BaselineEntryLocalService.class);

		ServiceTracker<BaselineEntryLocalService, BaselineEntryLocalService> serviceTracker =
			new ServiceTracker<BaselineEntryLocalService, BaselineEntryLocalService>(bundle.getBundleContext(),
				BaselineEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
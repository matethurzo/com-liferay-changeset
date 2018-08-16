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

package com.liferay.changeset.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.changeset.model.ChangesetBaselineEntry;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the changeset baseline entry service. This utility wraps {@link com.liferay.changeset.service.persistence.impl.ChangesetBaselineEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetBaselineEntryPersistence
 * @see com.liferay.changeset.service.persistence.impl.ChangesetBaselineEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class ChangesetBaselineEntryUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(ChangesetBaselineEntry changesetBaselineEntry) {
		getPersistence().clearCache(changesetBaselineEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ChangesetBaselineEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ChangesetBaselineEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ChangesetBaselineEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ChangesetBaselineEntry update(
		ChangesetBaselineEntry changesetBaselineEntry) {
		return getPersistence().update(changesetBaselineEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ChangesetBaselineEntry update(
		ChangesetBaselineEntry changesetBaselineEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(changesetBaselineEntry, serviceContext);
	}

	/**
	* Caches the changeset baseline entry in the entity cache if it is enabled.
	*
	* @param changesetBaselineEntry the changeset baseline entry
	*/
	public static void cacheResult(
		ChangesetBaselineEntry changesetBaselineEntry) {
		getPersistence().cacheResult(changesetBaselineEntry);
	}

	/**
	* Caches the changeset baseline entries in the entity cache if it is enabled.
	*
	* @param changesetBaselineEntries the changeset baseline entries
	*/
	public static void cacheResult(
		List<ChangesetBaselineEntry> changesetBaselineEntries) {
		getPersistence().cacheResult(changesetBaselineEntries);
	}

	/**
	* Creates a new changeset baseline entry with the primary key. Does not add the changeset baseline entry to the database.
	*
	* @param changesetBaselineEntryId the primary key for the new changeset baseline entry
	* @return the new changeset baseline entry
	*/
	public static ChangesetBaselineEntry create(long changesetBaselineEntryId) {
		return getPersistence().create(changesetBaselineEntryId);
	}

	/**
	* Removes the changeset baseline entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changesetBaselineEntryId the primary key of the changeset baseline entry
	* @return the changeset baseline entry that was removed
	* @throws NoSuchBaselineEntryException if a changeset baseline entry with the primary key could not be found
	*/
	public static ChangesetBaselineEntry remove(long changesetBaselineEntryId)
		throws com.liferay.changeset.exception.NoSuchBaselineEntryException {
		return getPersistence().remove(changesetBaselineEntryId);
	}

	public static ChangesetBaselineEntry updateImpl(
		ChangesetBaselineEntry changesetBaselineEntry) {
		return getPersistence().updateImpl(changesetBaselineEntry);
	}

	/**
	* Returns the changeset baseline entry with the primary key or throws a {@link NoSuchBaselineEntryException} if it could not be found.
	*
	* @param changesetBaselineEntryId the primary key of the changeset baseline entry
	* @return the changeset baseline entry
	* @throws NoSuchBaselineEntryException if a changeset baseline entry with the primary key could not be found
	*/
	public static ChangesetBaselineEntry findByPrimaryKey(
		long changesetBaselineEntryId)
		throws com.liferay.changeset.exception.NoSuchBaselineEntryException {
		return getPersistence().findByPrimaryKey(changesetBaselineEntryId);
	}

	/**
	* Returns the changeset baseline entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param changesetBaselineEntryId the primary key of the changeset baseline entry
	* @return the changeset baseline entry, or <code>null</code> if a changeset baseline entry with the primary key could not be found
	*/
	public static ChangesetBaselineEntry fetchByPrimaryKey(
		long changesetBaselineEntryId) {
		return getPersistence().fetchByPrimaryKey(changesetBaselineEntryId);
	}

	public static java.util.Map<java.io.Serializable, ChangesetBaselineEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the changeset baseline entries.
	*
	* @return the changeset baseline entries
	*/
	public static List<ChangesetBaselineEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the changeset baseline entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetBaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of changeset baseline entries
	* @param end the upper bound of the range of changeset baseline entries (not inclusive)
	* @return the range of changeset baseline entries
	*/
	public static List<ChangesetBaselineEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the changeset baseline entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetBaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of changeset baseline entries
	* @param end the upper bound of the range of changeset baseline entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of changeset baseline entries
	*/
	public static List<ChangesetBaselineEntry> findAll(int start, int end,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the changeset baseline entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetBaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of changeset baseline entries
	* @param end the upper bound of the range of changeset baseline entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of changeset baseline entries
	*/
	public static List<ChangesetBaselineEntry> findAll(int start, int end,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the changeset baseline entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of changeset baseline entries.
	*
	* @return the number of changeset baseline entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ChangesetBaselineEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ChangesetBaselineEntryPersistence, ChangesetBaselineEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ChangesetBaselineEntryPersistence.class);

		ServiceTracker<ChangesetBaselineEntryPersistence, ChangesetBaselineEntryPersistence> serviceTracker =
			new ServiceTracker<ChangesetBaselineEntryPersistence, ChangesetBaselineEntryPersistence>(bundle.getBundleContext(),
				ChangesetBaselineEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
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

package com.liferay.changeset.baseline.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.changeset.baseline.model.BaselineEntry;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the baseline entry service. This utility wraps {@link com.liferay.changeset.baseline.service.persistence.impl.BaselineEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BaselineEntryPersistence
 * @see com.liferay.changeset.baseline.service.persistence.impl.BaselineEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class BaselineEntryUtil {
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
	public static void clearCache(BaselineEntry baselineEntry) {
		getPersistence().clearCache(baselineEntry);
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
	public static List<BaselineEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<BaselineEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<BaselineEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<BaselineEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static BaselineEntry update(BaselineEntry baselineEntry) {
		return getPersistence().update(baselineEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static BaselineEntry update(BaselineEntry baselineEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(baselineEntry, serviceContext);
	}

	/**
	* Caches the baseline entry in the entity cache if it is enabled.
	*
	* @param baselineEntry the baseline entry
	*/
	public static void cacheResult(BaselineEntry baselineEntry) {
		getPersistence().cacheResult(baselineEntry);
	}

	/**
	* Caches the baseline entries in the entity cache if it is enabled.
	*
	* @param baselineEntries the baseline entries
	*/
	public static void cacheResult(List<BaselineEntry> baselineEntries) {
		getPersistence().cacheResult(baselineEntries);
	}

	/**
	* Creates a new baseline entry with the primary key. Does not add the baseline entry to the database.
	*
	* @param baselineEntryId the primary key for the new baseline entry
	* @return the new baseline entry
	*/
	public static BaselineEntry create(long baselineEntryId) {
		return getPersistence().create(baselineEntryId);
	}

	/**
	* Removes the baseline entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param baselineEntryId the primary key of the baseline entry
	* @return the baseline entry that was removed
	* @throws NoSuchBaselineEntryException if a baseline entry with the primary key could not be found
	*/
	public static BaselineEntry remove(long baselineEntryId)
		throws com.liferay.changeset.baseline.exception.NoSuchBaselineEntryException {
		return getPersistence().remove(baselineEntryId);
	}

	public static BaselineEntry updateImpl(BaselineEntry baselineEntry) {
		return getPersistence().updateImpl(baselineEntry);
	}

	/**
	* Returns the baseline entry with the primary key or throws a {@link NoSuchBaselineEntryException} if it could not be found.
	*
	* @param baselineEntryId the primary key of the baseline entry
	* @return the baseline entry
	* @throws NoSuchBaselineEntryException if a baseline entry with the primary key could not be found
	*/
	public static BaselineEntry findByPrimaryKey(long baselineEntryId)
		throws com.liferay.changeset.baseline.exception.NoSuchBaselineEntryException {
		return getPersistence().findByPrimaryKey(baselineEntryId);
	}

	/**
	* Returns the baseline entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param baselineEntryId the primary key of the baseline entry
	* @return the baseline entry, or <code>null</code> if a baseline entry with the primary key could not be found
	*/
	public static BaselineEntry fetchByPrimaryKey(long baselineEntryId) {
		return getPersistence().fetchByPrimaryKey(baselineEntryId);
	}

	public static java.util.Map<java.io.Serializable, BaselineEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the baseline entries.
	*
	* @return the baseline entries
	*/
	public static List<BaselineEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the baseline entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of baseline entries
	* @param end the upper bound of the range of baseline entries (not inclusive)
	* @return the range of baseline entries
	*/
	public static List<BaselineEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the baseline entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of baseline entries
	* @param end the upper bound of the range of baseline entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of baseline entries
	*/
	public static List<BaselineEntry> findAll(int start, int end,
		OrderByComparator<BaselineEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the baseline entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of baseline entries
	* @param end the upper bound of the range of baseline entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of baseline entries
	*/
	public static List<BaselineEntry> findAll(int start, int end,
		OrderByComparator<BaselineEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the baseline entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of baseline entries.
	*
	* @return the number of baseline entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static BaselineEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<BaselineEntryPersistence, BaselineEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(BaselineEntryPersistence.class);

		ServiceTracker<BaselineEntryPersistence, BaselineEntryPersistence> serviceTracker =
			new ServiceTracker<BaselineEntryPersistence, BaselineEntryPersistence>(bundle.getBundleContext(),
				BaselineEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
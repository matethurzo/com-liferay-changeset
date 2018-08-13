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

import com.liferay.changeset.baseline.model.BaselineInformation;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the baseline information service. This utility wraps {@link com.liferay.changeset.baseline.service.persistence.impl.BaselineInformationPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BaselineInformationPersistence
 * @see com.liferay.changeset.baseline.service.persistence.impl.BaselineInformationPersistenceImpl
 * @generated
 */
@ProviderType
public class BaselineInformationUtil {
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
	public static void clearCache(BaselineInformation baselineInformation) {
		getPersistence().clearCache(baselineInformation);
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
	public static List<BaselineInformation> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<BaselineInformation> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<BaselineInformation> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<BaselineInformation> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static BaselineInformation update(
		BaselineInformation baselineInformation) {
		return getPersistence().update(baselineInformation);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static BaselineInformation update(
		BaselineInformation baselineInformation, ServiceContext serviceContext) {
		return getPersistence().update(baselineInformation, serviceContext);
	}

	/**
	* Returns the baseline information where name = &#63; or throws a {@link NoSuchBaselineInformationException} if it could not be found.
	*
	* @param name the name
	* @return the matching baseline information
	* @throws NoSuchBaselineInformationException if a matching baseline information could not be found
	*/
	public static BaselineInformation findByName(String name)
		throws com.liferay.changeset.baseline.exception.NoSuchBaselineInformationException {
		return getPersistence().findByName(name);
	}

	/**
	* Returns the baseline information where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param name the name
	* @return the matching baseline information, or <code>null</code> if a matching baseline information could not be found
	*/
	public static BaselineInformation fetchByName(String name) {
		return getPersistence().fetchByName(name);
	}

	/**
	* Returns the baseline information where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching baseline information, or <code>null</code> if a matching baseline information could not be found
	*/
	public static BaselineInformation fetchByName(String name,
		boolean retrieveFromCache) {
		return getPersistence().fetchByName(name, retrieveFromCache);
	}

	/**
	* Removes the baseline information where name = &#63; from the database.
	*
	* @param name the name
	* @return the baseline information that was removed
	*/
	public static BaselineInformation removeByName(String name)
		throws com.liferay.changeset.baseline.exception.NoSuchBaselineInformationException {
		return getPersistence().removeByName(name);
	}

	/**
	* Returns the number of baseline informations where name = &#63;.
	*
	* @param name the name
	* @return the number of matching baseline informations
	*/
	public static int countByName(String name) {
		return getPersistence().countByName(name);
	}

	/**
	* Caches the baseline information in the entity cache if it is enabled.
	*
	* @param baselineInformation the baseline information
	*/
	public static void cacheResult(BaselineInformation baselineInformation) {
		getPersistence().cacheResult(baselineInformation);
	}

	/**
	* Caches the baseline informations in the entity cache if it is enabled.
	*
	* @param baselineInformations the baseline informations
	*/
	public static void cacheResult(
		List<BaselineInformation> baselineInformations) {
		getPersistence().cacheResult(baselineInformations);
	}

	/**
	* Creates a new baseline information with the primary key. Does not add the baseline information to the database.
	*
	* @param baselineInformationId the primary key for the new baseline information
	* @return the new baseline information
	*/
	public static BaselineInformation create(long baselineInformationId) {
		return getPersistence().create(baselineInformationId);
	}

	/**
	* Removes the baseline information with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param baselineInformationId the primary key of the baseline information
	* @return the baseline information that was removed
	* @throws NoSuchBaselineInformationException if a baseline information with the primary key could not be found
	*/
	public static BaselineInformation remove(long baselineInformationId)
		throws com.liferay.changeset.baseline.exception.NoSuchBaselineInformationException {
		return getPersistence().remove(baselineInformationId);
	}

	public static BaselineInformation updateImpl(
		BaselineInformation baselineInformation) {
		return getPersistence().updateImpl(baselineInformation);
	}

	/**
	* Returns the baseline information with the primary key or throws a {@link NoSuchBaselineInformationException} if it could not be found.
	*
	* @param baselineInformationId the primary key of the baseline information
	* @return the baseline information
	* @throws NoSuchBaselineInformationException if a baseline information with the primary key could not be found
	*/
	public static BaselineInformation findByPrimaryKey(
		long baselineInformationId)
		throws com.liferay.changeset.baseline.exception.NoSuchBaselineInformationException {
		return getPersistence().findByPrimaryKey(baselineInformationId);
	}

	/**
	* Returns the baseline information with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param baselineInformationId the primary key of the baseline information
	* @return the baseline information, or <code>null</code> if a baseline information with the primary key could not be found
	*/
	public static BaselineInformation fetchByPrimaryKey(
		long baselineInformationId) {
		return getPersistence().fetchByPrimaryKey(baselineInformationId);
	}

	public static java.util.Map<java.io.Serializable, BaselineInformation> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the baseline informations.
	*
	* @return the baseline informations
	*/
	public static List<BaselineInformation> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the baseline informations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BaselineInformationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of baseline informations
	* @param end the upper bound of the range of baseline informations (not inclusive)
	* @return the range of baseline informations
	*/
	public static List<BaselineInformation> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the baseline informations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BaselineInformationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of baseline informations
	* @param end the upper bound of the range of baseline informations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of baseline informations
	*/
	public static List<BaselineInformation> findAll(int start, int end,
		OrderByComparator<BaselineInformation> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the baseline informations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link BaselineInformationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of baseline informations
	* @param end the upper bound of the range of baseline informations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of baseline informations
	*/
	public static List<BaselineInformation> findAll(int start, int end,
		OrderByComparator<BaselineInformation> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the baseline informations from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of baseline informations.
	*
	* @return the number of baseline informations
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static BaselineInformationPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<BaselineInformationPersistence, BaselineInformationPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(BaselineInformationPersistence.class);

		ServiceTracker<BaselineInformationPersistence, BaselineInformationPersistence> serviceTracker =
			new ServiceTracker<BaselineInformationPersistence, BaselineInformationPersistence>(bundle.getBundleContext(),
				BaselineInformationPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
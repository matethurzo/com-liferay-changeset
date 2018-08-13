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

import com.liferay.changeset.baseline.exception.NoSuchBaselineInformationException;
import com.liferay.changeset.baseline.model.BaselineInformation;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the baseline information service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.changeset.baseline.service.persistence.impl.BaselineInformationPersistenceImpl
 * @see BaselineInformationUtil
 * @generated
 */
@ProviderType
public interface BaselineInformationPersistence extends BasePersistence<BaselineInformation> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BaselineInformationUtil} to access the baseline information persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the baseline information where name = &#63; or throws a {@link NoSuchBaselineInformationException} if it could not be found.
	*
	* @param name the name
	* @return the matching baseline information
	* @throws NoSuchBaselineInformationException if a matching baseline information could not be found
	*/
	public BaselineInformation findByName(String name)
		throws NoSuchBaselineInformationException;

	/**
	* Returns the baseline information where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param name the name
	* @return the matching baseline information, or <code>null</code> if a matching baseline information could not be found
	*/
	public BaselineInformation fetchByName(String name);

	/**
	* Returns the baseline information where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching baseline information, or <code>null</code> if a matching baseline information could not be found
	*/
	public BaselineInformation fetchByName(String name,
		boolean retrieveFromCache);

	/**
	* Removes the baseline information where name = &#63; from the database.
	*
	* @param name the name
	* @return the baseline information that was removed
	*/
	public BaselineInformation removeByName(String name)
		throws NoSuchBaselineInformationException;

	/**
	* Returns the number of baseline informations where name = &#63;.
	*
	* @param name the name
	* @return the number of matching baseline informations
	*/
	public int countByName(String name);

	/**
	* Caches the baseline information in the entity cache if it is enabled.
	*
	* @param baselineInformation the baseline information
	*/
	public void cacheResult(BaselineInformation baselineInformation);

	/**
	* Caches the baseline informations in the entity cache if it is enabled.
	*
	* @param baselineInformations the baseline informations
	*/
	public void cacheResult(
		java.util.List<BaselineInformation> baselineInformations);

	/**
	* Creates a new baseline information with the primary key. Does not add the baseline information to the database.
	*
	* @param baselineInformationId the primary key for the new baseline information
	* @return the new baseline information
	*/
	public BaselineInformation create(long baselineInformationId);

	/**
	* Removes the baseline information with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param baselineInformationId the primary key of the baseline information
	* @return the baseline information that was removed
	* @throws NoSuchBaselineInformationException if a baseline information with the primary key could not be found
	*/
	public BaselineInformation remove(long baselineInformationId)
		throws NoSuchBaselineInformationException;

	public BaselineInformation updateImpl(
		BaselineInformation baselineInformation);

	/**
	* Returns the baseline information with the primary key or throws a {@link NoSuchBaselineInformationException} if it could not be found.
	*
	* @param baselineInformationId the primary key of the baseline information
	* @return the baseline information
	* @throws NoSuchBaselineInformationException if a baseline information with the primary key could not be found
	*/
	public BaselineInformation findByPrimaryKey(long baselineInformationId)
		throws NoSuchBaselineInformationException;

	/**
	* Returns the baseline information with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param baselineInformationId the primary key of the baseline information
	* @return the baseline information, or <code>null</code> if a baseline information with the primary key could not be found
	*/
	public BaselineInformation fetchByPrimaryKey(long baselineInformationId);

	@Override
	public java.util.Map<java.io.Serializable, BaselineInformation> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the baseline informations.
	*
	* @return the baseline informations
	*/
	public java.util.List<BaselineInformation> findAll();

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
	public java.util.List<BaselineInformation> findAll(int start, int end);

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
	public java.util.List<BaselineInformation> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BaselineInformation> orderByComparator);

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
	public java.util.List<BaselineInformation> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BaselineInformation> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the baseline informations from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of baseline informations.
	*
	* @return the number of baseline informations
	*/
	public int countAll();
}
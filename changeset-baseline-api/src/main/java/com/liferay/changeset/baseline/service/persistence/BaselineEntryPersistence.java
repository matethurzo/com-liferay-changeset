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

import com.liferay.changeset.baseline.exception.NoSuchBaselineEntryException;
import com.liferay.changeset.baseline.model.BaselineEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the baseline entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.changeset.baseline.service.persistence.impl.BaselineEntryPersistenceImpl
 * @see BaselineEntryUtil
 * @generated
 */
@ProviderType
public interface BaselineEntryPersistence extends BasePersistence<BaselineEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BaselineEntryUtil} to access the baseline entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the baseline entry in the entity cache if it is enabled.
	*
	* @param baselineEntry the baseline entry
	*/
	public void cacheResult(BaselineEntry baselineEntry);

	/**
	* Caches the baseline entries in the entity cache if it is enabled.
	*
	* @param baselineEntries the baseline entries
	*/
	public void cacheResult(java.util.List<BaselineEntry> baselineEntries);

	/**
	* Creates a new baseline entry with the primary key. Does not add the baseline entry to the database.
	*
	* @param baselineEntryId the primary key for the new baseline entry
	* @return the new baseline entry
	*/
	public BaselineEntry create(long baselineEntryId);

	/**
	* Removes the baseline entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param baselineEntryId the primary key of the baseline entry
	* @return the baseline entry that was removed
	* @throws NoSuchBaselineEntryException if a baseline entry with the primary key could not be found
	*/
	public BaselineEntry remove(long baselineEntryId)
		throws NoSuchBaselineEntryException;

	public BaselineEntry updateImpl(BaselineEntry baselineEntry);

	/**
	* Returns the baseline entry with the primary key or throws a {@link NoSuchBaselineEntryException} if it could not be found.
	*
	* @param baselineEntryId the primary key of the baseline entry
	* @return the baseline entry
	* @throws NoSuchBaselineEntryException if a baseline entry with the primary key could not be found
	*/
	public BaselineEntry findByPrimaryKey(long baselineEntryId)
		throws NoSuchBaselineEntryException;

	/**
	* Returns the baseline entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param baselineEntryId the primary key of the baseline entry
	* @return the baseline entry, or <code>null</code> if a baseline entry with the primary key could not be found
	*/
	public BaselineEntry fetchByPrimaryKey(long baselineEntryId);

	@Override
	public java.util.Map<java.io.Serializable, BaselineEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the baseline entries.
	*
	* @return the baseline entries
	*/
	public java.util.List<BaselineEntry> findAll();

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
	public java.util.List<BaselineEntry> findAll(int start, int end);

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
	public java.util.List<BaselineEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BaselineEntry> orderByComparator);

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
	public java.util.List<BaselineEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<BaselineEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the baseline entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of baseline entries.
	*
	* @return the number of baseline entries
	*/
	public int countAll();
}
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

import com.liferay.changeset.exception.NoSuchBaselineEntryException;
import com.liferay.changeset.model.ChangesetBaselineEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the changeset baseline entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.changeset.service.persistence.impl.ChangesetBaselineEntryPersistenceImpl
 * @see ChangesetBaselineEntryUtil
 * @generated
 */
@ProviderType
public interface ChangesetBaselineEntryPersistence extends BasePersistence<ChangesetBaselineEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ChangesetBaselineEntryUtil} to access the changeset baseline entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the changeset baseline entry in the entity cache if it is enabled.
	*
	* @param changesetBaselineEntry the changeset baseline entry
	*/
	public void cacheResult(ChangesetBaselineEntry changesetBaselineEntry);

	/**
	* Caches the changeset baseline entries in the entity cache if it is enabled.
	*
	* @param changesetBaselineEntries the changeset baseline entries
	*/
	public void cacheResult(
		java.util.List<ChangesetBaselineEntry> changesetBaselineEntries);

	/**
	* Creates a new changeset baseline entry with the primary key. Does not add the changeset baseline entry to the database.
	*
	* @param changesetBaselineEntryId the primary key for the new changeset baseline entry
	* @return the new changeset baseline entry
	*/
	public ChangesetBaselineEntry create(long changesetBaselineEntryId);

	/**
	* Removes the changeset baseline entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changesetBaselineEntryId the primary key of the changeset baseline entry
	* @return the changeset baseline entry that was removed
	* @throws NoSuchBaselineEntryException if a changeset baseline entry with the primary key could not be found
	*/
	public ChangesetBaselineEntry remove(long changesetBaselineEntryId)
		throws NoSuchBaselineEntryException;

	public ChangesetBaselineEntry updateImpl(
		ChangesetBaselineEntry changesetBaselineEntry);

	/**
	* Returns the changeset baseline entry with the primary key or throws a {@link NoSuchBaselineEntryException} if it could not be found.
	*
	* @param changesetBaselineEntryId the primary key of the changeset baseline entry
	* @return the changeset baseline entry
	* @throws NoSuchBaselineEntryException if a changeset baseline entry with the primary key could not be found
	*/
	public ChangesetBaselineEntry findByPrimaryKey(
		long changesetBaselineEntryId) throws NoSuchBaselineEntryException;

	/**
	* Returns the changeset baseline entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param changesetBaselineEntryId the primary key of the changeset baseline entry
	* @return the changeset baseline entry, or <code>null</code> if a changeset baseline entry with the primary key could not be found
	*/
	public ChangesetBaselineEntry fetchByPrimaryKey(
		long changesetBaselineEntryId);

	@Override
	public java.util.Map<java.io.Serializable, ChangesetBaselineEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the changeset baseline entries.
	*
	* @return the changeset baseline entries
	*/
	public java.util.List<ChangesetBaselineEntry> findAll();

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
	public java.util.List<ChangesetBaselineEntry> findAll(int start, int end);

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
	public java.util.List<ChangesetBaselineEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetBaselineEntry> orderByComparator);

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
	public java.util.List<ChangesetBaselineEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetBaselineEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the changeset baseline entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of changeset baseline entries.
	*
	* @return the number of changeset baseline entries
	*/
	public int countAll();
}
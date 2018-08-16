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

import com.liferay.changeset.exception.NoSuchBaselineCollectionException;
import com.liferay.changeset.model.ChangesetBaselineCollection;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the changeset baseline collection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.changeset.service.persistence.impl.ChangesetBaselineCollectionPersistenceImpl
 * @see ChangesetBaselineCollectionUtil
 * @generated
 */
@ProviderType
public interface ChangesetBaselineCollectionPersistence extends BasePersistence<ChangesetBaselineCollection> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ChangesetBaselineCollectionUtil} to access the changeset baseline collection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the changeset baseline collection where name = &#63; or throws a {@link NoSuchBaselineCollectionException} if it could not be found.
	*
	* @param name the name
	* @return the matching changeset baseline collection
	* @throws NoSuchBaselineCollectionException if a matching changeset baseline collection could not be found
	*/
	public ChangesetBaselineCollection findByName(String name)
		throws NoSuchBaselineCollectionException;

	/**
	* Returns the changeset baseline collection where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param name the name
	* @return the matching changeset baseline collection, or <code>null</code> if a matching changeset baseline collection could not be found
	*/
	public ChangesetBaselineCollection fetchByName(String name);

	/**
	* Returns the changeset baseline collection where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching changeset baseline collection, or <code>null</code> if a matching changeset baseline collection could not be found
	*/
	public ChangesetBaselineCollection fetchByName(String name,
		boolean retrieveFromCache);

	/**
	* Removes the changeset baseline collection where name = &#63; from the database.
	*
	* @param name the name
	* @return the changeset baseline collection that was removed
	*/
	public ChangesetBaselineCollection removeByName(String name)
		throws NoSuchBaselineCollectionException;

	/**
	* Returns the number of changeset baseline collections where name = &#63;.
	*
	* @param name the name
	* @return the number of matching changeset baseline collections
	*/
	public int countByName(String name);

	/**
	* Caches the changeset baseline collection in the entity cache if it is enabled.
	*
	* @param changesetBaselineCollection the changeset baseline collection
	*/
	public void cacheResult(
		ChangesetBaselineCollection changesetBaselineCollection);

	/**
	* Caches the changeset baseline collections in the entity cache if it is enabled.
	*
	* @param changesetBaselineCollections the changeset baseline collections
	*/
	public void cacheResult(
		java.util.List<ChangesetBaselineCollection> changesetBaselineCollections);

	/**
	* Creates a new changeset baseline collection with the primary key. Does not add the changeset baseline collection to the database.
	*
	* @param changesetBaselineCollectionId the primary key for the new changeset baseline collection
	* @return the new changeset baseline collection
	*/
	public ChangesetBaselineCollection create(
		long changesetBaselineCollectionId);

	/**
	* Removes the changeset baseline collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changesetBaselineCollectionId the primary key of the changeset baseline collection
	* @return the changeset baseline collection that was removed
	* @throws NoSuchBaselineCollectionException if a changeset baseline collection with the primary key could not be found
	*/
	public ChangesetBaselineCollection remove(
		long changesetBaselineCollectionId)
		throws NoSuchBaselineCollectionException;

	public ChangesetBaselineCollection updateImpl(
		ChangesetBaselineCollection changesetBaselineCollection);

	/**
	* Returns the changeset baseline collection with the primary key or throws a {@link NoSuchBaselineCollectionException} if it could not be found.
	*
	* @param changesetBaselineCollectionId the primary key of the changeset baseline collection
	* @return the changeset baseline collection
	* @throws NoSuchBaselineCollectionException if a changeset baseline collection with the primary key could not be found
	*/
	public ChangesetBaselineCollection findByPrimaryKey(
		long changesetBaselineCollectionId)
		throws NoSuchBaselineCollectionException;

	/**
	* Returns the changeset baseline collection with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param changesetBaselineCollectionId the primary key of the changeset baseline collection
	* @return the changeset baseline collection, or <code>null</code> if a changeset baseline collection with the primary key could not be found
	*/
	public ChangesetBaselineCollection fetchByPrimaryKey(
		long changesetBaselineCollectionId);

	@Override
	public java.util.Map<java.io.Serializable, ChangesetBaselineCollection> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the changeset baseline collections.
	*
	* @return the changeset baseline collections
	*/
	public java.util.List<ChangesetBaselineCollection> findAll();

	/**
	* Returns a range of all the changeset baseline collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetBaselineCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of changeset baseline collections
	* @param end the upper bound of the range of changeset baseline collections (not inclusive)
	* @return the range of changeset baseline collections
	*/
	public java.util.List<ChangesetBaselineCollection> findAll(int start,
		int end);

	/**
	* Returns an ordered range of all the changeset baseline collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetBaselineCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of changeset baseline collections
	* @param end the upper bound of the range of changeset baseline collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of changeset baseline collections
	*/
	public java.util.List<ChangesetBaselineCollection> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetBaselineCollection> orderByComparator);

	/**
	* Returns an ordered range of all the changeset baseline collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetBaselineCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of changeset baseline collections
	* @param end the upper bound of the range of changeset baseline collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of changeset baseline collections
	*/
	public java.util.List<ChangesetBaselineCollection> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<ChangesetBaselineCollection> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the changeset baseline collections from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of changeset baseline collections.
	*
	* @return the number of changeset baseline collections
	*/
	public int countAll();
}
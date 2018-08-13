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

import com.liferay.changeset.exception.NoSuchEntryException;
import com.liferay.changeset.model.ChangesetEntry;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Set;

/**
 * Provides the local service interface for ChangesetEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetEntryLocalServiceUtil
 * @see com.liferay.changeset.service.base.ChangesetEntryLocalServiceBaseImpl
 * @see com.liferay.changeset.service.impl.ChangesetEntryLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ChangesetEntryLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ChangesetEntryLocalServiceUtil} to access the changeset entry local service. Add custom service methods to {@link com.liferay.changeset.service.impl.ChangesetEntryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the changeset entry to the database. Also notifies the appropriate model listeners.
	*
	* @param changesetEntry the changeset entry
	* @return the changeset entry that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public ChangesetEntry addChangesetEntry(ChangesetEntry changesetEntry);

	public ChangesetEntry addChangesetEntry(long userId,
		long changesetCollectionId, long classNameId, long classPK)
		throws PortalException;

	/**
	* Creates a new changeset entry with the primary key. Does not add the changeset entry to the database.
	*
	* @param changesetEntryId the primary key for the new changeset entry
	* @return the new changeset entry
	*/
	@Transactional(enabled = false)
	public ChangesetEntry createChangesetEntry(long changesetEntryId);

	public void deleteChangesetEntries(long changesetCollectionId)
		throws PortalException;

	public void deleteChangesetEntries(Set<Long> changesetEntryIds)
		throws PortalException;

	/**
	* Deletes the changeset entry from the database. Also notifies the appropriate model listeners.
	*
	* @param changesetEntry the changeset entry
	* @return the changeset entry that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public ChangesetEntry deleteChangesetEntry(ChangesetEntry changesetEntry);

	/**
	* Deletes the changeset entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changesetEntryId the primary key of the changeset entry
	* @return the changeset entry that was removed
	* @throws PortalException if a changeset entry with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public ChangesetEntry deleteChangesetEntry(long changesetEntryId)
		throws PortalException;

	public void deleteEntry(long changesetId, long classNameId, long classPK);

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	public DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.model.impl.ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.model.impl.ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ChangesetEntry fetchChangesetEntry(long changesetEntryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ChangesetEntry fetchChangesetEntry(long changesetCollectionId,
		long classNameId, long classPK);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ChangesetEntry fetchOrAddChangesetEntry(long changesetCollectionId,
		long classNameId, long classPK) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns a range of all the changeset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.model.impl.ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of changeset entries
	* @param end the upper bound of the range of changeset entries (not inclusive)
	* @return the range of changeset entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangesetEntry> getChangesetEntries(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ChangesetEntry> getChangesetEntries(
		long changesetCollectionId, long classNameId);

	/**
	* Returns the number of changeset entries.
	*
	* @return the number of changeset entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getChangesetEntriesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getChangesetEntriesCount(long changesetCollectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getChangesetEntriesCount(long changesetCollectionId,
		long classNameId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getChangesetEntriesCount(long changesetCollectionId,
		long classNameId, Set<Long> classPKs);

	/**
	* Returns the changeset entry with the primary key.
	*
	* @param changesetEntryId the primary key of the changeset entry
	* @return the changeset entry
	* @throws PortalException if a changeset entry with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ChangesetEntry getChangesetEntry(long changesetEntryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ChangesetEntry getChangesetEntry(long changesetCollectionId,
		long classNameId, long classPK) throws NoSuchEntryException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	* Updates the changeset entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param changesetEntry the changeset entry
	* @return the changeset entry that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public ChangesetEntry updateChangesetEntry(ChangesetEntry changesetEntry);
}
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

package com.liferay.changeset.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.changeset.exception.NoSuchBaselineCollectionException;
import com.liferay.changeset.model.ChangesetBaselineCollection;
import com.liferay.changeset.model.impl.ChangesetBaselineCollectionImpl;
import com.liferay.changeset.model.impl.ChangesetBaselineCollectionModelImpl;
import com.liferay.changeset.service.persistence.ChangesetBaselineCollectionPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the changeset baseline collection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetBaselineCollectionPersistence
 * @see com.liferay.changeset.service.persistence.ChangesetBaselineCollectionUtil
 * @generated
 */
@ProviderType
public class ChangesetBaselineCollectionPersistenceImpl
	extends BasePersistenceImpl<ChangesetBaselineCollection>
	implements ChangesetBaselineCollectionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ChangesetBaselineCollectionUtil} to access the changeset baseline collection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ChangesetBaselineCollectionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ChangesetBaselineCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineCollectionModelImpl.FINDER_CACHE_ENABLED,
			ChangesetBaselineCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ChangesetBaselineCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineCollectionModelImpl.FINDER_CACHE_ENABLED,
			ChangesetBaselineCollectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ChangesetBaselineCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineCollectionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_NAME = new FinderPath(ChangesetBaselineCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineCollectionModelImpl.FINDER_CACHE_ENABLED,
			ChangesetBaselineCollectionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByName", new String[] { String.class.getName() },
			ChangesetBaselineCollectionModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_NAME = new FinderPath(ChangesetBaselineCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineCollectionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByName", new String[] { String.class.getName() });

	/**
	 * Returns the changeset baseline collection where name = &#63; or throws a {@link NoSuchBaselineCollectionException} if it could not be found.
	 *
	 * @param name the name
	 * @return the matching changeset baseline collection
	 * @throws NoSuchBaselineCollectionException if a matching changeset baseline collection could not be found
	 */
	@Override
	public ChangesetBaselineCollection findByName(String name)
		throws NoSuchBaselineCollectionException {
		ChangesetBaselineCollection changesetBaselineCollection = fetchByName(name);

		if (changesetBaselineCollection == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("name=");
			msg.append(name);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchBaselineCollectionException(msg.toString());
		}

		return changesetBaselineCollection;
	}

	/**
	 * Returns the changeset baseline collection where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching changeset baseline collection, or <code>null</code> if a matching changeset baseline collection could not be found
	 */
	@Override
	public ChangesetBaselineCollection fetchByName(String name) {
		return fetchByName(name, true);
	}

	/**
	 * Returns the changeset baseline collection where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching changeset baseline collection, or <code>null</code> if a matching changeset baseline collection could not be found
	 */
	@Override
	public ChangesetBaselineCollection fetchByName(String name,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { name };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_NAME,
					finderArgs, this);
		}

		if (result instanceof ChangesetBaselineCollection) {
			ChangesetBaselineCollection changesetBaselineCollection = (ChangesetBaselineCollection)result;

			if (!Objects.equals(name, changesetBaselineCollection.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_CHANGESETBASELINECOLLECTION_WHERE);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_NAME_NAME_1);
			}
			else if (name.equals("")) {
				query.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindName) {
					qPos.add(name);
				}

				List<ChangesetBaselineCollection> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_NAME,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"ChangesetBaselineCollectionPersistenceImpl.fetchByName(String, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					ChangesetBaselineCollection changesetBaselineCollection = list.get(0);

					result = changesetBaselineCollection;

					cacheResult(changesetBaselineCollection);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_NAME, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (ChangesetBaselineCollection)result;
		}
	}

	/**
	 * Removes the changeset baseline collection where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the changeset baseline collection that was removed
	 */
	@Override
	public ChangesetBaselineCollection removeByName(String name)
		throws NoSuchBaselineCollectionException {
		ChangesetBaselineCollection changesetBaselineCollection = findByName(name);

		return remove(changesetBaselineCollection);
	}

	/**
	 * Returns the number of changeset baseline collections where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching changeset baseline collections
	 */
	@Override
	public int countByName(String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_NAME;

		Object[] finderArgs = new Object[] { name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CHANGESETBASELINECOLLECTION_WHERE);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_NAME_NAME_1);
			}
			else if (name.equals("")) {
				query.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindName) {
					qPos.add(name);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_NAME_NAME_1 = "changesetBaselineCollection.name IS NULL";
	private static final String _FINDER_COLUMN_NAME_NAME_2 = "changesetBaselineCollection.name = ?";
	private static final String _FINDER_COLUMN_NAME_NAME_3 = "(changesetBaselineCollection.name IS NULL OR changesetBaselineCollection.name = '')";

	public ChangesetBaselineCollectionPersistenceImpl() {
		setModelClass(ChangesetBaselineCollection.class);
	}

	/**
	 * Caches the changeset baseline collection in the entity cache if it is enabled.
	 *
	 * @param changesetBaselineCollection the changeset baseline collection
	 */
	@Override
	public void cacheResult(
		ChangesetBaselineCollection changesetBaselineCollection) {
		entityCache.putResult(ChangesetBaselineCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineCollectionImpl.class,
			changesetBaselineCollection.getPrimaryKey(),
			changesetBaselineCollection);

		finderCache.putResult(FINDER_PATH_FETCH_BY_NAME,
			new Object[] { changesetBaselineCollection.getName() },
			changesetBaselineCollection);

		changesetBaselineCollection.resetOriginalValues();
	}

	/**
	 * Caches the changeset baseline collections in the entity cache if it is enabled.
	 *
	 * @param changesetBaselineCollections the changeset baseline collections
	 */
	@Override
	public void cacheResult(
		List<ChangesetBaselineCollection> changesetBaselineCollections) {
		for (ChangesetBaselineCollection changesetBaselineCollection : changesetBaselineCollections) {
			if (entityCache.getResult(
						ChangesetBaselineCollectionModelImpl.ENTITY_CACHE_ENABLED,
						ChangesetBaselineCollectionImpl.class,
						changesetBaselineCollection.getPrimaryKey()) == null) {
				cacheResult(changesetBaselineCollection);
			}
			else {
				changesetBaselineCollection.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all changeset baseline collections.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ChangesetBaselineCollectionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the changeset baseline collection.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		ChangesetBaselineCollection changesetBaselineCollection) {
		entityCache.removeResult(ChangesetBaselineCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineCollectionImpl.class,
			changesetBaselineCollection.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((ChangesetBaselineCollectionModelImpl)changesetBaselineCollection,
			true);
	}

	@Override
	public void clearCache(
		List<ChangesetBaselineCollection> changesetBaselineCollections) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ChangesetBaselineCollection changesetBaselineCollection : changesetBaselineCollections) {
			entityCache.removeResult(ChangesetBaselineCollectionModelImpl.ENTITY_CACHE_ENABLED,
				ChangesetBaselineCollectionImpl.class,
				changesetBaselineCollection.getPrimaryKey());

			clearUniqueFindersCache((ChangesetBaselineCollectionModelImpl)changesetBaselineCollection,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		ChangesetBaselineCollectionModelImpl changesetBaselineCollectionModelImpl) {
		Object[] args = new Object[] {
				changesetBaselineCollectionModelImpl.getName()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_NAME, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_NAME, args,
			changesetBaselineCollectionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		ChangesetBaselineCollectionModelImpl changesetBaselineCollectionModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					changesetBaselineCollectionModelImpl.getName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_NAME, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_NAME, args);
		}

		if ((changesetBaselineCollectionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_NAME.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					changesetBaselineCollectionModelImpl.getOriginalName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_NAME, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_NAME, args);
		}
	}

	/**
	 * Creates a new changeset baseline collection with the primary key. Does not add the changeset baseline collection to the database.
	 *
	 * @param changesetBaselineCollectionId the primary key for the new changeset baseline collection
	 * @return the new changeset baseline collection
	 */
	@Override
	public ChangesetBaselineCollection create(
		long changesetBaselineCollectionId) {
		ChangesetBaselineCollection changesetBaselineCollection = new ChangesetBaselineCollectionImpl();

		changesetBaselineCollection.setNew(true);
		changesetBaselineCollection.setPrimaryKey(changesetBaselineCollectionId);

		changesetBaselineCollection.setCompanyId(companyProvider.getCompanyId());

		return changesetBaselineCollection;
	}

	/**
	 * Removes the changeset baseline collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param changesetBaselineCollectionId the primary key of the changeset baseline collection
	 * @return the changeset baseline collection that was removed
	 * @throws NoSuchBaselineCollectionException if a changeset baseline collection with the primary key could not be found
	 */
	@Override
	public ChangesetBaselineCollection remove(
		long changesetBaselineCollectionId)
		throws NoSuchBaselineCollectionException {
		return remove((Serializable)changesetBaselineCollectionId);
	}

	/**
	 * Removes the changeset baseline collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the changeset baseline collection
	 * @return the changeset baseline collection that was removed
	 * @throws NoSuchBaselineCollectionException if a changeset baseline collection with the primary key could not be found
	 */
	@Override
	public ChangesetBaselineCollection remove(Serializable primaryKey)
		throws NoSuchBaselineCollectionException {
		Session session = null;

		try {
			session = openSession();

			ChangesetBaselineCollection changesetBaselineCollection = (ChangesetBaselineCollection)session.get(ChangesetBaselineCollectionImpl.class,
					primaryKey);

			if (changesetBaselineCollection == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchBaselineCollectionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(changesetBaselineCollection);
		}
		catch (NoSuchBaselineCollectionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected ChangesetBaselineCollection removeImpl(
		ChangesetBaselineCollection changesetBaselineCollection) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(changesetBaselineCollection)) {
				changesetBaselineCollection = (ChangesetBaselineCollection)session.get(ChangesetBaselineCollectionImpl.class,
						changesetBaselineCollection.getPrimaryKeyObj());
			}

			if (changesetBaselineCollection != null) {
				session.delete(changesetBaselineCollection);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (changesetBaselineCollection != null) {
			clearCache(changesetBaselineCollection);
		}

		return changesetBaselineCollection;
	}

	@Override
	public ChangesetBaselineCollection updateImpl(
		ChangesetBaselineCollection changesetBaselineCollection) {
		boolean isNew = changesetBaselineCollection.isNew();

		if (!(changesetBaselineCollection instanceof ChangesetBaselineCollectionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(changesetBaselineCollection.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(changesetBaselineCollection);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in changesetBaselineCollection proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ChangesetBaselineCollection implementation " +
				changesetBaselineCollection.getClass());
		}

		ChangesetBaselineCollectionModelImpl changesetBaselineCollectionModelImpl =
			(ChangesetBaselineCollectionModelImpl)changesetBaselineCollection;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (changesetBaselineCollection.getCreateDate() == null)) {
			if (serviceContext == null) {
				changesetBaselineCollection.setCreateDate(now);
			}
			else {
				changesetBaselineCollection.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!changesetBaselineCollectionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				changesetBaselineCollection.setModifiedDate(now);
			}
			else {
				changesetBaselineCollection.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (changesetBaselineCollection.isNew()) {
				session.save(changesetBaselineCollection);

				changesetBaselineCollection.setNew(false);
			}
			else {
				changesetBaselineCollection = (ChangesetBaselineCollection)session.merge(changesetBaselineCollection);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!ChangesetBaselineCollectionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(ChangesetBaselineCollectionModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineCollectionImpl.class,
			changesetBaselineCollection.getPrimaryKey(),
			changesetBaselineCollection, false);

		clearUniqueFindersCache(changesetBaselineCollectionModelImpl, false);
		cacheUniqueFindersCache(changesetBaselineCollectionModelImpl);

		changesetBaselineCollection.resetOriginalValues();

		return changesetBaselineCollection;
	}

	/**
	 * Returns the changeset baseline collection with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the changeset baseline collection
	 * @return the changeset baseline collection
	 * @throws NoSuchBaselineCollectionException if a changeset baseline collection with the primary key could not be found
	 */
	@Override
	public ChangesetBaselineCollection findByPrimaryKey(Serializable primaryKey)
		throws NoSuchBaselineCollectionException {
		ChangesetBaselineCollection changesetBaselineCollection = fetchByPrimaryKey(primaryKey);

		if (changesetBaselineCollection == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchBaselineCollectionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return changesetBaselineCollection;
	}

	/**
	 * Returns the changeset baseline collection with the primary key or throws a {@link NoSuchBaselineCollectionException} if it could not be found.
	 *
	 * @param changesetBaselineCollectionId the primary key of the changeset baseline collection
	 * @return the changeset baseline collection
	 * @throws NoSuchBaselineCollectionException if a changeset baseline collection with the primary key could not be found
	 */
	@Override
	public ChangesetBaselineCollection findByPrimaryKey(
		long changesetBaselineCollectionId)
		throws NoSuchBaselineCollectionException {
		return findByPrimaryKey((Serializable)changesetBaselineCollectionId);
	}

	/**
	 * Returns the changeset baseline collection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the changeset baseline collection
	 * @return the changeset baseline collection, or <code>null</code> if a changeset baseline collection with the primary key could not be found
	 */
	@Override
	public ChangesetBaselineCollection fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(ChangesetBaselineCollectionModelImpl.ENTITY_CACHE_ENABLED,
				ChangesetBaselineCollectionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		ChangesetBaselineCollection changesetBaselineCollection = (ChangesetBaselineCollection)serializable;

		if (changesetBaselineCollection == null) {
			Session session = null;

			try {
				session = openSession();

				changesetBaselineCollection = (ChangesetBaselineCollection)session.get(ChangesetBaselineCollectionImpl.class,
						primaryKey);

				if (changesetBaselineCollection != null) {
					cacheResult(changesetBaselineCollection);
				}
				else {
					entityCache.putResult(ChangesetBaselineCollectionModelImpl.ENTITY_CACHE_ENABLED,
						ChangesetBaselineCollectionImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(ChangesetBaselineCollectionModelImpl.ENTITY_CACHE_ENABLED,
					ChangesetBaselineCollectionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return changesetBaselineCollection;
	}

	/**
	 * Returns the changeset baseline collection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param changesetBaselineCollectionId the primary key of the changeset baseline collection
	 * @return the changeset baseline collection, or <code>null</code> if a changeset baseline collection with the primary key could not be found
	 */
	@Override
	public ChangesetBaselineCollection fetchByPrimaryKey(
		long changesetBaselineCollectionId) {
		return fetchByPrimaryKey((Serializable)changesetBaselineCollectionId);
	}

	@Override
	public Map<Serializable, ChangesetBaselineCollection> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ChangesetBaselineCollection> map = new HashMap<Serializable, ChangesetBaselineCollection>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ChangesetBaselineCollection changesetBaselineCollection = fetchByPrimaryKey(primaryKey);

			if (changesetBaselineCollection != null) {
				map.put(primaryKey, changesetBaselineCollection);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(ChangesetBaselineCollectionModelImpl.ENTITY_CACHE_ENABLED,
					ChangesetBaselineCollectionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(ChangesetBaselineCollection)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CHANGESETBASELINECOLLECTION_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (ChangesetBaselineCollection changesetBaselineCollection : (List<ChangesetBaselineCollection>)q.list()) {
				map.put(changesetBaselineCollection.getPrimaryKeyObj(),
					changesetBaselineCollection);

				cacheResult(changesetBaselineCollection);

				uncachedPrimaryKeys.remove(changesetBaselineCollection.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(ChangesetBaselineCollectionModelImpl.ENTITY_CACHE_ENABLED,
					ChangesetBaselineCollectionImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the changeset baseline collections.
	 *
	 * @return the changeset baseline collections
	 */
	@Override
	public List<ChangesetBaselineCollection> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<ChangesetBaselineCollection> findAll(int start, int end) {
		return findAll(start, end, null);
	}

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
	@Override
	public List<ChangesetBaselineCollection> findAll(int start, int end,
		OrderByComparator<ChangesetBaselineCollection> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

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
	@Override
	public List<ChangesetBaselineCollection> findAll(int start, int end,
		OrderByComparator<ChangesetBaselineCollection> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<ChangesetBaselineCollection> list = null;

		if (retrieveFromCache) {
			list = (List<ChangesetBaselineCollection>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CHANGESETBASELINECOLLECTION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CHANGESETBASELINECOLLECTION;

				if (pagination) {
					sql = sql.concat(ChangesetBaselineCollectionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ChangesetBaselineCollection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ChangesetBaselineCollection>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the changeset baseline collections from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ChangesetBaselineCollection changesetBaselineCollection : findAll()) {
			remove(changesetBaselineCollection);
		}
	}

	/**
	 * Returns the number of changeset baseline collections.
	 *
	 * @return the number of changeset baseline collections
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CHANGESETBASELINECOLLECTION);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ChangesetBaselineCollectionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the changeset baseline collection persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(ChangesetBaselineCollectionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_CHANGESETBASELINECOLLECTION = "SELECT changesetBaselineCollection FROM ChangesetBaselineCollection changesetBaselineCollection";
	private static final String _SQL_SELECT_CHANGESETBASELINECOLLECTION_WHERE_PKS_IN =
		"SELECT changesetBaselineCollection FROM ChangesetBaselineCollection changesetBaselineCollection WHERE changesetBaselineCollectionId IN (";
	private static final String _SQL_SELECT_CHANGESETBASELINECOLLECTION_WHERE = "SELECT changesetBaselineCollection FROM ChangesetBaselineCollection changesetBaselineCollection WHERE ";
	private static final String _SQL_COUNT_CHANGESETBASELINECOLLECTION = "SELECT COUNT(changesetBaselineCollection) FROM ChangesetBaselineCollection changesetBaselineCollection";
	private static final String _SQL_COUNT_CHANGESETBASELINECOLLECTION_WHERE = "SELECT COUNT(changesetBaselineCollection) FROM ChangesetBaselineCollection changesetBaselineCollection WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "changesetBaselineCollection.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ChangesetBaselineCollection exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ChangesetBaselineCollection exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(ChangesetBaselineCollectionPersistenceImpl.class);
}
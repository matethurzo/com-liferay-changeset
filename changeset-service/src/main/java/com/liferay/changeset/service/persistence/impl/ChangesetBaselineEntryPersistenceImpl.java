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

import com.liferay.changeset.exception.NoSuchBaselineEntryException;
import com.liferay.changeset.model.ChangesetBaselineEntry;
import com.liferay.changeset.model.impl.ChangesetBaselineEntryImpl;
import com.liferay.changeset.model.impl.ChangesetBaselineEntryModelImpl;
import com.liferay.changeset.service.persistence.ChangesetBaselineEntryPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
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
import java.util.Set;

/**
 * The persistence implementation for the changeset baseline entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetBaselineEntryPersistence
 * @see com.liferay.changeset.service.persistence.ChangesetBaselineEntryUtil
 * @generated
 */
@ProviderType
public class ChangesetBaselineEntryPersistenceImpl extends BasePersistenceImpl<ChangesetBaselineEntry>
	implements ChangesetBaselineEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ChangesetBaselineEntryUtil} to access the changeset baseline entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ChangesetBaselineEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineEntryModelImpl.FINDER_CACHE_ENABLED,
			ChangesetBaselineEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineEntryModelImpl.FINDER_CACHE_ENABLED,
			ChangesetBaselineEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	public ChangesetBaselineEntryPersistenceImpl() {
		setModelClass(ChangesetBaselineEntry.class);
	}

	/**
	 * Caches the changeset baseline entry in the entity cache if it is enabled.
	 *
	 * @param changesetBaselineEntry the changeset baseline entry
	 */
	@Override
	public void cacheResult(ChangesetBaselineEntry changesetBaselineEntry) {
		entityCache.putResult(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineEntryImpl.class,
			changesetBaselineEntry.getPrimaryKey(), changesetBaselineEntry);

		changesetBaselineEntry.resetOriginalValues();
	}

	/**
	 * Caches the changeset baseline entries in the entity cache if it is enabled.
	 *
	 * @param changesetBaselineEntries the changeset baseline entries
	 */
	@Override
	public void cacheResult(
		List<ChangesetBaselineEntry> changesetBaselineEntries) {
		for (ChangesetBaselineEntry changesetBaselineEntry : changesetBaselineEntries) {
			if (entityCache.getResult(
						ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
						ChangesetBaselineEntryImpl.class,
						changesetBaselineEntry.getPrimaryKey()) == null) {
				cacheResult(changesetBaselineEntry);
			}
			else {
				changesetBaselineEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all changeset baseline entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ChangesetBaselineEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the changeset baseline entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ChangesetBaselineEntry changesetBaselineEntry) {
		entityCache.removeResult(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineEntryImpl.class,
			changesetBaselineEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<ChangesetBaselineEntry> changesetBaselineEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ChangesetBaselineEntry changesetBaselineEntry : changesetBaselineEntries) {
			entityCache.removeResult(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
				ChangesetBaselineEntryImpl.class,
				changesetBaselineEntry.getPrimaryKey());
		}
	}

	/**
	 * Creates a new changeset baseline entry with the primary key. Does not add the changeset baseline entry to the database.
	 *
	 * @param changesetBaselineEntryId the primary key for the new changeset baseline entry
	 * @return the new changeset baseline entry
	 */
	@Override
	public ChangesetBaselineEntry create(long changesetBaselineEntryId) {
		ChangesetBaselineEntry changesetBaselineEntry = new ChangesetBaselineEntryImpl();

		changesetBaselineEntry.setNew(true);
		changesetBaselineEntry.setPrimaryKey(changesetBaselineEntryId);

		changesetBaselineEntry.setCompanyId(companyProvider.getCompanyId());

		return changesetBaselineEntry;
	}

	/**
	 * Removes the changeset baseline entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param changesetBaselineEntryId the primary key of the changeset baseline entry
	 * @return the changeset baseline entry that was removed
	 * @throws NoSuchBaselineEntryException if a changeset baseline entry with the primary key could not be found
	 */
	@Override
	public ChangesetBaselineEntry remove(long changesetBaselineEntryId)
		throws NoSuchBaselineEntryException {
		return remove((Serializable)changesetBaselineEntryId);
	}

	/**
	 * Removes the changeset baseline entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the changeset baseline entry
	 * @return the changeset baseline entry that was removed
	 * @throws NoSuchBaselineEntryException if a changeset baseline entry with the primary key could not be found
	 */
	@Override
	public ChangesetBaselineEntry remove(Serializable primaryKey)
		throws NoSuchBaselineEntryException {
		Session session = null;

		try {
			session = openSession();

			ChangesetBaselineEntry changesetBaselineEntry = (ChangesetBaselineEntry)session.get(ChangesetBaselineEntryImpl.class,
					primaryKey);

			if (changesetBaselineEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchBaselineEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(changesetBaselineEntry);
		}
		catch (NoSuchBaselineEntryException nsee) {
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
	protected ChangesetBaselineEntry removeImpl(
		ChangesetBaselineEntry changesetBaselineEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(changesetBaselineEntry)) {
				changesetBaselineEntry = (ChangesetBaselineEntry)session.get(ChangesetBaselineEntryImpl.class,
						changesetBaselineEntry.getPrimaryKeyObj());
			}

			if (changesetBaselineEntry != null) {
				session.delete(changesetBaselineEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (changesetBaselineEntry != null) {
			clearCache(changesetBaselineEntry);
		}

		return changesetBaselineEntry;
	}

	@Override
	public ChangesetBaselineEntry updateImpl(
		ChangesetBaselineEntry changesetBaselineEntry) {
		boolean isNew = changesetBaselineEntry.isNew();

		if (!(changesetBaselineEntry instanceof ChangesetBaselineEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(changesetBaselineEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(changesetBaselineEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in changesetBaselineEntry proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ChangesetBaselineEntry implementation " +
				changesetBaselineEntry.getClass());
		}

		ChangesetBaselineEntryModelImpl changesetBaselineEntryModelImpl = (ChangesetBaselineEntryModelImpl)changesetBaselineEntry;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (changesetBaselineEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				changesetBaselineEntry.setCreateDate(now);
			}
			else {
				changesetBaselineEntry.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!changesetBaselineEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				changesetBaselineEntry.setModifiedDate(now);
			}
			else {
				changesetBaselineEntry.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (changesetBaselineEntry.isNew()) {
				session.save(changesetBaselineEntry);

				changesetBaselineEntry.setNew(false);
			}
			else {
				changesetBaselineEntry = (ChangesetBaselineEntry)session.merge(changesetBaselineEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineEntryImpl.class,
			changesetBaselineEntry.getPrimaryKey(), changesetBaselineEntry,
			false);

		changesetBaselineEntry.resetOriginalValues();

		return changesetBaselineEntry;
	}

	/**
	 * Returns the changeset baseline entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the changeset baseline entry
	 * @return the changeset baseline entry
	 * @throws NoSuchBaselineEntryException if a changeset baseline entry with the primary key could not be found
	 */
	@Override
	public ChangesetBaselineEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchBaselineEntryException {
		ChangesetBaselineEntry changesetBaselineEntry = fetchByPrimaryKey(primaryKey);

		if (changesetBaselineEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchBaselineEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return changesetBaselineEntry;
	}

	/**
	 * Returns the changeset baseline entry with the primary key or throws a {@link NoSuchBaselineEntryException} if it could not be found.
	 *
	 * @param changesetBaselineEntryId the primary key of the changeset baseline entry
	 * @return the changeset baseline entry
	 * @throws NoSuchBaselineEntryException if a changeset baseline entry with the primary key could not be found
	 */
	@Override
	public ChangesetBaselineEntry findByPrimaryKey(
		long changesetBaselineEntryId) throws NoSuchBaselineEntryException {
		return findByPrimaryKey((Serializable)changesetBaselineEntryId);
	}

	/**
	 * Returns the changeset baseline entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the changeset baseline entry
	 * @return the changeset baseline entry, or <code>null</code> if a changeset baseline entry with the primary key could not be found
	 */
	@Override
	public ChangesetBaselineEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
				ChangesetBaselineEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		ChangesetBaselineEntry changesetBaselineEntry = (ChangesetBaselineEntry)serializable;

		if (changesetBaselineEntry == null) {
			Session session = null;

			try {
				session = openSession();

				changesetBaselineEntry = (ChangesetBaselineEntry)session.get(ChangesetBaselineEntryImpl.class,
						primaryKey);

				if (changesetBaselineEntry != null) {
					cacheResult(changesetBaselineEntry);
				}
				else {
					entityCache.putResult(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
						ChangesetBaselineEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
					ChangesetBaselineEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return changesetBaselineEntry;
	}

	/**
	 * Returns the changeset baseline entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param changesetBaselineEntryId the primary key of the changeset baseline entry
	 * @return the changeset baseline entry, or <code>null</code> if a changeset baseline entry with the primary key could not be found
	 */
	@Override
	public ChangesetBaselineEntry fetchByPrimaryKey(
		long changesetBaselineEntryId) {
		return fetchByPrimaryKey((Serializable)changesetBaselineEntryId);
	}

	@Override
	public Map<Serializable, ChangesetBaselineEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ChangesetBaselineEntry> map = new HashMap<Serializable, ChangesetBaselineEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ChangesetBaselineEntry changesetBaselineEntry = fetchByPrimaryKey(primaryKey);

			if (changesetBaselineEntry != null) {
				map.put(primaryKey, changesetBaselineEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
					ChangesetBaselineEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (ChangesetBaselineEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CHANGESETBASELINEENTRY_WHERE_PKS_IN);

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

			for (ChangesetBaselineEntry changesetBaselineEntry : (List<ChangesetBaselineEntry>)q.list()) {
				map.put(changesetBaselineEntry.getPrimaryKeyObj(),
					changesetBaselineEntry);

				cacheResult(changesetBaselineEntry);

				uncachedPrimaryKeys.remove(changesetBaselineEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
					ChangesetBaselineEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the changeset baseline entries.
	 *
	 * @return the changeset baseline entries
	 */
	@Override
	public List<ChangesetBaselineEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<ChangesetBaselineEntry> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<ChangesetBaselineEntry> findAll(int start, int end,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<ChangesetBaselineEntry> findAll(int start, int end,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator,
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

		List<ChangesetBaselineEntry> list = null;

		if (retrieveFromCache) {
			list = (List<ChangesetBaselineEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CHANGESETBASELINEENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CHANGESETBASELINEENTRY;

				if (pagination) {
					sql = sql.concat(ChangesetBaselineEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ChangesetBaselineEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ChangesetBaselineEntry>)QueryUtil.list(q,
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
	 * Removes all the changeset baseline entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ChangesetBaselineEntry changesetBaselineEntry : findAll()) {
			remove(changesetBaselineEntry);
		}
	}

	/**
	 * Returns the number of changeset baseline entries.
	 *
	 * @return the number of changeset baseline entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CHANGESETBASELINEENTRY);

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
		return ChangesetBaselineEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the changeset baseline entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(ChangesetBaselineEntryImpl.class.getName());
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
	private static final String _SQL_SELECT_CHANGESETBASELINEENTRY = "SELECT changesetBaselineEntry FROM ChangesetBaselineEntry changesetBaselineEntry";
	private static final String _SQL_SELECT_CHANGESETBASELINEENTRY_WHERE_PKS_IN = "SELECT changesetBaselineEntry FROM ChangesetBaselineEntry changesetBaselineEntry WHERE changesetBaselineEntryId IN (";
	private static final String _SQL_COUNT_CHANGESETBASELINEENTRY = "SELECT COUNT(changesetBaselineEntry) FROM ChangesetBaselineEntry changesetBaselineEntry";
	private static final String _ORDER_BY_ENTITY_ALIAS = "changesetBaselineEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ChangesetBaselineEntry exists with the primary key ";
	private static final Log _log = LogFactoryUtil.getLog(ChangesetBaselineEntryPersistenceImpl.class);
}
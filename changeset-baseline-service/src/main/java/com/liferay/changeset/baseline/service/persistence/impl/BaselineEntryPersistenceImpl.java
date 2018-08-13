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

package com.liferay.changeset.baseline.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.changeset.baseline.exception.NoSuchBaselineEntryException;
import com.liferay.changeset.baseline.model.BaselineEntry;
import com.liferay.changeset.baseline.model.impl.BaselineEntryImpl;
import com.liferay.changeset.baseline.model.impl.BaselineEntryModelImpl;
import com.liferay.changeset.baseline.service.persistence.BaselineEntryPersistence;

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
 * The persistence implementation for the baseline entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BaselineEntryPersistence
 * @see com.liferay.changeset.baseline.service.persistence.BaselineEntryUtil
 * @generated
 */
@ProviderType
public class BaselineEntryPersistenceImpl extends BasePersistenceImpl<BaselineEntry>
	implements BaselineEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link BaselineEntryUtil} to access the baseline entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = BaselineEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(BaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			BaselineEntryModelImpl.FINDER_CACHE_ENABLED,
			BaselineEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(BaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			BaselineEntryModelImpl.FINDER_CACHE_ENABLED,
			BaselineEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(BaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			BaselineEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	public BaselineEntryPersistenceImpl() {
		setModelClass(BaselineEntry.class);
	}

	/**
	 * Caches the baseline entry in the entity cache if it is enabled.
	 *
	 * @param baselineEntry the baseline entry
	 */
	@Override
	public void cacheResult(BaselineEntry baselineEntry) {
		entityCache.putResult(BaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			BaselineEntryImpl.class, baselineEntry.getPrimaryKey(),
			baselineEntry);

		baselineEntry.resetOriginalValues();
	}

	/**
	 * Caches the baseline entries in the entity cache if it is enabled.
	 *
	 * @param baselineEntries the baseline entries
	 */
	@Override
	public void cacheResult(List<BaselineEntry> baselineEntries) {
		for (BaselineEntry baselineEntry : baselineEntries) {
			if (entityCache.getResult(
						BaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
						BaselineEntryImpl.class, baselineEntry.getPrimaryKey()) == null) {
				cacheResult(baselineEntry);
			}
			else {
				baselineEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all baseline entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(BaselineEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the baseline entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BaselineEntry baselineEntry) {
		entityCache.removeResult(BaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			BaselineEntryImpl.class, baselineEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<BaselineEntry> baselineEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (BaselineEntry baselineEntry : baselineEntries) {
			entityCache.removeResult(BaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
				BaselineEntryImpl.class, baselineEntry.getPrimaryKey());
		}
	}

	/**
	 * Creates a new baseline entry with the primary key. Does not add the baseline entry to the database.
	 *
	 * @param baselineEntryId the primary key for the new baseline entry
	 * @return the new baseline entry
	 */
	@Override
	public BaselineEntry create(long baselineEntryId) {
		BaselineEntry baselineEntry = new BaselineEntryImpl();

		baselineEntry.setNew(true);
		baselineEntry.setPrimaryKey(baselineEntryId);

		baselineEntry.setCompanyId(companyProvider.getCompanyId());

		return baselineEntry;
	}

	/**
	 * Removes the baseline entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param baselineEntryId the primary key of the baseline entry
	 * @return the baseline entry that was removed
	 * @throws NoSuchBaselineEntryException if a baseline entry with the primary key could not be found
	 */
	@Override
	public BaselineEntry remove(long baselineEntryId)
		throws NoSuchBaselineEntryException {
		return remove((Serializable)baselineEntryId);
	}

	/**
	 * Removes the baseline entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the baseline entry
	 * @return the baseline entry that was removed
	 * @throws NoSuchBaselineEntryException if a baseline entry with the primary key could not be found
	 */
	@Override
	public BaselineEntry remove(Serializable primaryKey)
		throws NoSuchBaselineEntryException {
		Session session = null;

		try {
			session = openSession();

			BaselineEntry baselineEntry = (BaselineEntry)session.get(BaselineEntryImpl.class,
					primaryKey);

			if (baselineEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchBaselineEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(baselineEntry);
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
	protected BaselineEntry removeImpl(BaselineEntry baselineEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(baselineEntry)) {
				baselineEntry = (BaselineEntry)session.get(BaselineEntryImpl.class,
						baselineEntry.getPrimaryKeyObj());
			}

			if (baselineEntry != null) {
				session.delete(baselineEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (baselineEntry != null) {
			clearCache(baselineEntry);
		}

		return baselineEntry;
	}

	@Override
	public BaselineEntry updateImpl(BaselineEntry baselineEntry) {
		boolean isNew = baselineEntry.isNew();

		if (!(baselineEntry instanceof BaselineEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(baselineEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(baselineEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in baselineEntry proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom BaselineEntry implementation " +
				baselineEntry.getClass());
		}

		BaselineEntryModelImpl baselineEntryModelImpl = (BaselineEntryModelImpl)baselineEntry;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (baselineEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				baselineEntry.setCreateDate(now);
			}
			else {
				baselineEntry.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!baselineEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				baselineEntry.setModifiedDate(now);
			}
			else {
				baselineEntry.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (baselineEntry.isNew()) {
				session.save(baselineEntry);

				baselineEntry.setNew(false);
			}
			else {
				baselineEntry = (BaselineEntry)session.merge(baselineEntry);
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

		entityCache.putResult(BaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			BaselineEntryImpl.class, baselineEntry.getPrimaryKey(),
			baselineEntry, false);

		baselineEntry.resetOriginalValues();

		return baselineEntry;
	}

	/**
	 * Returns the baseline entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the baseline entry
	 * @return the baseline entry
	 * @throws NoSuchBaselineEntryException if a baseline entry with the primary key could not be found
	 */
	@Override
	public BaselineEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchBaselineEntryException {
		BaselineEntry baselineEntry = fetchByPrimaryKey(primaryKey);

		if (baselineEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchBaselineEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return baselineEntry;
	}

	/**
	 * Returns the baseline entry with the primary key or throws a {@link NoSuchBaselineEntryException} if it could not be found.
	 *
	 * @param baselineEntryId the primary key of the baseline entry
	 * @return the baseline entry
	 * @throws NoSuchBaselineEntryException if a baseline entry with the primary key could not be found
	 */
	@Override
	public BaselineEntry findByPrimaryKey(long baselineEntryId)
		throws NoSuchBaselineEntryException {
		return findByPrimaryKey((Serializable)baselineEntryId);
	}

	/**
	 * Returns the baseline entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the baseline entry
	 * @return the baseline entry, or <code>null</code> if a baseline entry with the primary key could not be found
	 */
	@Override
	public BaselineEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(BaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
				BaselineEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		BaselineEntry baselineEntry = (BaselineEntry)serializable;

		if (baselineEntry == null) {
			Session session = null;

			try {
				session = openSession();

				baselineEntry = (BaselineEntry)session.get(BaselineEntryImpl.class,
						primaryKey);

				if (baselineEntry != null) {
					cacheResult(baselineEntry);
				}
				else {
					entityCache.putResult(BaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
						BaselineEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(BaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
					BaselineEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return baselineEntry;
	}

	/**
	 * Returns the baseline entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param baselineEntryId the primary key of the baseline entry
	 * @return the baseline entry, or <code>null</code> if a baseline entry with the primary key could not be found
	 */
	@Override
	public BaselineEntry fetchByPrimaryKey(long baselineEntryId) {
		return fetchByPrimaryKey((Serializable)baselineEntryId);
	}

	@Override
	public Map<Serializable, BaselineEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, BaselineEntry> map = new HashMap<Serializable, BaselineEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			BaselineEntry baselineEntry = fetchByPrimaryKey(primaryKey);

			if (baselineEntry != null) {
				map.put(primaryKey, baselineEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(BaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
					BaselineEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (BaselineEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_BASELINEENTRY_WHERE_PKS_IN);

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

			for (BaselineEntry baselineEntry : (List<BaselineEntry>)q.list()) {
				map.put(baselineEntry.getPrimaryKeyObj(), baselineEntry);

				cacheResult(baselineEntry);

				uncachedPrimaryKeys.remove(baselineEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(BaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
					BaselineEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the baseline entries.
	 *
	 * @return the baseline entries
	 */
	@Override
	public List<BaselineEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<BaselineEntry> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<BaselineEntry> findAll(int start, int end,
		OrderByComparator<BaselineEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<BaselineEntry> findAll(int start, int end,
		OrderByComparator<BaselineEntry> orderByComparator,
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

		List<BaselineEntry> list = null;

		if (retrieveFromCache) {
			list = (List<BaselineEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_BASELINEENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_BASELINEENTRY;

				if (pagination) {
					sql = sql.concat(BaselineEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<BaselineEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BaselineEntry>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the baseline entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BaselineEntry baselineEntry : findAll()) {
			remove(baselineEntry);
		}
	}

	/**
	 * Returns the number of baseline entries.
	 *
	 * @return the number of baseline entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_BASELINEENTRY);

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
		return BaselineEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the baseline entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(BaselineEntryImpl.class.getName());
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
	private static final String _SQL_SELECT_BASELINEENTRY = "SELECT baselineEntry FROM BaselineEntry baselineEntry";
	private static final String _SQL_SELECT_BASELINEENTRY_WHERE_PKS_IN = "SELECT baselineEntry FROM BaselineEntry baselineEntry WHERE baselineEntryId IN (";
	private static final String _SQL_COUNT_BASELINEENTRY = "SELECT COUNT(baselineEntry) FROM BaselineEntry baselineEntry";
	private static final String _ORDER_BY_ENTITY_ALIAS = "baselineEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No BaselineEntry exists with the primary key ";
	private static final Log _log = LogFactoryUtil.getLog(BaselineEntryPersistenceImpl.class);
}
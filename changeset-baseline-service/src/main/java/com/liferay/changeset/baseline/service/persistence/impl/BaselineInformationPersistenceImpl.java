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

import com.liferay.changeset.baseline.exception.NoSuchBaselineInformationException;
import com.liferay.changeset.baseline.model.BaselineInformation;
import com.liferay.changeset.baseline.model.impl.BaselineInformationImpl;
import com.liferay.changeset.baseline.model.impl.BaselineInformationModelImpl;
import com.liferay.changeset.baseline.service.persistence.BaselineInformationPersistence;

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
 * The persistence implementation for the baseline information service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BaselineInformationPersistence
 * @see com.liferay.changeset.baseline.service.persistence.BaselineInformationUtil
 * @generated
 */
@ProviderType
public class BaselineInformationPersistenceImpl extends BasePersistenceImpl<BaselineInformation>
	implements BaselineInformationPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link BaselineInformationUtil} to access the baseline information persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = BaselineInformationImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(BaselineInformationModelImpl.ENTITY_CACHE_ENABLED,
			BaselineInformationModelImpl.FINDER_CACHE_ENABLED,
			BaselineInformationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(BaselineInformationModelImpl.ENTITY_CACHE_ENABLED,
			BaselineInformationModelImpl.FINDER_CACHE_ENABLED,
			BaselineInformationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(BaselineInformationModelImpl.ENTITY_CACHE_ENABLED,
			BaselineInformationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_NAME = new FinderPath(BaselineInformationModelImpl.ENTITY_CACHE_ENABLED,
			BaselineInformationModelImpl.FINDER_CACHE_ENABLED,
			BaselineInformationImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByName", new String[] { String.class.getName() },
			BaselineInformationModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_NAME = new FinderPath(BaselineInformationModelImpl.ENTITY_CACHE_ENABLED,
			BaselineInformationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByName",
			new String[] { String.class.getName() });

	/**
	 * Returns the baseline information where name = &#63; or throws a {@link NoSuchBaselineInformationException} if it could not be found.
	 *
	 * @param name the name
	 * @return the matching baseline information
	 * @throws NoSuchBaselineInformationException if a matching baseline information could not be found
	 */
	@Override
	public BaselineInformation findByName(String name)
		throws NoSuchBaselineInformationException {
		BaselineInformation baselineInformation = fetchByName(name);

		if (baselineInformation == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("name=");
			msg.append(name);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchBaselineInformationException(msg.toString());
		}

		return baselineInformation;
	}

	/**
	 * Returns the baseline information where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching baseline information, or <code>null</code> if a matching baseline information could not be found
	 */
	@Override
	public BaselineInformation fetchByName(String name) {
		return fetchByName(name, true);
	}

	/**
	 * Returns the baseline information where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching baseline information, or <code>null</code> if a matching baseline information could not be found
	 */
	@Override
	public BaselineInformation fetchByName(String name,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { name };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_NAME,
					finderArgs, this);
		}

		if (result instanceof BaselineInformation) {
			BaselineInformation baselineInformation = (BaselineInformation)result;

			if (!Objects.equals(name, baselineInformation.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_BASELINEINFORMATION_WHERE);

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

				List<BaselineInformation> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_NAME,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"BaselineInformationPersistenceImpl.fetchByName(String, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					BaselineInformation baselineInformation = list.get(0);

					result = baselineInformation;

					cacheResult(baselineInformation);
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
			return (BaselineInformation)result;
		}
	}

	/**
	 * Removes the baseline information where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the baseline information that was removed
	 */
	@Override
	public BaselineInformation removeByName(String name)
		throws NoSuchBaselineInformationException {
		BaselineInformation baselineInformation = findByName(name);

		return remove(baselineInformation);
	}

	/**
	 * Returns the number of baseline informations where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching baseline informations
	 */
	@Override
	public int countByName(String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_NAME;

		Object[] finderArgs = new Object[] { name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BASELINEINFORMATION_WHERE);

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

	private static final String _FINDER_COLUMN_NAME_NAME_1 = "baselineInformation.name IS NULL";
	private static final String _FINDER_COLUMN_NAME_NAME_2 = "baselineInformation.name = ?";
	private static final String _FINDER_COLUMN_NAME_NAME_3 = "(baselineInformation.name IS NULL OR baselineInformation.name = '')";

	public BaselineInformationPersistenceImpl() {
		setModelClass(BaselineInformation.class);
	}

	/**
	 * Caches the baseline information in the entity cache if it is enabled.
	 *
	 * @param baselineInformation the baseline information
	 */
	@Override
	public void cacheResult(BaselineInformation baselineInformation) {
		entityCache.putResult(BaselineInformationModelImpl.ENTITY_CACHE_ENABLED,
			BaselineInformationImpl.class, baselineInformation.getPrimaryKey(),
			baselineInformation);

		finderCache.putResult(FINDER_PATH_FETCH_BY_NAME,
			new Object[] { baselineInformation.getName() }, baselineInformation);

		baselineInformation.resetOriginalValues();
	}

	/**
	 * Caches the baseline informations in the entity cache if it is enabled.
	 *
	 * @param baselineInformations the baseline informations
	 */
	@Override
	public void cacheResult(List<BaselineInformation> baselineInformations) {
		for (BaselineInformation baselineInformation : baselineInformations) {
			if (entityCache.getResult(
						BaselineInformationModelImpl.ENTITY_CACHE_ENABLED,
						BaselineInformationImpl.class,
						baselineInformation.getPrimaryKey()) == null) {
				cacheResult(baselineInformation);
			}
			else {
				baselineInformation.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all baseline informations.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(BaselineInformationImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the baseline information.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BaselineInformation baselineInformation) {
		entityCache.removeResult(BaselineInformationModelImpl.ENTITY_CACHE_ENABLED,
			BaselineInformationImpl.class, baselineInformation.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((BaselineInformationModelImpl)baselineInformation,
			true);
	}

	@Override
	public void clearCache(List<BaselineInformation> baselineInformations) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (BaselineInformation baselineInformation : baselineInformations) {
			entityCache.removeResult(BaselineInformationModelImpl.ENTITY_CACHE_ENABLED,
				BaselineInformationImpl.class,
				baselineInformation.getPrimaryKey());

			clearUniqueFindersCache((BaselineInformationModelImpl)baselineInformation,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		BaselineInformationModelImpl baselineInformationModelImpl) {
		Object[] args = new Object[] { baselineInformationModelImpl.getName() };

		finderCache.putResult(FINDER_PATH_COUNT_BY_NAME, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_NAME, args,
			baselineInformationModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		BaselineInformationModelImpl baselineInformationModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] { baselineInformationModelImpl.getName() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_NAME, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_NAME, args);
		}

		if ((baselineInformationModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_NAME.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					baselineInformationModelImpl.getOriginalName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_NAME, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_NAME, args);
		}
	}

	/**
	 * Creates a new baseline information with the primary key. Does not add the baseline information to the database.
	 *
	 * @param baselineInformationId the primary key for the new baseline information
	 * @return the new baseline information
	 */
	@Override
	public BaselineInformation create(long baselineInformationId) {
		BaselineInformation baselineInformation = new BaselineInformationImpl();

		baselineInformation.setNew(true);
		baselineInformation.setPrimaryKey(baselineInformationId);

		baselineInformation.setCompanyId(companyProvider.getCompanyId());

		return baselineInformation;
	}

	/**
	 * Removes the baseline information with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param baselineInformationId the primary key of the baseline information
	 * @return the baseline information that was removed
	 * @throws NoSuchBaselineInformationException if a baseline information with the primary key could not be found
	 */
	@Override
	public BaselineInformation remove(long baselineInformationId)
		throws NoSuchBaselineInformationException {
		return remove((Serializable)baselineInformationId);
	}

	/**
	 * Removes the baseline information with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the baseline information
	 * @return the baseline information that was removed
	 * @throws NoSuchBaselineInformationException if a baseline information with the primary key could not be found
	 */
	@Override
	public BaselineInformation remove(Serializable primaryKey)
		throws NoSuchBaselineInformationException {
		Session session = null;

		try {
			session = openSession();

			BaselineInformation baselineInformation = (BaselineInformation)session.get(BaselineInformationImpl.class,
					primaryKey);

			if (baselineInformation == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchBaselineInformationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(baselineInformation);
		}
		catch (NoSuchBaselineInformationException nsee) {
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
	protected BaselineInformation removeImpl(
		BaselineInformation baselineInformation) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(baselineInformation)) {
				baselineInformation = (BaselineInformation)session.get(BaselineInformationImpl.class,
						baselineInformation.getPrimaryKeyObj());
			}

			if (baselineInformation != null) {
				session.delete(baselineInformation);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (baselineInformation != null) {
			clearCache(baselineInformation);
		}

		return baselineInformation;
	}

	@Override
	public BaselineInformation updateImpl(
		BaselineInformation baselineInformation) {
		boolean isNew = baselineInformation.isNew();

		if (!(baselineInformation instanceof BaselineInformationModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(baselineInformation.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(baselineInformation);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in baselineInformation proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom BaselineInformation implementation " +
				baselineInformation.getClass());
		}

		BaselineInformationModelImpl baselineInformationModelImpl = (BaselineInformationModelImpl)baselineInformation;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (baselineInformation.getCreateDate() == null)) {
			if (serviceContext == null) {
				baselineInformation.setCreateDate(now);
			}
			else {
				baselineInformation.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!baselineInformationModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				baselineInformation.setModifiedDate(now);
			}
			else {
				baselineInformation.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (baselineInformation.isNew()) {
				session.save(baselineInformation);

				baselineInformation.setNew(false);
			}
			else {
				baselineInformation = (BaselineInformation)session.merge(baselineInformation);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!BaselineInformationModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(BaselineInformationModelImpl.ENTITY_CACHE_ENABLED,
			BaselineInformationImpl.class, baselineInformation.getPrimaryKey(),
			baselineInformation, false);

		clearUniqueFindersCache(baselineInformationModelImpl, false);
		cacheUniqueFindersCache(baselineInformationModelImpl);

		baselineInformation.resetOriginalValues();

		return baselineInformation;
	}

	/**
	 * Returns the baseline information with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the baseline information
	 * @return the baseline information
	 * @throws NoSuchBaselineInformationException if a baseline information with the primary key could not be found
	 */
	@Override
	public BaselineInformation findByPrimaryKey(Serializable primaryKey)
		throws NoSuchBaselineInformationException {
		BaselineInformation baselineInformation = fetchByPrimaryKey(primaryKey);

		if (baselineInformation == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchBaselineInformationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return baselineInformation;
	}

	/**
	 * Returns the baseline information with the primary key or throws a {@link NoSuchBaselineInformationException} if it could not be found.
	 *
	 * @param baselineInformationId the primary key of the baseline information
	 * @return the baseline information
	 * @throws NoSuchBaselineInformationException if a baseline information with the primary key could not be found
	 */
	@Override
	public BaselineInformation findByPrimaryKey(long baselineInformationId)
		throws NoSuchBaselineInformationException {
		return findByPrimaryKey((Serializable)baselineInformationId);
	}

	/**
	 * Returns the baseline information with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the baseline information
	 * @return the baseline information, or <code>null</code> if a baseline information with the primary key could not be found
	 */
	@Override
	public BaselineInformation fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(BaselineInformationModelImpl.ENTITY_CACHE_ENABLED,
				BaselineInformationImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		BaselineInformation baselineInformation = (BaselineInformation)serializable;

		if (baselineInformation == null) {
			Session session = null;

			try {
				session = openSession();

				baselineInformation = (BaselineInformation)session.get(BaselineInformationImpl.class,
						primaryKey);

				if (baselineInformation != null) {
					cacheResult(baselineInformation);
				}
				else {
					entityCache.putResult(BaselineInformationModelImpl.ENTITY_CACHE_ENABLED,
						BaselineInformationImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(BaselineInformationModelImpl.ENTITY_CACHE_ENABLED,
					BaselineInformationImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return baselineInformation;
	}

	/**
	 * Returns the baseline information with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param baselineInformationId the primary key of the baseline information
	 * @return the baseline information, or <code>null</code> if a baseline information with the primary key could not be found
	 */
	@Override
	public BaselineInformation fetchByPrimaryKey(long baselineInformationId) {
		return fetchByPrimaryKey((Serializable)baselineInformationId);
	}

	@Override
	public Map<Serializable, BaselineInformation> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, BaselineInformation> map = new HashMap<Serializable, BaselineInformation>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			BaselineInformation baselineInformation = fetchByPrimaryKey(primaryKey);

			if (baselineInformation != null) {
				map.put(primaryKey, baselineInformation);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(BaselineInformationModelImpl.ENTITY_CACHE_ENABLED,
					BaselineInformationImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (BaselineInformation)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_BASELINEINFORMATION_WHERE_PKS_IN);

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

			for (BaselineInformation baselineInformation : (List<BaselineInformation>)q.list()) {
				map.put(baselineInformation.getPrimaryKeyObj(),
					baselineInformation);

				cacheResult(baselineInformation);

				uncachedPrimaryKeys.remove(baselineInformation.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(BaselineInformationModelImpl.ENTITY_CACHE_ENABLED,
					BaselineInformationImpl.class, primaryKey, nullModel);
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
	 * Returns all the baseline informations.
	 *
	 * @return the baseline informations
	 */
	@Override
	public List<BaselineInformation> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<BaselineInformation> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<BaselineInformation> findAll(int start, int end,
		OrderByComparator<BaselineInformation> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<BaselineInformation> findAll(int start, int end,
		OrderByComparator<BaselineInformation> orderByComparator,
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

		List<BaselineInformation> list = null;

		if (retrieveFromCache) {
			list = (List<BaselineInformation>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_BASELINEINFORMATION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_BASELINEINFORMATION;

				if (pagination) {
					sql = sql.concat(BaselineInformationModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<BaselineInformation>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BaselineInformation>)QueryUtil.list(q,
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
	 * Removes all the baseline informations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BaselineInformation baselineInformation : findAll()) {
			remove(baselineInformation);
		}
	}

	/**
	 * Returns the number of baseline informations.
	 *
	 * @return the number of baseline informations
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_BASELINEINFORMATION);

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
		return BaselineInformationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the baseline information persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(BaselineInformationImpl.class.getName());
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
	private static final String _SQL_SELECT_BASELINEINFORMATION = "SELECT baselineInformation FROM BaselineInformation baselineInformation";
	private static final String _SQL_SELECT_BASELINEINFORMATION_WHERE_PKS_IN = "SELECT baselineInformation FROM BaselineInformation baselineInformation WHERE baselineInformationId IN (";
	private static final String _SQL_SELECT_BASELINEINFORMATION_WHERE = "SELECT baselineInformation FROM BaselineInformation baselineInformation WHERE ";
	private static final String _SQL_COUNT_BASELINEINFORMATION = "SELECT COUNT(baselineInformation) FROM BaselineInformation baselineInformation";
	private static final String _SQL_COUNT_BASELINEINFORMATION_WHERE = "SELECT COUNT(baselineInformation) FROM BaselineInformation baselineInformation WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "baselineInformation.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No BaselineInformation exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No BaselineInformation exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(BaselineInformationPersistenceImpl.class);
}
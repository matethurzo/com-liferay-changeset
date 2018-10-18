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
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_RESOURCEPRIMKEY =
		new FinderPath(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineEntryModelImpl.FINDER_CACHE_ENABLED,
			ChangesetBaselineEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByResourcePrimKey",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY =
		new FinderPath(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineEntryModelImpl.FINDER_CACHE_ENABLED,
			ChangesetBaselineEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByResourcePrimKey",
			new String[] { Long.class.getName() },
			ChangesetBaselineEntryModelImpl.RESOURCEPRIMKEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY = new FinderPath(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByResourcePrimKey", new String[] { Long.class.getName() });

	/**
	 * Returns all the changeset baseline entries where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @return the matching changeset baseline entries
	 */
	@Override
	public List<ChangesetBaselineEntry> findByResourcePrimKey(
		long resourcePrimKey) {
		return findByResourcePrimKey(resourcePrimKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the changeset baseline entries where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetBaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of changeset baseline entries
	 * @param end the upper bound of the range of changeset baseline entries (not inclusive)
	 * @return the range of matching changeset baseline entries
	 */
	@Override
	public List<ChangesetBaselineEntry> findByResourcePrimKey(
		long resourcePrimKey, int start, int end) {
		return findByResourcePrimKey(resourcePrimKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the changeset baseline entries where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetBaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of changeset baseline entries
	 * @param end the upper bound of the range of changeset baseline entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset baseline entries
	 */
	@Override
	public List<ChangesetBaselineEntry> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator) {
		return findByResourcePrimKey(resourcePrimKey, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the changeset baseline entries where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetBaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of changeset baseline entries
	 * @param end the upper bound of the range of changeset baseline entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching changeset baseline entries
	 */
	@Override
	public List<ChangesetBaselineEntry> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY;
			finderArgs = new Object[] { resourcePrimKey };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_RESOURCEPRIMKEY;
			finderArgs = new Object[] {
					resourcePrimKey,
					
					start, end, orderByComparator
				};
		}

		List<ChangesetBaselineEntry> list = null;

		if (retrieveFromCache) {
			list = (List<ChangesetBaselineEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ChangesetBaselineEntry changesetBaselineEntry : list) {
					if ((resourcePrimKey != changesetBaselineEntry.getResourcePrimKey())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_CHANGESETBASELINEENTRY_WHERE);

			query.append(_FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ChangesetBaselineEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

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
	 * Returns the first changeset baseline entry in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset baseline entry
	 * @throws NoSuchBaselineEntryException if a matching changeset baseline entry could not be found
	 */
	@Override
	public ChangesetBaselineEntry findByResourcePrimKey_First(
		long resourcePrimKey,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator)
		throws NoSuchBaselineEntryException {
		ChangesetBaselineEntry changesetBaselineEntry = fetchByResourcePrimKey_First(resourcePrimKey,
				orderByComparator);

		if (changesetBaselineEntry != null) {
			return changesetBaselineEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append("}");

		throw new NoSuchBaselineEntryException(msg.toString());
	}

	/**
	 * Returns the first changeset baseline entry in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset baseline entry, or <code>null</code> if a matching changeset baseline entry could not be found
	 */
	@Override
	public ChangesetBaselineEntry fetchByResourcePrimKey_First(
		long resourcePrimKey,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator) {
		List<ChangesetBaselineEntry> list = findByResourcePrimKey(resourcePrimKey,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last changeset baseline entry in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset baseline entry
	 * @throws NoSuchBaselineEntryException if a matching changeset baseline entry could not be found
	 */
	@Override
	public ChangesetBaselineEntry findByResourcePrimKey_Last(
		long resourcePrimKey,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator)
		throws NoSuchBaselineEntryException {
		ChangesetBaselineEntry changesetBaselineEntry = fetchByResourcePrimKey_Last(resourcePrimKey,
				orderByComparator);

		if (changesetBaselineEntry != null) {
			return changesetBaselineEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append("}");

		throw new NoSuchBaselineEntryException(msg.toString());
	}

	/**
	 * Returns the last changeset baseline entry in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset baseline entry, or <code>null</code> if a matching changeset baseline entry could not be found
	 */
	@Override
	public ChangesetBaselineEntry fetchByResourcePrimKey_Last(
		long resourcePrimKey,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator) {
		int count = countByResourcePrimKey(resourcePrimKey);

		if (count == 0) {
			return null;
		}

		List<ChangesetBaselineEntry> list = findByResourcePrimKey(resourcePrimKey,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the changeset baseline entries before and after the current changeset baseline entry in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param changesetBaselineEntryId the primary key of the current changeset baseline entry
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next changeset baseline entry
	 * @throws NoSuchBaselineEntryException if a changeset baseline entry with the primary key could not be found
	 */
	@Override
	public ChangesetBaselineEntry[] findByResourcePrimKey_PrevAndNext(
		long changesetBaselineEntryId, long resourcePrimKey,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator)
		throws NoSuchBaselineEntryException {
		ChangesetBaselineEntry changesetBaselineEntry = findByPrimaryKey(changesetBaselineEntryId);

		Session session = null;

		try {
			session = openSession();

			ChangesetBaselineEntry[] array = new ChangesetBaselineEntryImpl[3];

			array[0] = getByResourcePrimKey_PrevAndNext(session,
					changesetBaselineEntry, resourcePrimKey, orderByComparator,
					true);

			array[1] = changesetBaselineEntry;

			array[2] = getByResourcePrimKey_PrevAndNext(session,
					changesetBaselineEntry, resourcePrimKey, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ChangesetBaselineEntry getByResourcePrimKey_PrevAndNext(
		Session session, ChangesetBaselineEntry changesetBaselineEntry,
		long resourcePrimKey,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CHANGESETBASELINEENTRY_WHERE);

		query.append(_FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ChangesetBaselineEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(resourcePrimKey);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(changesetBaselineEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ChangesetBaselineEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the changeset baseline entries where resourcePrimKey = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 */
	@Override
	public void removeByResourcePrimKey(long resourcePrimKey) {
		for (ChangesetBaselineEntry changesetBaselineEntry : findByResourcePrimKey(
				resourcePrimKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(changesetBaselineEntry);
		}
	}

	/**
	 * Returns the number of changeset baseline entries where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @return the number of matching changeset baseline entries
	 */
	@Override
	public int countByResourcePrimKey(long resourcePrimKey) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY;

		Object[] finderArgs = new Object[] { resourcePrimKey };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CHANGESETBASELINEENTRY_WHERE);

			query.append(_FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

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

	private static final String _FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2 =
		"changesetBaselineEntry.resourcePrimKey = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CHANGESETBASELINECOLLECTIONID =
		new FinderPath(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineEntryModelImpl.FINDER_CACHE_ENABLED,
			ChangesetBaselineEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByChangesetBaselineCollectionId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CHANGESETBASELINECOLLECTIONID =
		new FinderPath(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineEntryModelImpl.FINDER_CACHE_ENABLED,
			ChangesetBaselineEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByChangesetBaselineCollectionId",
			new String[] { Long.class.getName() },
			ChangesetBaselineEntryModelImpl.CHANGESETBASELINECOLLECTIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CHANGESETBASELINECOLLECTIONID =
		new FinderPath(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByChangesetBaselineCollectionId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the changeset baseline entries where changesetBaselineCollectionId = &#63;.
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @return the matching changeset baseline entries
	 */
	@Override
	public List<ChangesetBaselineEntry> findByChangesetBaselineCollectionId(
		long changesetBaselineCollectionId) {
		return findByChangesetBaselineCollectionId(changesetBaselineCollectionId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the changeset baseline entries where changesetBaselineCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetBaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param start the lower bound of the range of changeset baseline entries
	 * @param end the upper bound of the range of changeset baseline entries (not inclusive)
	 * @return the range of matching changeset baseline entries
	 */
	@Override
	public List<ChangesetBaselineEntry> findByChangesetBaselineCollectionId(
		long changesetBaselineCollectionId, int start, int end) {
		return findByChangesetBaselineCollectionId(changesetBaselineCollectionId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the changeset baseline entries where changesetBaselineCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetBaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param start the lower bound of the range of changeset baseline entries
	 * @param end the upper bound of the range of changeset baseline entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset baseline entries
	 */
	@Override
	public List<ChangesetBaselineEntry> findByChangesetBaselineCollectionId(
		long changesetBaselineCollectionId, int start, int end,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator) {
		return findByChangesetBaselineCollectionId(changesetBaselineCollectionId,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the changeset baseline entries where changesetBaselineCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetBaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param start the lower bound of the range of changeset baseline entries
	 * @param end the upper bound of the range of changeset baseline entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching changeset baseline entries
	 */
	@Override
	public List<ChangesetBaselineEntry> findByChangesetBaselineCollectionId(
		long changesetBaselineCollectionId, int start, int end,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CHANGESETBASELINECOLLECTIONID;
			finderArgs = new Object[] { changesetBaselineCollectionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CHANGESETBASELINECOLLECTIONID;
			finderArgs = new Object[] {
					changesetBaselineCollectionId,
					
					start, end, orderByComparator
				};
		}

		List<ChangesetBaselineEntry> list = null;

		if (retrieveFromCache) {
			list = (List<ChangesetBaselineEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ChangesetBaselineEntry changesetBaselineEntry : list) {
					if ((changesetBaselineCollectionId != changesetBaselineEntry.getChangesetBaselineCollectionId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_CHANGESETBASELINEENTRY_WHERE);

			query.append(_FINDER_COLUMN_CHANGESETBASELINECOLLECTIONID_CHANGESETBASELINECOLLECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ChangesetBaselineEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(changesetBaselineCollectionId);

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
	 * Returns the first changeset baseline entry in the ordered set where changesetBaselineCollectionId = &#63;.
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset baseline entry
	 * @throws NoSuchBaselineEntryException if a matching changeset baseline entry could not be found
	 */
	@Override
	public ChangesetBaselineEntry findByChangesetBaselineCollectionId_First(
		long changesetBaselineCollectionId,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator)
		throws NoSuchBaselineEntryException {
		ChangesetBaselineEntry changesetBaselineEntry = fetchByChangesetBaselineCollectionId_First(changesetBaselineCollectionId,
				orderByComparator);

		if (changesetBaselineEntry != null) {
			return changesetBaselineEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("changesetBaselineCollectionId=");
		msg.append(changesetBaselineCollectionId);

		msg.append("}");

		throw new NoSuchBaselineEntryException(msg.toString());
	}

	/**
	 * Returns the first changeset baseline entry in the ordered set where changesetBaselineCollectionId = &#63;.
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset baseline entry, or <code>null</code> if a matching changeset baseline entry could not be found
	 */
	@Override
	public ChangesetBaselineEntry fetchByChangesetBaselineCollectionId_First(
		long changesetBaselineCollectionId,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator) {
		List<ChangesetBaselineEntry> list = findByChangesetBaselineCollectionId(changesetBaselineCollectionId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last changeset baseline entry in the ordered set where changesetBaselineCollectionId = &#63;.
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset baseline entry
	 * @throws NoSuchBaselineEntryException if a matching changeset baseline entry could not be found
	 */
	@Override
	public ChangesetBaselineEntry findByChangesetBaselineCollectionId_Last(
		long changesetBaselineCollectionId,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator)
		throws NoSuchBaselineEntryException {
		ChangesetBaselineEntry changesetBaselineEntry = fetchByChangesetBaselineCollectionId_Last(changesetBaselineCollectionId,
				orderByComparator);

		if (changesetBaselineEntry != null) {
			return changesetBaselineEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("changesetBaselineCollectionId=");
		msg.append(changesetBaselineCollectionId);

		msg.append("}");

		throw new NoSuchBaselineEntryException(msg.toString());
	}

	/**
	 * Returns the last changeset baseline entry in the ordered set where changesetBaselineCollectionId = &#63;.
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset baseline entry, or <code>null</code> if a matching changeset baseline entry could not be found
	 */
	@Override
	public ChangesetBaselineEntry fetchByChangesetBaselineCollectionId_Last(
		long changesetBaselineCollectionId,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator) {
		int count = countByChangesetBaselineCollectionId(changesetBaselineCollectionId);

		if (count == 0) {
			return null;
		}

		List<ChangesetBaselineEntry> list = findByChangesetBaselineCollectionId(changesetBaselineCollectionId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the changeset baseline entries before and after the current changeset baseline entry in the ordered set where changesetBaselineCollectionId = &#63;.
	 *
	 * @param changesetBaselineEntryId the primary key of the current changeset baseline entry
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next changeset baseline entry
	 * @throws NoSuchBaselineEntryException if a changeset baseline entry with the primary key could not be found
	 */
	@Override
	public ChangesetBaselineEntry[] findByChangesetBaselineCollectionId_PrevAndNext(
		long changesetBaselineEntryId, long changesetBaselineCollectionId,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator)
		throws NoSuchBaselineEntryException {
		ChangesetBaselineEntry changesetBaselineEntry = findByPrimaryKey(changesetBaselineEntryId);

		Session session = null;

		try {
			session = openSession();

			ChangesetBaselineEntry[] array = new ChangesetBaselineEntryImpl[3];

			array[0] = getByChangesetBaselineCollectionId_PrevAndNext(session,
					changesetBaselineEntry, changesetBaselineCollectionId,
					orderByComparator, true);

			array[1] = changesetBaselineEntry;

			array[2] = getByChangesetBaselineCollectionId_PrevAndNext(session,
					changesetBaselineEntry, changesetBaselineCollectionId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ChangesetBaselineEntry getByChangesetBaselineCollectionId_PrevAndNext(
		Session session, ChangesetBaselineEntry changesetBaselineEntry,
		long changesetBaselineCollectionId,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CHANGESETBASELINEENTRY_WHERE);

		query.append(_FINDER_COLUMN_CHANGESETBASELINECOLLECTIONID_CHANGESETBASELINECOLLECTIONID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ChangesetBaselineEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(changesetBaselineCollectionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(changesetBaselineEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ChangesetBaselineEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the changeset baseline entries where changesetBaselineCollectionId = &#63; from the database.
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 */
	@Override
	public void removeByChangesetBaselineCollectionId(
		long changesetBaselineCollectionId) {
		for (ChangesetBaselineEntry changesetBaselineEntry : findByChangesetBaselineCollectionId(
				changesetBaselineCollectionId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(changesetBaselineEntry);
		}
	}

	/**
	 * Returns the number of changeset baseline entries where changesetBaselineCollectionId = &#63;.
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @return the number of matching changeset baseline entries
	 */
	@Override
	public int countByChangesetBaselineCollectionId(
		long changesetBaselineCollectionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CHANGESETBASELINECOLLECTIONID;

		Object[] finderArgs = new Object[] { changesetBaselineCollectionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CHANGESETBASELINEENTRY_WHERE);

			query.append(_FINDER_COLUMN_CHANGESETBASELINECOLLECTIONID_CHANGESETBASELINECOLLECTIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(changesetBaselineCollectionId);

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

	private static final String _FINDER_COLUMN_CHANGESETBASELINECOLLECTIONID_CHANGESETBASELINECOLLECTIONID_2 =
		"changesetBaselineEntry.changesetBaselineCollectionId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_R = new FinderPath(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineEntryModelImpl.FINDER_CACHE_ENABLED,
			ChangesetBaselineEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_R",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_R = new FinderPath(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineEntryModelImpl.FINDER_CACHE_ENABLED,
			ChangesetBaselineEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_R",
			new String[] { Long.class.getName(), Long.class.getName() },
			ChangesetBaselineEntryModelImpl.CHANGESETBASELINECOLLECTIONID_COLUMN_BITMASK |
			ChangesetBaselineEntryModelImpl.RESOURCEPRIMKEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_R = new FinderPath(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_R",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the changeset baseline entries where changesetBaselineCollectionId = &#63; and resourcePrimKey = &#63;.
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param resourcePrimKey the resource prim key
	 * @return the matching changeset baseline entries
	 */
	@Override
	public List<ChangesetBaselineEntry> findByC_R(
		long changesetBaselineCollectionId, long resourcePrimKey) {
		return findByC_R(changesetBaselineCollectionId, resourcePrimKey,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the changeset baseline entries where changesetBaselineCollectionId = &#63; and resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetBaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of changeset baseline entries
	 * @param end the upper bound of the range of changeset baseline entries (not inclusive)
	 * @return the range of matching changeset baseline entries
	 */
	@Override
	public List<ChangesetBaselineEntry> findByC_R(
		long changesetBaselineCollectionId, long resourcePrimKey, int start,
		int end) {
		return findByC_R(changesetBaselineCollectionId, resourcePrimKey, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the changeset baseline entries where changesetBaselineCollectionId = &#63; and resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetBaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of changeset baseline entries
	 * @param end the upper bound of the range of changeset baseline entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset baseline entries
	 */
	@Override
	public List<ChangesetBaselineEntry> findByC_R(
		long changesetBaselineCollectionId, long resourcePrimKey, int start,
		int end, OrderByComparator<ChangesetBaselineEntry> orderByComparator) {
		return findByC_R(changesetBaselineCollectionId, resourcePrimKey, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the changeset baseline entries where changesetBaselineCollectionId = &#63; and resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ChangesetBaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of changeset baseline entries
	 * @param end the upper bound of the range of changeset baseline entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching changeset baseline entries
	 */
	@Override
	public List<ChangesetBaselineEntry> findByC_R(
		long changesetBaselineCollectionId, long resourcePrimKey, int start,
		int end, OrderByComparator<ChangesetBaselineEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_R;
			finderArgs = new Object[] {
					changesetBaselineCollectionId, resourcePrimKey
				};
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_R;
			finderArgs = new Object[] {
					changesetBaselineCollectionId, resourcePrimKey,
					
					start, end, orderByComparator
				};
		}

		List<ChangesetBaselineEntry> list = null;

		if (retrieveFromCache) {
			list = (List<ChangesetBaselineEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ChangesetBaselineEntry changesetBaselineEntry : list) {
					if ((changesetBaselineCollectionId != changesetBaselineEntry.getChangesetBaselineCollectionId()) ||
							(resourcePrimKey != changesetBaselineEntry.getResourcePrimKey())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_CHANGESETBASELINEENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_R_CHANGESETBASELINECOLLECTIONID_2);

			query.append(_FINDER_COLUMN_C_R_RESOURCEPRIMKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ChangesetBaselineEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(changesetBaselineCollectionId);

				qPos.add(resourcePrimKey);

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
	 * Returns the first changeset baseline entry in the ordered set where changesetBaselineCollectionId = &#63; and resourcePrimKey = &#63;.
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset baseline entry
	 * @throws NoSuchBaselineEntryException if a matching changeset baseline entry could not be found
	 */
	@Override
	public ChangesetBaselineEntry findByC_R_First(
		long changesetBaselineCollectionId, long resourcePrimKey,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator)
		throws NoSuchBaselineEntryException {
		ChangesetBaselineEntry changesetBaselineEntry = fetchByC_R_First(changesetBaselineCollectionId,
				resourcePrimKey, orderByComparator);

		if (changesetBaselineEntry != null) {
			return changesetBaselineEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("changesetBaselineCollectionId=");
		msg.append(changesetBaselineCollectionId);

		msg.append(", resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append("}");

		throw new NoSuchBaselineEntryException(msg.toString());
	}

	/**
	 * Returns the first changeset baseline entry in the ordered set where changesetBaselineCollectionId = &#63; and resourcePrimKey = &#63;.
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset baseline entry, or <code>null</code> if a matching changeset baseline entry could not be found
	 */
	@Override
	public ChangesetBaselineEntry fetchByC_R_First(
		long changesetBaselineCollectionId, long resourcePrimKey,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator) {
		List<ChangesetBaselineEntry> list = findByC_R(changesetBaselineCollectionId,
				resourcePrimKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last changeset baseline entry in the ordered set where changesetBaselineCollectionId = &#63; and resourcePrimKey = &#63;.
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset baseline entry
	 * @throws NoSuchBaselineEntryException if a matching changeset baseline entry could not be found
	 */
	@Override
	public ChangesetBaselineEntry findByC_R_Last(
		long changesetBaselineCollectionId, long resourcePrimKey,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator)
		throws NoSuchBaselineEntryException {
		ChangesetBaselineEntry changesetBaselineEntry = fetchByC_R_Last(changesetBaselineCollectionId,
				resourcePrimKey, orderByComparator);

		if (changesetBaselineEntry != null) {
			return changesetBaselineEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("changesetBaselineCollectionId=");
		msg.append(changesetBaselineCollectionId);

		msg.append(", resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append("}");

		throw new NoSuchBaselineEntryException(msg.toString());
	}

	/**
	 * Returns the last changeset baseline entry in the ordered set where changesetBaselineCollectionId = &#63; and resourcePrimKey = &#63;.
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset baseline entry, or <code>null</code> if a matching changeset baseline entry could not be found
	 */
	@Override
	public ChangesetBaselineEntry fetchByC_R_Last(
		long changesetBaselineCollectionId, long resourcePrimKey,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator) {
		int count = countByC_R(changesetBaselineCollectionId, resourcePrimKey);

		if (count == 0) {
			return null;
		}

		List<ChangesetBaselineEntry> list = findByC_R(changesetBaselineCollectionId,
				resourcePrimKey, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the changeset baseline entries before and after the current changeset baseline entry in the ordered set where changesetBaselineCollectionId = &#63; and resourcePrimKey = &#63;.
	 *
	 * @param changesetBaselineEntryId the primary key of the current changeset baseline entry
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next changeset baseline entry
	 * @throws NoSuchBaselineEntryException if a changeset baseline entry with the primary key could not be found
	 */
	@Override
	public ChangesetBaselineEntry[] findByC_R_PrevAndNext(
		long changesetBaselineEntryId, long changesetBaselineCollectionId,
		long resourcePrimKey,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator)
		throws NoSuchBaselineEntryException {
		ChangesetBaselineEntry changesetBaselineEntry = findByPrimaryKey(changesetBaselineEntryId);

		Session session = null;

		try {
			session = openSession();

			ChangesetBaselineEntry[] array = new ChangesetBaselineEntryImpl[3];

			array[0] = getByC_R_PrevAndNext(session, changesetBaselineEntry,
					changesetBaselineCollectionId, resourcePrimKey,
					orderByComparator, true);

			array[1] = changesetBaselineEntry;

			array[2] = getByC_R_PrevAndNext(session, changesetBaselineEntry,
					changesetBaselineCollectionId, resourcePrimKey,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ChangesetBaselineEntry getByC_R_PrevAndNext(Session session,
		ChangesetBaselineEntry changesetBaselineEntry,
		long changesetBaselineCollectionId, long resourcePrimKey,
		OrderByComparator<ChangesetBaselineEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_CHANGESETBASELINEENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_R_CHANGESETBASELINECOLLECTIONID_2);

		query.append(_FINDER_COLUMN_C_R_RESOURCEPRIMKEY_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ChangesetBaselineEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(changesetBaselineCollectionId);

		qPos.add(resourcePrimKey);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(changesetBaselineEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ChangesetBaselineEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the changeset baseline entries where changesetBaselineCollectionId = &#63; and resourcePrimKey = &#63; from the database.
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param resourcePrimKey the resource prim key
	 */
	@Override
	public void removeByC_R(long changesetBaselineCollectionId,
		long resourcePrimKey) {
		for (ChangesetBaselineEntry changesetBaselineEntry : findByC_R(
				changesetBaselineCollectionId, resourcePrimKey,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(changesetBaselineEntry);
		}
	}

	/**
	 * Returns the number of changeset baseline entries where changesetBaselineCollectionId = &#63; and resourcePrimKey = &#63;.
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param resourcePrimKey the resource prim key
	 * @return the number of matching changeset baseline entries
	 */
	@Override
	public int countByC_R(long changesetBaselineCollectionId,
		long resourcePrimKey) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_R;

		Object[] finderArgs = new Object[] {
				changesetBaselineCollectionId, resourcePrimKey
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CHANGESETBASELINEENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_R_CHANGESETBASELINECOLLECTIONID_2);

			query.append(_FINDER_COLUMN_C_R_RESOURCEPRIMKEY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(changesetBaselineCollectionId);

				qPos.add(resourcePrimKey);

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

	private static final String _FINDER_COLUMN_C_R_CHANGESETBASELINECOLLECTIONID_2 =
		"changesetBaselineEntry.changesetBaselineCollectionId = ? AND ";
	private static final String _FINDER_COLUMN_C_R_RESOURCEPRIMKEY_2 = "changesetBaselineEntry.resourcePrimKey = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C_C = new FinderPath(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineEntryModelImpl.FINDER_CACHE_ENABLED,
			ChangesetBaselineEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			ChangesetBaselineEntryModelImpl.CHANGESETBASELINECOLLECTIONID_COLUMN_BITMASK |
			ChangesetBaselineEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			ChangesetBaselineEntryModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_C = new FinderPath(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns the changeset baseline entry where changesetBaselineCollectionId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchBaselineEntryException} if it could not be found.
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching changeset baseline entry
	 * @throws NoSuchBaselineEntryException if a matching changeset baseline entry could not be found
	 */
	@Override
	public ChangesetBaselineEntry findByC_C_C(
		long changesetBaselineCollectionId, long classNameId, long classPK)
		throws NoSuchBaselineEntryException {
		ChangesetBaselineEntry changesetBaselineEntry = fetchByC_C_C(changesetBaselineCollectionId,
				classNameId, classPK);

		if (changesetBaselineEntry == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("changesetBaselineCollectionId=");
			msg.append(changesetBaselineCollectionId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchBaselineEntryException(msg.toString());
		}

		return changesetBaselineEntry;
	}

	/**
	 * Returns the changeset baseline entry where changesetBaselineCollectionId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching changeset baseline entry, or <code>null</code> if a matching changeset baseline entry could not be found
	 */
	@Override
	public ChangesetBaselineEntry fetchByC_C_C(
		long changesetBaselineCollectionId, long classNameId, long classPK) {
		return fetchByC_C_C(changesetBaselineCollectionId, classNameId,
			classPK, true);
	}

	/**
	 * Returns the changeset baseline entry where changesetBaselineCollectionId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching changeset baseline entry, or <code>null</code> if a matching changeset baseline entry could not be found
	 */
	@Override
	public ChangesetBaselineEntry fetchByC_C_C(
		long changesetBaselineCollectionId, long classNameId, long classPK,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				changesetBaselineCollectionId, classNameId, classPK
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_C_C,
					finderArgs, this);
		}

		if (result instanceof ChangesetBaselineEntry) {
			ChangesetBaselineEntry changesetBaselineEntry = (ChangesetBaselineEntry)result;

			if ((changesetBaselineCollectionId != changesetBaselineEntry.getChangesetBaselineCollectionId()) ||
					(classNameId != changesetBaselineEntry.getClassNameId()) ||
					(classPK != changesetBaselineEntry.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_CHANGESETBASELINEENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_C_CHANGESETBASELINECOLLECTIONID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(changesetBaselineCollectionId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<ChangesetBaselineEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_C_C,
						finderArgs, list);
				}
				else {
					ChangesetBaselineEntry changesetBaselineEntry = list.get(0);

					result = changesetBaselineEntry;

					cacheResult(changesetBaselineEntry);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C_C, finderArgs);

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
			return (ChangesetBaselineEntry)result;
		}
	}

	/**
	 * Removes the changeset baseline entry where changesetBaselineCollectionId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the changeset baseline entry that was removed
	 */
	@Override
	public ChangesetBaselineEntry removeByC_C_C(
		long changesetBaselineCollectionId, long classNameId, long classPK)
		throws NoSuchBaselineEntryException {
		ChangesetBaselineEntry changesetBaselineEntry = findByC_C_C(changesetBaselineCollectionId,
				classNameId, classPK);

		return remove(changesetBaselineEntry);
	}

	/**
	 * Returns the number of changeset baseline entries where changesetBaselineCollectionId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param changesetBaselineCollectionId the changeset baseline collection ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching changeset baseline entries
	 */
	@Override
	public int countByC_C_C(long changesetBaselineCollectionId,
		long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C_C;

		Object[] finderArgs = new Object[] {
				changesetBaselineCollectionId, classNameId, classPK
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_CHANGESETBASELINEENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_C_CHANGESETBASELINECOLLECTIONID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(changesetBaselineCollectionId);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_C_CHANGESETBASELINECOLLECTIONID_2 =
		"changesetBaselineEntry.changesetBaselineCollectionId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_CLASSNAMEID_2 = "changesetBaselineEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 = "changesetBaselineEntry.classPK = ?";

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

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_C_C,
			new Object[] {
				changesetBaselineEntry.getChangesetBaselineCollectionId(),
				changesetBaselineEntry.getClassNameId(),
				changesetBaselineEntry.getClassPK()
			}, changesetBaselineEntry);

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

		clearUniqueFindersCache((ChangesetBaselineEntryModelImpl)changesetBaselineEntry,
			true);
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

			clearUniqueFindersCache((ChangesetBaselineEntryModelImpl)changesetBaselineEntry,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		ChangesetBaselineEntryModelImpl changesetBaselineEntryModelImpl) {
		Object[] args = new Object[] {
				changesetBaselineEntryModelImpl.getChangesetBaselineCollectionId(),
				changesetBaselineEntryModelImpl.getClassNameId(),
				changesetBaselineEntryModelImpl.getClassPK()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_C_C, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_C_C, args,
			changesetBaselineEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		ChangesetBaselineEntryModelImpl changesetBaselineEntryModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					changesetBaselineEntryModelImpl.getChangesetBaselineCollectionId(),
					changesetBaselineEntryModelImpl.getClassNameId(),
					changesetBaselineEntryModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C_C, args);
		}

		if ((changesetBaselineEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_C_C.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					changesetBaselineEntryModelImpl.getOriginalChangesetBaselineCollectionId(),
					changesetBaselineEntryModelImpl.getOriginalClassNameId(),
					changesetBaselineEntryModelImpl.getOriginalClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C_C, args);
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

		if (!ChangesetBaselineEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					changesetBaselineEntryModelImpl.getResourcePrimKey()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY,
				args);

			args = new Object[] {
					changesetBaselineEntryModelImpl.getChangesetBaselineCollectionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CHANGESETBASELINECOLLECTIONID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CHANGESETBASELINECOLLECTIONID,
				args);

			args = new Object[] {
					changesetBaselineEntryModelImpl.getChangesetBaselineCollectionId(),
					changesetBaselineEntryModelImpl.getResourcePrimKey()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_R, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_R,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((changesetBaselineEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						changesetBaselineEntryModelImpl.getOriginalResourcePrimKey()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY,
					args);

				args = new Object[] {
						changesetBaselineEntryModelImpl.getResourcePrimKey()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY,
					args);
			}

			if ((changesetBaselineEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CHANGESETBASELINECOLLECTIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						changesetBaselineEntryModelImpl.getOriginalChangesetBaselineCollectionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CHANGESETBASELINECOLLECTIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CHANGESETBASELINECOLLECTIONID,
					args);

				args = new Object[] {
						changesetBaselineEntryModelImpl.getChangesetBaselineCollectionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CHANGESETBASELINECOLLECTIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CHANGESETBASELINECOLLECTIONID,
					args);
			}

			if ((changesetBaselineEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_R.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						changesetBaselineEntryModelImpl.getOriginalChangesetBaselineCollectionId(),
						changesetBaselineEntryModelImpl.getOriginalResourcePrimKey()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_R, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_R,
					args);

				args = new Object[] {
						changesetBaselineEntryModelImpl.getChangesetBaselineCollectionId(),
						changesetBaselineEntryModelImpl.getResourcePrimKey()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_R, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_R,
					args);
			}
		}

		entityCache.putResult(ChangesetBaselineEntryModelImpl.ENTITY_CACHE_ENABLED,
			ChangesetBaselineEntryImpl.class,
			changesetBaselineEntry.getPrimaryKey(), changesetBaselineEntry,
			false);

		clearUniqueFindersCache(changesetBaselineEntryModelImpl, false);
		cacheUniqueFindersCache(changesetBaselineEntryModelImpl);

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
	private static final String _SQL_SELECT_CHANGESETBASELINEENTRY_WHERE = "SELECT changesetBaselineEntry FROM ChangesetBaselineEntry changesetBaselineEntry WHERE ";
	private static final String _SQL_COUNT_CHANGESETBASELINEENTRY = "SELECT COUNT(changesetBaselineEntry) FROM ChangesetBaselineEntry changesetBaselineEntry";
	private static final String _SQL_COUNT_CHANGESETBASELINEENTRY_WHERE = "SELECT COUNT(changesetBaselineEntry) FROM ChangesetBaselineEntry changesetBaselineEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "changesetBaselineEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ChangesetBaselineEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ChangesetBaselineEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(ChangesetBaselineEntryPersistenceImpl.class);
}
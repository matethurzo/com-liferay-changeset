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

package com.liferay.changeset.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.changeset.exception.NoSuchBaselineCollectionException;
import com.liferay.changeset.model.ChangesetBaselineCollection;
import com.liferay.changeset.service.ChangesetBaselineCollectionLocalServiceUtil;
import com.liferay.changeset.service.persistence.ChangesetBaselineCollectionPersistence;
import com.liferay.changeset.service.persistence.ChangesetBaselineCollectionUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class ChangesetBaselineCollectionPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.changeset.service"));

	@Before
	public void setUp() {
		_persistence = ChangesetBaselineCollectionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ChangesetBaselineCollection> iterator = _changesetBaselineCollections.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangesetBaselineCollection changesetBaselineCollection = _persistence.create(pk);

		Assert.assertNotNull(changesetBaselineCollection);

		Assert.assertEquals(changesetBaselineCollection.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ChangesetBaselineCollection newChangesetBaselineCollection = addChangesetBaselineCollection();

		_persistence.remove(newChangesetBaselineCollection);

		ChangesetBaselineCollection existingChangesetBaselineCollection = _persistence.fetchByPrimaryKey(newChangesetBaselineCollection.getPrimaryKey());

		Assert.assertNull(existingChangesetBaselineCollection);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addChangesetBaselineCollection();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangesetBaselineCollection newChangesetBaselineCollection = _persistence.create(pk);

		newChangesetBaselineCollection.setCompanyId(RandomTestUtil.nextLong());

		newChangesetBaselineCollection.setUserId(RandomTestUtil.nextLong());

		newChangesetBaselineCollection.setUserName(RandomTestUtil.randomString());

		newChangesetBaselineCollection.setCreateDate(RandomTestUtil.nextDate());

		newChangesetBaselineCollection.setModifiedDate(RandomTestUtil.nextDate());

		newChangesetBaselineCollection.setName(RandomTestUtil.randomString());

		_changesetBaselineCollections.add(_persistence.update(
				newChangesetBaselineCollection));

		ChangesetBaselineCollection existingChangesetBaselineCollection = _persistence.findByPrimaryKey(newChangesetBaselineCollection.getPrimaryKey());

		Assert.assertEquals(existingChangesetBaselineCollection.getChangesetBaselineCollectionId(),
			newChangesetBaselineCollection.getChangesetBaselineCollectionId());
		Assert.assertEquals(existingChangesetBaselineCollection.getCompanyId(),
			newChangesetBaselineCollection.getCompanyId());
		Assert.assertEquals(existingChangesetBaselineCollection.getUserId(),
			newChangesetBaselineCollection.getUserId());
		Assert.assertEquals(existingChangesetBaselineCollection.getUserName(),
			newChangesetBaselineCollection.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingChangesetBaselineCollection.getCreateDate()),
			Time.getShortTimestamp(
				newChangesetBaselineCollection.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingChangesetBaselineCollection.getModifiedDate()),
			Time.getShortTimestamp(
				newChangesetBaselineCollection.getModifiedDate()));
		Assert.assertEquals(existingChangesetBaselineCollection.getName(),
			newChangesetBaselineCollection.getName());
	}

	@Test
	public void testCountByName() throws Exception {
		_persistence.countByName("");

		_persistence.countByName("null");

		_persistence.countByName((String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ChangesetBaselineCollection newChangesetBaselineCollection = addChangesetBaselineCollection();

		ChangesetBaselineCollection existingChangesetBaselineCollection = _persistence.findByPrimaryKey(newChangesetBaselineCollection.getPrimaryKey());

		Assert.assertEquals(existingChangesetBaselineCollection,
			newChangesetBaselineCollection);
	}

	@Test(expected = NoSuchBaselineCollectionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<ChangesetBaselineCollection> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ChangesetBaselineCollection",
			"changesetBaselineCollectionId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"name", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ChangesetBaselineCollection newChangesetBaselineCollection = addChangesetBaselineCollection();

		ChangesetBaselineCollection existingChangesetBaselineCollection = _persistence.fetchByPrimaryKey(newChangesetBaselineCollection.getPrimaryKey());

		Assert.assertEquals(existingChangesetBaselineCollection,
			newChangesetBaselineCollection);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangesetBaselineCollection missingChangesetBaselineCollection = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingChangesetBaselineCollection);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ChangesetBaselineCollection newChangesetBaselineCollection1 = addChangesetBaselineCollection();
		ChangesetBaselineCollection newChangesetBaselineCollection2 = addChangesetBaselineCollection();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChangesetBaselineCollection1.getPrimaryKey());
		primaryKeys.add(newChangesetBaselineCollection2.getPrimaryKey());

		Map<Serializable, ChangesetBaselineCollection> changesetBaselineCollections =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, changesetBaselineCollections.size());
		Assert.assertEquals(newChangesetBaselineCollection1,
			changesetBaselineCollections.get(
				newChangesetBaselineCollection1.getPrimaryKey()));
		Assert.assertEquals(newChangesetBaselineCollection2,
			changesetBaselineCollections.get(
				newChangesetBaselineCollection2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ChangesetBaselineCollection> changesetBaselineCollections =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(changesetBaselineCollections.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ChangesetBaselineCollection newChangesetBaselineCollection = addChangesetBaselineCollection();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChangesetBaselineCollection.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ChangesetBaselineCollection> changesetBaselineCollections =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, changesetBaselineCollections.size());
		Assert.assertEquals(newChangesetBaselineCollection,
			changesetBaselineCollections.get(
				newChangesetBaselineCollection.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ChangesetBaselineCollection> changesetBaselineCollections =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(changesetBaselineCollections.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ChangesetBaselineCollection newChangesetBaselineCollection = addChangesetBaselineCollection();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChangesetBaselineCollection.getPrimaryKey());

		Map<Serializable, ChangesetBaselineCollection> changesetBaselineCollections =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, changesetBaselineCollections.size());
		Assert.assertEquals(newChangesetBaselineCollection,
			changesetBaselineCollections.get(
				newChangesetBaselineCollection.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ChangesetBaselineCollectionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<ChangesetBaselineCollection>() {
				@Override
				public void performAction(
					ChangesetBaselineCollection changesetBaselineCollection) {
					Assert.assertNotNull(changesetBaselineCollection);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ChangesetBaselineCollection newChangesetBaselineCollection = addChangesetBaselineCollection();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangesetBaselineCollection.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"changesetBaselineCollectionId",
				newChangesetBaselineCollection.getChangesetBaselineCollectionId()));

		List<ChangesetBaselineCollection> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ChangesetBaselineCollection existingChangesetBaselineCollection = result.get(0);

		Assert.assertEquals(existingChangesetBaselineCollection,
			newChangesetBaselineCollection);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangesetBaselineCollection.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"changesetBaselineCollectionId", RandomTestUtil.nextLong()));

		List<ChangesetBaselineCollection> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ChangesetBaselineCollection newChangesetBaselineCollection = addChangesetBaselineCollection();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangesetBaselineCollection.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"changesetBaselineCollectionId"));

		Object newChangesetBaselineCollectionId = newChangesetBaselineCollection.getChangesetBaselineCollectionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"changesetBaselineCollectionId",
				new Object[] { newChangesetBaselineCollectionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingChangesetBaselineCollectionId = result.get(0);

		Assert.assertEquals(existingChangesetBaselineCollectionId,
			newChangesetBaselineCollectionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangesetBaselineCollection.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"changesetBaselineCollectionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"changesetBaselineCollectionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		ChangesetBaselineCollection newChangesetBaselineCollection = addChangesetBaselineCollection();

		_persistence.clearCache();

		ChangesetBaselineCollection existingChangesetBaselineCollection = _persistence.findByPrimaryKey(newChangesetBaselineCollection.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingChangesetBaselineCollection.getName(),
				ReflectionTestUtil.invoke(existingChangesetBaselineCollection,
					"getOriginalName", new Class<?>[0])));
	}

	protected ChangesetBaselineCollection addChangesetBaselineCollection()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangesetBaselineCollection changesetBaselineCollection = _persistence.create(pk);

		changesetBaselineCollection.setCompanyId(RandomTestUtil.nextLong());

		changesetBaselineCollection.setUserId(RandomTestUtil.nextLong());

		changesetBaselineCollection.setUserName(RandomTestUtil.randomString());

		changesetBaselineCollection.setCreateDate(RandomTestUtil.nextDate());

		changesetBaselineCollection.setModifiedDate(RandomTestUtil.nextDate());

		changesetBaselineCollection.setName(RandomTestUtil.randomString());

		_changesetBaselineCollections.add(_persistence.update(
				changesetBaselineCollection));

		return changesetBaselineCollection;
	}

	private List<ChangesetBaselineCollection> _changesetBaselineCollections = new ArrayList<ChangesetBaselineCollection>();
	private ChangesetBaselineCollectionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}
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

import com.liferay.changeset.exception.NoSuchBaselineEntryException;
import com.liferay.changeset.model.ChangesetBaselineEntry;
import com.liferay.changeset.service.ChangesetBaselineEntryLocalServiceUtil;
import com.liferay.changeset.service.persistence.ChangesetBaselineEntryPersistence;
import com.liferay.changeset.service.persistence.ChangesetBaselineEntryUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
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
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class ChangesetBaselineEntryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.changeset.service"));

	@Before
	public void setUp() {
		_persistence = ChangesetBaselineEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ChangesetBaselineEntry> iterator = _changesetBaselineEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangesetBaselineEntry changesetBaselineEntry = _persistence.create(pk);

		Assert.assertNotNull(changesetBaselineEntry);

		Assert.assertEquals(changesetBaselineEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ChangesetBaselineEntry newChangesetBaselineEntry = addChangesetBaselineEntry();

		_persistence.remove(newChangesetBaselineEntry);

		ChangesetBaselineEntry existingChangesetBaselineEntry = _persistence.fetchByPrimaryKey(newChangesetBaselineEntry.getPrimaryKey());

		Assert.assertNull(existingChangesetBaselineEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addChangesetBaselineEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangesetBaselineEntry newChangesetBaselineEntry = _persistence.create(pk);

		newChangesetBaselineEntry.setCompanyId(RandomTestUtil.nextLong());

		newChangesetBaselineEntry.setUserId(RandomTestUtil.nextLong());

		newChangesetBaselineEntry.setUserName(RandomTestUtil.randomString());

		newChangesetBaselineEntry.setCreateDate(RandomTestUtil.nextDate());

		newChangesetBaselineEntry.setModifiedDate(RandomTestUtil.nextDate());

		newChangesetBaselineEntry.setChangesetBaselineCollectionId(RandomTestUtil.nextLong());

		newChangesetBaselineEntry.setClassNameId(RandomTestUtil.nextLong());

		newChangesetBaselineEntry.setClassPK(RandomTestUtil.nextLong());

		newChangesetBaselineEntry.setVersion(RandomTestUtil.nextDouble());

		_changesetBaselineEntries.add(_persistence.update(
				newChangesetBaselineEntry));

		ChangesetBaselineEntry existingChangesetBaselineEntry = _persistence.findByPrimaryKey(newChangesetBaselineEntry.getPrimaryKey());

		Assert.assertEquals(existingChangesetBaselineEntry.getChangesetBaselineEntryId(),
			newChangesetBaselineEntry.getChangesetBaselineEntryId());
		Assert.assertEquals(existingChangesetBaselineEntry.getCompanyId(),
			newChangesetBaselineEntry.getCompanyId());
		Assert.assertEquals(existingChangesetBaselineEntry.getUserId(),
			newChangesetBaselineEntry.getUserId());
		Assert.assertEquals(existingChangesetBaselineEntry.getUserName(),
			newChangesetBaselineEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingChangesetBaselineEntry.getCreateDate()),
			Time.getShortTimestamp(newChangesetBaselineEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingChangesetBaselineEntry.getModifiedDate()),
			Time.getShortTimestamp(newChangesetBaselineEntry.getModifiedDate()));
		Assert.assertEquals(existingChangesetBaselineEntry.getChangesetBaselineCollectionId(),
			newChangesetBaselineEntry.getChangesetBaselineCollectionId());
		Assert.assertEquals(existingChangesetBaselineEntry.getClassNameId(),
			newChangesetBaselineEntry.getClassNameId());
		Assert.assertEquals(existingChangesetBaselineEntry.getClassPK(),
			newChangesetBaselineEntry.getClassPK());
		AssertUtils.assertEquals(existingChangesetBaselineEntry.getVersion(),
			newChangesetBaselineEntry.getVersion());
	}

	@Test
	public void testCountByChangesetBaselineCollectionId()
		throws Exception {
		_persistence.countByChangesetBaselineCollectionId(RandomTestUtil.nextLong());

		_persistence.countByChangesetBaselineCollectionId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ChangesetBaselineEntry newChangesetBaselineEntry = addChangesetBaselineEntry();

		ChangesetBaselineEntry existingChangesetBaselineEntry = _persistence.findByPrimaryKey(newChangesetBaselineEntry.getPrimaryKey());

		Assert.assertEquals(existingChangesetBaselineEntry,
			newChangesetBaselineEntry);
	}

	@Test(expected = NoSuchBaselineEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<ChangesetBaselineEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ChangesetBaselineEntry",
			"changesetBaselineEntryId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"changesetBaselineCollectionId", true, "classNameId", true,
			"classPK", true, "version", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ChangesetBaselineEntry newChangesetBaselineEntry = addChangesetBaselineEntry();

		ChangesetBaselineEntry existingChangesetBaselineEntry = _persistence.fetchByPrimaryKey(newChangesetBaselineEntry.getPrimaryKey());

		Assert.assertEquals(existingChangesetBaselineEntry,
			newChangesetBaselineEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangesetBaselineEntry missingChangesetBaselineEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingChangesetBaselineEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ChangesetBaselineEntry newChangesetBaselineEntry1 = addChangesetBaselineEntry();
		ChangesetBaselineEntry newChangesetBaselineEntry2 = addChangesetBaselineEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChangesetBaselineEntry1.getPrimaryKey());
		primaryKeys.add(newChangesetBaselineEntry2.getPrimaryKey());

		Map<Serializable, ChangesetBaselineEntry> changesetBaselineEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, changesetBaselineEntries.size());
		Assert.assertEquals(newChangesetBaselineEntry1,
			changesetBaselineEntries.get(
				newChangesetBaselineEntry1.getPrimaryKey()));
		Assert.assertEquals(newChangesetBaselineEntry2,
			changesetBaselineEntries.get(
				newChangesetBaselineEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ChangesetBaselineEntry> changesetBaselineEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(changesetBaselineEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ChangesetBaselineEntry newChangesetBaselineEntry = addChangesetBaselineEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChangesetBaselineEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ChangesetBaselineEntry> changesetBaselineEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, changesetBaselineEntries.size());
		Assert.assertEquals(newChangesetBaselineEntry,
			changesetBaselineEntries.get(
				newChangesetBaselineEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ChangesetBaselineEntry> changesetBaselineEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(changesetBaselineEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ChangesetBaselineEntry newChangesetBaselineEntry = addChangesetBaselineEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChangesetBaselineEntry.getPrimaryKey());

		Map<Serializable, ChangesetBaselineEntry> changesetBaselineEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, changesetBaselineEntries.size());
		Assert.assertEquals(newChangesetBaselineEntry,
			changesetBaselineEntries.get(
				newChangesetBaselineEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ChangesetBaselineEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<ChangesetBaselineEntry>() {
				@Override
				public void performAction(
					ChangesetBaselineEntry changesetBaselineEntry) {
					Assert.assertNotNull(changesetBaselineEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ChangesetBaselineEntry newChangesetBaselineEntry = addChangesetBaselineEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangesetBaselineEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"changesetBaselineEntryId",
				newChangesetBaselineEntry.getChangesetBaselineEntryId()));

		List<ChangesetBaselineEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ChangesetBaselineEntry existingChangesetBaselineEntry = result.get(0);

		Assert.assertEquals(existingChangesetBaselineEntry,
			newChangesetBaselineEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangesetBaselineEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"changesetBaselineEntryId", RandomTestUtil.nextLong()));

		List<ChangesetBaselineEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ChangesetBaselineEntry newChangesetBaselineEntry = addChangesetBaselineEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangesetBaselineEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"changesetBaselineEntryId"));

		Object newChangesetBaselineEntryId = newChangesetBaselineEntry.getChangesetBaselineEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"changesetBaselineEntryId",
				new Object[] { newChangesetBaselineEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingChangesetBaselineEntryId = result.get(0);

		Assert.assertEquals(existingChangesetBaselineEntryId,
			newChangesetBaselineEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ChangesetBaselineEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"changesetBaselineEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"changesetBaselineEntryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ChangesetBaselineEntry addChangesetBaselineEntry()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		ChangesetBaselineEntry changesetBaselineEntry = _persistence.create(pk);

		changesetBaselineEntry.setCompanyId(RandomTestUtil.nextLong());

		changesetBaselineEntry.setUserId(RandomTestUtil.nextLong());

		changesetBaselineEntry.setUserName(RandomTestUtil.randomString());

		changesetBaselineEntry.setCreateDate(RandomTestUtil.nextDate());

		changesetBaselineEntry.setModifiedDate(RandomTestUtil.nextDate());

		changesetBaselineEntry.setChangesetBaselineCollectionId(RandomTestUtil.nextLong());

		changesetBaselineEntry.setClassNameId(RandomTestUtil.nextLong());

		changesetBaselineEntry.setClassPK(RandomTestUtil.nextLong());

		changesetBaselineEntry.setVersion(RandomTestUtil.nextDouble());

		_changesetBaselineEntries.add(_persistence.update(
				changesetBaselineEntry));

		return changesetBaselineEntry;
	}

	private List<ChangesetBaselineEntry> _changesetBaselineEntries = new ArrayList<ChangesetBaselineEntry>();
	private ChangesetBaselineEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}
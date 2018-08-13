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

package com.liferay.changeset.baseline.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.changeset.baseline.exception.NoSuchBaselineEntryException;
import com.liferay.changeset.baseline.model.BaselineEntry;
import com.liferay.changeset.baseline.service.BaselineEntryLocalServiceUtil;
import com.liferay.changeset.baseline.service.persistence.BaselineEntryPersistence;
import com.liferay.changeset.baseline.service.persistence.BaselineEntryUtil;

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
public class BaselineEntryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.changeset.baseline.service"));

	@Before
	public void setUp() {
		_persistence = BaselineEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<BaselineEntry> iterator = _baselineEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BaselineEntry baselineEntry = _persistence.create(pk);

		Assert.assertNotNull(baselineEntry);

		Assert.assertEquals(baselineEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		BaselineEntry newBaselineEntry = addBaselineEntry();

		_persistence.remove(newBaselineEntry);

		BaselineEntry existingBaselineEntry = _persistence.fetchByPrimaryKey(newBaselineEntry.getPrimaryKey());

		Assert.assertNull(existingBaselineEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addBaselineEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BaselineEntry newBaselineEntry = _persistence.create(pk);

		newBaselineEntry.setCompanyId(RandomTestUtil.nextLong());

		newBaselineEntry.setUserId(RandomTestUtil.nextLong());

		newBaselineEntry.setUserName(RandomTestUtil.randomString());

		newBaselineEntry.setCreateDate(RandomTestUtil.nextDate());

		newBaselineEntry.setModifiedDate(RandomTestUtil.nextDate());

		newBaselineEntry.setBaselineInformationId(RandomTestUtil.nextLong());

		newBaselineEntry.setClassNameId(RandomTestUtil.nextLong());

		newBaselineEntry.setClassPK(RandomTestUtil.nextLong());

		newBaselineEntry.setVersion(RandomTestUtil.nextDouble());

		_baselineEntries.add(_persistence.update(newBaselineEntry));

		BaselineEntry existingBaselineEntry = _persistence.findByPrimaryKey(newBaselineEntry.getPrimaryKey());

		Assert.assertEquals(existingBaselineEntry.getBaselineEntryId(),
			newBaselineEntry.getBaselineEntryId());
		Assert.assertEquals(existingBaselineEntry.getCompanyId(),
			newBaselineEntry.getCompanyId());
		Assert.assertEquals(existingBaselineEntry.getUserId(),
			newBaselineEntry.getUserId());
		Assert.assertEquals(existingBaselineEntry.getUserName(),
			newBaselineEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingBaselineEntry.getCreateDate()),
			Time.getShortTimestamp(newBaselineEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingBaselineEntry.getModifiedDate()),
			Time.getShortTimestamp(newBaselineEntry.getModifiedDate()));
		Assert.assertEquals(existingBaselineEntry.getBaselineInformationId(),
			newBaselineEntry.getBaselineInformationId());
		Assert.assertEquals(existingBaselineEntry.getClassNameId(),
			newBaselineEntry.getClassNameId());
		Assert.assertEquals(existingBaselineEntry.getClassPK(),
			newBaselineEntry.getClassPK());
		AssertUtils.assertEquals(existingBaselineEntry.getVersion(),
			newBaselineEntry.getVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		BaselineEntry newBaselineEntry = addBaselineEntry();

		BaselineEntry existingBaselineEntry = _persistence.findByPrimaryKey(newBaselineEntry.getPrimaryKey());

		Assert.assertEquals(existingBaselineEntry, newBaselineEntry);
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

	protected OrderByComparator<BaselineEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("BaselineEntry",
			"baselineEntryId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"baselineInformationId", true, "classNameId", true, "classPK",
			true, "version", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		BaselineEntry newBaselineEntry = addBaselineEntry();

		BaselineEntry existingBaselineEntry = _persistence.fetchByPrimaryKey(newBaselineEntry.getPrimaryKey());

		Assert.assertEquals(existingBaselineEntry, newBaselineEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BaselineEntry missingBaselineEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingBaselineEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		BaselineEntry newBaselineEntry1 = addBaselineEntry();
		BaselineEntry newBaselineEntry2 = addBaselineEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBaselineEntry1.getPrimaryKey());
		primaryKeys.add(newBaselineEntry2.getPrimaryKey());

		Map<Serializable, BaselineEntry> baselineEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, baselineEntries.size());
		Assert.assertEquals(newBaselineEntry1,
			baselineEntries.get(newBaselineEntry1.getPrimaryKey()));
		Assert.assertEquals(newBaselineEntry2,
			baselineEntries.get(newBaselineEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, BaselineEntry> baselineEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(baselineEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		BaselineEntry newBaselineEntry = addBaselineEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBaselineEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, BaselineEntry> baselineEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, baselineEntries.size());
		Assert.assertEquals(newBaselineEntry,
			baselineEntries.get(newBaselineEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, BaselineEntry> baselineEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(baselineEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		BaselineEntry newBaselineEntry = addBaselineEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBaselineEntry.getPrimaryKey());

		Map<Serializable, BaselineEntry> baselineEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, baselineEntries.size());
		Assert.assertEquals(newBaselineEntry,
			baselineEntries.get(newBaselineEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = BaselineEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<BaselineEntry>() {
				@Override
				public void performAction(BaselineEntry baselineEntry) {
					Assert.assertNotNull(baselineEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		BaselineEntry newBaselineEntry = addBaselineEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BaselineEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("baselineEntryId",
				newBaselineEntry.getBaselineEntryId()));

		List<BaselineEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		BaselineEntry existingBaselineEntry = result.get(0);

		Assert.assertEquals(existingBaselineEntry, newBaselineEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BaselineEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("baselineEntryId",
				RandomTestUtil.nextLong()));

		List<BaselineEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		BaselineEntry newBaselineEntry = addBaselineEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BaselineEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"baselineEntryId"));

		Object newBaselineEntryId = newBaselineEntry.getBaselineEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("baselineEntryId",
				new Object[] { newBaselineEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingBaselineEntryId = result.get(0);

		Assert.assertEquals(existingBaselineEntryId, newBaselineEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BaselineEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"baselineEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("baselineEntryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected BaselineEntry addBaselineEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BaselineEntry baselineEntry = _persistence.create(pk);

		baselineEntry.setCompanyId(RandomTestUtil.nextLong());

		baselineEntry.setUserId(RandomTestUtil.nextLong());

		baselineEntry.setUserName(RandomTestUtil.randomString());

		baselineEntry.setCreateDate(RandomTestUtil.nextDate());

		baselineEntry.setModifiedDate(RandomTestUtil.nextDate());

		baselineEntry.setBaselineInformationId(RandomTestUtil.nextLong());

		baselineEntry.setClassNameId(RandomTestUtil.nextLong());

		baselineEntry.setClassPK(RandomTestUtil.nextLong());

		baselineEntry.setVersion(RandomTestUtil.nextDouble());

		_baselineEntries.add(_persistence.update(baselineEntry));

		return baselineEntry;
	}

	private List<BaselineEntry> _baselineEntries = new ArrayList<BaselineEntry>();
	private BaselineEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}
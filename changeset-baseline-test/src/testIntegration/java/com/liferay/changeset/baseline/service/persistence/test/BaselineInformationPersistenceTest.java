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

import com.liferay.changeset.baseline.exception.NoSuchBaselineInformationException;
import com.liferay.changeset.baseline.model.BaselineInformation;
import com.liferay.changeset.baseline.service.BaselineInformationLocalServiceUtil;
import com.liferay.changeset.baseline.service.persistence.BaselineInformationPersistence;
import com.liferay.changeset.baseline.service.persistence.BaselineInformationUtil;

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
public class BaselineInformationPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.changeset.baseline.service"));

	@Before
	public void setUp() {
		_persistence = BaselineInformationUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<BaselineInformation> iterator = _baselineInformations.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BaselineInformation baselineInformation = _persistence.create(pk);

		Assert.assertNotNull(baselineInformation);

		Assert.assertEquals(baselineInformation.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		BaselineInformation newBaselineInformation = addBaselineInformation();

		_persistence.remove(newBaselineInformation);

		BaselineInformation existingBaselineInformation = _persistence.fetchByPrimaryKey(newBaselineInformation.getPrimaryKey());

		Assert.assertNull(existingBaselineInformation);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addBaselineInformation();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BaselineInformation newBaselineInformation = _persistence.create(pk);

		newBaselineInformation.setCompanyId(RandomTestUtil.nextLong());

		newBaselineInformation.setUserId(RandomTestUtil.nextLong());

		newBaselineInformation.setUserName(RandomTestUtil.randomString());

		newBaselineInformation.setCreateDate(RandomTestUtil.nextDate());

		newBaselineInformation.setModifiedDate(RandomTestUtil.nextDate());

		newBaselineInformation.setName(RandomTestUtil.randomString());

		_baselineInformations.add(_persistence.update(newBaselineInformation));

		BaselineInformation existingBaselineInformation = _persistence.findByPrimaryKey(newBaselineInformation.getPrimaryKey());

		Assert.assertEquals(existingBaselineInformation.getBaselineInformationId(),
			newBaselineInformation.getBaselineInformationId());
		Assert.assertEquals(existingBaselineInformation.getCompanyId(),
			newBaselineInformation.getCompanyId());
		Assert.assertEquals(existingBaselineInformation.getUserId(),
			newBaselineInformation.getUserId());
		Assert.assertEquals(existingBaselineInformation.getUserName(),
			newBaselineInformation.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingBaselineInformation.getCreateDate()),
			Time.getShortTimestamp(newBaselineInformation.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingBaselineInformation.getModifiedDate()),
			Time.getShortTimestamp(newBaselineInformation.getModifiedDate()));
		Assert.assertEquals(existingBaselineInformation.getName(),
			newBaselineInformation.getName());
	}

	@Test
	public void testCountByName() throws Exception {
		_persistence.countByName("");

		_persistence.countByName("null");

		_persistence.countByName((String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		BaselineInformation newBaselineInformation = addBaselineInformation();

		BaselineInformation existingBaselineInformation = _persistence.findByPrimaryKey(newBaselineInformation.getPrimaryKey());

		Assert.assertEquals(existingBaselineInformation, newBaselineInformation);
	}

	@Test(expected = NoSuchBaselineInformationException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<BaselineInformation> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("BaselineInformation",
			"baselineInformationId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true, "name",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		BaselineInformation newBaselineInformation = addBaselineInformation();

		BaselineInformation existingBaselineInformation = _persistence.fetchByPrimaryKey(newBaselineInformation.getPrimaryKey());

		Assert.assertEquals(existingBaselineInformation, newBaselineInformation);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BaselineInformation missingBaselineInformation = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingBaselineInformation);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		BaselineInformation newBaselineInformation1 = addBaselineInformation();
		BaselineInformation newBaselineInformation2 = addBaselineInformation();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBaselineInformation1.getPrimaryKey());
		primaryKeys.add(newBaselineInformation2.getPrimaryKey());

		Map<Serializable, BaselineInformation> baselineInformations = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, baselineInformations.size());
		Assert.assertEquals(newBaselineInformation1,
			baselineInformations.get(newBaselineInformation1.getPrimaryKey()));
		Assert.assertEquals(newBaselineInformation2,
			baselineInformations.get(newBaselineInformation2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, BaselineInformation> baselineInformations = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(baselineInformations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		BaselineInformation newBaselineInformation = addBaselineInformation();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBaselineInformation.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, BaselineInformation> baselineInformations = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, baselineInformations.size());
		Assert.assertEquals(newBaselineInformation,
			baselineInformations.get(newBaselineInformation.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, BaselineInformation> baselineInformations = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(baselineInformations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		BaselineInformation newBaselineInformation = addBaselineInformation();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBaselineInformation.getPrimaryKey());

		Map<Serializable, BaselineInformation> baselineInformations = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, baselineInformations.size());
		Assert.assertEquals(newBaselineInformation,
			baselineInformations.get(newBaselineInformation.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = BaselineInformationLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<BaselineInformation>() {
				@Override
				public void performAction(
					BaselineInformation baselineInformation) {
					Assert.assertNotNull(baselineInformation);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		BaselineInformation newBaselineInformation = addBaselineInformation();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BaselineInformation.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("baselineInformationId",
				newBaselineInformation.getBaselineInformationId()));

		List<BaselineInformation> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		BaselineInformation existingBaselineInformation = result.get(0);

		Assert.assertEquals(existingBaselineInformation, newBaselineInformation);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BaselineInformation.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("baselineInformationId",
				RandomTestUtil.nextLong()));

		List<BaselineInformation> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		BaselineInformation newBaselineInformation = addBaselineInformation();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BaselineInformation.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"baselineInformationId"));

		Object newBaselineInformationId = newBaselineInformation.getBaselineInformationId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("baselineInformationId",
				new Object[] { newBaselineInformationId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingBaselineInformationId = result.get(0);

		Assert.assertEquals(existingBaselineInformationId,
			newBaselineInformationId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BaselineInformation.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"baselineInformationId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("baselineInformationId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		BaselineInformation newBaselineInformation = addBaselineInformation();

		_persistence.clearCache();

		BaselineInformation existingBaselineInformation = _persistence.findByPrimaryKey(newBaselineInformation.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingBaselineInformation.getName(),
				ReflectionTestUtil.invoke(existingBaselineInformation,
					"getOriginalName", new Class<?>[0])));
	}

	protected BaselineInformation addBaselineInformation()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		BaselineInformation baselineInformation = _persistence.create(pk);

		baselineInformation.setCompanyId(RandomTestUtil.nextLong());

		baselineInformation.setUserId(RandomTestUtil.nextLong());

		baselineInformation.setUserName(RandomTestUtil.randomString());

		baselineInformation.setCreateDate(RandomTestUtil.nextDate());

		baselineInformation.setModifiedDate(RandomTestUtil.nextDate());

		baselineInformation.setName(RandomTestUtil.randomString());

		_baselineInformations.add(_persistence.update(baselineInformation));

		return baselineInformation;
	}

	private List<BaselineInformation> _baselineInformations = new ArrayList<BaselineInformation>();
	private BaselineInformationPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}
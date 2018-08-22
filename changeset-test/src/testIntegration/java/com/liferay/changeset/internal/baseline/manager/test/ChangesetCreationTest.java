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

package com.liferay.changeset.internal.baseline.manager.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.test.util.BlogsTestUtil;
import com.liferay.changeset.manager.ChangesetBaselineManager;
import com.liferay.changeset.manager.ChangesetManager;
import com.liferay.changeset.model.ChangesetBaselineCollection;
import com.liferay.changeset.model.ChangesetBaselineEntry;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.model.ChangesetEntry;
import com.liferay.changeset.service.ChangesetAwareServiceContext;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@RunWith(Arquillian.class)
public class ChangesetCreationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_serviceContext = new ChangesetAwareServiceContext(
			new ServiceContext());

		_serviceContext.setScopeGroupId(_group.getGroupId());
		_serviceContext.setUserId(TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		_changesetManager.enableChangesets();

		_productionChangsetBaselineCollection =
			_changesetBaselineManager.getProductionBaseline().orElse(null);
	}

	@After
	public void tearDown() throws Exception {
		_changesetManager.disableChangesets();

		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testCreateChangesetWithSupportedEntities() throws Exception {
		long changesetCollectionId = _addChangesetCollection();

		List<ChangesetEntry> changesetEntries =
			_changesetManager.getChangesetEntries(changesetCollectionId);

		Assert.assertEquals(
			"Changes number must be 0", 0, changesetEntries.size());

		CommerceUserSegmentEntry commerceUserSegmentEntry = _addSupportedEntity(
			changesetCollectionId);

		changesetEntries = _changesetManager.getChangesetEntries(
			changesetCollectionId);

		Assert.assertEquals(
			"Changes number must be 1", 1, changesetEntries.size());

		Optional<ChangesetBaselineEntry> baselineEntry =
			_changesetBaselineManager.getBaselineEntry(
				_productionChangsetBaselineCollection.
					getChangesetBaselineCollectionId(),
				CommerceUserSegmentEntry.class.getName(),
				commerceUserSegmentEntry.getPrimaryKey());

		Assert.assertFalse(
			"Baseline entry must be null", baselineEntry.isPresent());
	}

	@Test
	public void testCreateChangesetWithUnsupportedEntities() throws Exception {
		final long changesetCollectionId = _addChangesetCollection();

		List<ChangesetEntry> changesetEntries =
			_changesetManager.getChangesetEntries(changesetCollectionId);

		Assert.assertEquals(
			"Changes number must be 0", 0, changesetEntries.size());

		BlogsEntry blogsEntry = BlogsTestUtil.addEntryWithWorkflow(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), true,
			_serviceContext);

		changesetEntries = _changesetManager.getChangesetEntries(
			changesetCollectionId);

		Assert.assertEquals(
			"Changes number must be 0", 0, changesetEntries.size());

		Optional<ChangesetBaselineEntry> baselineEntry =
			_changesetBaselineManager.getBaselineEntry(
				_productionChangsetBaselineCollection.
					getChangesetBaselineCollectionId(),
				BlogsEntry.class.getName(), blogsEntry.getEntryId());

		Assert.assertFalse(
			"Baseline entry must be null", baselineEntry.isPresent());
	}

	@Test
	public void testCreateEmptyChangeset() {
		final String name = RandomTestUtil.randomString();

		long changesetCollectionId = _addChangesetCollection(name);

		Assert.assertNotEquals(
			"Changeset collection must not be null", changesetCollectionId, 0L);

		List<ChangesetEntry> changesetEntries =
			_changesetManager.getChangesetEntries(changesetCollectionId);

		Assert.assertTrue(
			"Changeset entries must be empty", changesetEntries.isEmpty());

		Optional<ChangesetBaselineCollection> changesetBaseline =
			_changesetBaselineManager.getChangesetBaselineCollection(
				() -> name);

		Assert.assertTrue(
			"Changeset baseline must not be null",
			changesetBaseline.isPresent());

		List<ChangesetBaselineEntry> productionChangesetBaselineEntries =
			_changesetBaselineManager.getChangesetBaselineEntries(
				() -> _productionChangsetBaselineCollection.
					getChangesetBaselineCollectionId());

		List<ChangesetBaselineEntry> changesetBaselineEntries =
			_changesetBaselineManager.getChangesetBaselineEntries(
				() -> changesetBaseline.map(
					ChangesetBaselineCollection::
						getChangesetBaselineCollectionId).get());

		Assert.assertEquals(
			"Baselines must be equal", productionChangesetBaselineEntries,
			changesetBaselineEntries);
	}

	@Test
	public void testCreateParallelChangesetWithSupportedEntities()
		throws Exception {

		long changesetCollectionId1 = _addChangesetCollection();
		long changesetCollectionId2 = _addChangesetCollection();

		List<ChangesetEntry> changesetEntries =
			_changesetManager.getChangesetEntries(changesetCollectionId1);

		Assert.assertEquals(
			"Changes number must be 0", 0, changesetEntries.size());

		changesetEntries = _changesetManager.getChangesetEntries(
			changesetCollectionId2);

		Assert.assertEquals(
			"Changes number must be 0", 0, changesetEntries.size());

		_addSupportedEntity(changesetCollectionId1);

		changesetEntries = _changesetManager.getChangesetEntries(
			changesetCollectionId1);

		Assert.assertEquals(
			"Changes number must be 1", 1, changesetEntries.size());

		changesetEntries = _changesetManager.getChangesetEntries(
			changesetCollectionId2);

		Assert.assertEquals(
			"Changes number must be 0", 0, changesetEntries.size());

		_addSupportedEntity(changesetCollectionId2);

		changesetEntries = _changesetManager.getChangesetEntries(
			changesetCollectionId1);

		Assert.assertEquals(
			"Changes number must be 1", 1, changesetEntries.size());

		changesetEntries = _changesetManager.getChangesetEntries(
			changesetCollectionId2);

		Assert.assertEquals(
			"Changes number must be 1", 1, changesetEntries.size());
	}

	private long _addChangesetCollection() {
		return _addChangesetCollection(RandomTestUtil.randomString());
	}

	private long _addChangesetCollection(String name) {
		final String descrtiption = RandomTestUtil.randomString();

		Optional<ChangesetCollection> changesetCollectionOptional =
			_changesetManager.create(name, descrtiption);

		return changesetCollectionOptional.map(
			ChangesetCollection::getChangesetCollectionId).orElse(0L);
	}

	private CommerceUserSegmentEntry _addSupportedEntity(
			long changesetCollectionId)
		throws PortalException {

		CommerceUserSegmentEntry commerceUserSegmentEntry;

		try {
			_serviceContext.setChangesetCollectionId(changesetCollectionId);

			ServiceContextThreadLocal.pushServiceContext(_serviceContext);

			final Map<Locale, String> nameMap = new HashMap<>();

			nameMap.put(LocaleUtil.HUNGARY, RandomTestUtil.randomString());

			final String key = RandomTestUtil.randomString();

			commerceUserSegmentEntry =
				_commerceUserSegmentEntryLocalService.
					addCommerceUserSegmentEntry(
						nameMap, key, true, false, 1.0D, _serviceContext);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();

			_serviceContext.setChangesetCollectionId(0);
		}

		return commerceUserSegmentEntry;
	}

	@Inject
	private ChangesetBaselineManager _changesetBaselineManager;

	@Inject
	private ChangesetManager _changesetManager;

	@Inject
	private CommerceUserSegmentEntryLocalService
		_commerceUserSegmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private ChangesetBaselineCollection _productionChangsetBaselineCollection;
	private ChangesetAwareServiceContext _serviceContext;

}
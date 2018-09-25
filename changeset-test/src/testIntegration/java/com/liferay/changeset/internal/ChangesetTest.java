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

package com.liferay.changeset.internal;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.changeset.manager.ChangesetBaselineManager;
import com.liferay.changeset.manager.ChangesetManager;
import com.liferay.changeset.model.ChangesetBaselineCollection;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.model.ChangesetEntry;
import com.liferay.changeset.service.ChangesetAwareServiceContext;
import com.liferay.changeset.service.ChangesetEntryLocalService;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentCriterionLocalService;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalService;
import com.liferay.commerce.user.segment.service.persistence.CommerceUserSegmentCriterionPersistence;
import com.liferay.commerce.user.segment.service.persistence.CommerceUserSegmentEntryPersistence;
import com.liferay.commerce.user.segment.service.persistence.CommerceUserSegmentEntryVersionPersistence;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mate Thurzo
 */
@RunWith(Arquillian.class)
public class ChangesetTest {

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
	}

	@After
	public void tearDown() {
		_changesetManager.disableChangesets();

		try {
			_commerceUserSegmentEntryLocalService.
				deleteCommerceUserSegmentEntries(_group.getGroupId());
		}
		catch (PortalException pe) {
		}
	}

	@Test
	public void criticalPath() throws Exception {

		// Enable changesets

		_changesetManager.enableChangesets();

		Assert.assertTrue(
			"Changeset support for commerce user segment entries are needed",
			_changesetManager.isChangesetSupported(
				CommerceUserSegmentEntry.class));

		// Check production baseline

		Optional<ChangesetBaselineCollection> productionBaselineOptional =
			_changesetBaselineManager.getProductionBaseline();

		Assert.assertTrue(
			"Production baseline is not created",
			productionBaselineOptional.isPresent());

		// Add a new changeset

		Optional<ChangesetCollection> changesetCollectionOptional =
			_changesetManager.create("CHANGESET-1", "Changeset 1 description");

		Assert.assertTrue(
			"Changeset collection optional should not be empty",
			changesetCollectionOptional.isPresent());

		// Check changeset baseline

		Optional<ChangesetBaselineCollection> changesetBaselineOptional =
			_changesetBaselineManager.getChangesetBaselineCollection(
				() -> "CHANGESET-1");

		Assert.assertTrue(
			"Created baseline is not present",
			changesetBaselineOptional.isPresent());

		// Create user segment entry

		ChangesetAwareServiceContext changesetAwareServiceContext =
			new ChangesetAwareServiceContext(_serviceContext);

		changesetAwareServiceContext.setChangesetCollectionId(
			changesetCollectionOptional.get().getChangesetCollectionId());

		ServiceContextThreadLocal.pushServiceContext(
			changesetAwareServiceContext);

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(Locale.US, "User Segment");

		CommerceUserSegmentEntry segmentEntry =
			_commerceUserSegmentEntryLocalService.addCommerceUserSegmentEntry(
				nameMap, "USERSEGMENT", true, false, 1.0d,
				_serviceContext);

//		_commerceUserSegmentCriterionLocalService.
//			addCommerceUserSegmentCriterion(
//				segmentEntry.getCommerceUserSegmentEntryId(), "user",
//				StringPool.BLANK, 1.0, _serviceContext);

		// Check changeset content

		long changesetCollectionId =
			changesetCollectionOptional.get().getChangesetCollectionId();

		List<ChangesetEntry> changesetEntries =
			_changesetManager.getChangesetEntries(changesetCollectionId);

		Assert.assertFalse(
			"Changeset entries should not be empty",
			changesetEntries.isEmpty());

		long changesetEntriesCount =
			_changesetEntryLocalService.getChangesetEntriesCount(
				changesetCollectionId);

		Assert.assertEquals(
			"There should be only 1 changeset entry", 1, changesetEntriesCount);

		// Read segment entry from local service - should return changeset one

		segmentEntry =
			_commerceUserSegmentEntryLocalService.fetchCommerceUserSegmentEntry(
				segmentEntry.getGroupId(), segmentEntry.getKey());

		Assert.assertNotNull("Segment entry should not be null", segmentEntry);

		// Read segment entry from local service - should return production one

		changesetAwareServiceContext =
			(ChangesetAwareServiceContext)
				ServiceContextThreadLocal.popServiceContext();

		changesetAwareServiceContext.setChangesetCollectionId(0);

		ServiceContextThreadLocal.pushServiceContext(
			changesetAwareServiceContext);

		segmentEntry =
			_commerceUserSegmentEntryLocalService.fetchCommerceUserSegmentEntry(
				segmentEntry.getGroupId(), segmentEntry.getKey());

		Assert.assertNull(
			"Production segment entry should be null", segmentEntry);
	}

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

	@Inject
	private ChangesetBaselineManager _changesetBaselineManager;

	@Inject
	private ChangesetEntryLocalService _changesetEntryLocalService;

	@Inject
	private ChangesetManager _changesetManager;

	@Inject
	private CommerceUserSegmentCriterionLocalService
		_commerceUserSegmentCriterionLocalService;

	@Inject
	private CommerceUserSegmentEntryLocalService
		_commerceUserSegmentEntryLocalService;

	@Inject
	private CommerceUserSegmentEntryPersistence
		_commerceUserSegmentEntryPersistence;

	@Inject
	private CommerceUserSegmentEntryVersionPersistence
		_commerceUserSegmentEntryVersionPersistence;

	@Inject
	private CommerceUserSegmentCriterionPersistence
		_commerceUserSegmentCriterionPersistence;

}
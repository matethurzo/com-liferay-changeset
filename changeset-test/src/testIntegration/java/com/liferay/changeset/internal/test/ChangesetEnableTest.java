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

package com.liferay.changeset.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.test.util.BlogsTestUtil;
import com.liferay.changeset.constants.ChangesetConstants;
import com.liferay.changeset.manager.ChangesetBaselineManager;
import com.liferay.changeset.manager.ChangesetManager;
import com.liferay.changeset.model.ChangesetBaselineCollection;
import com.liferay.changeset.model.ChangesetBaselineEntry;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntryVersion;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalService;
import com.liferay.commerce.user.segment.service.persistence.CommerceUserSegmentEntryPersistence;
import com.liferay.commerce.user.segment.service.persistence.CommerceUserSegmentEntryVersionPersistence;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

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
 * @author Mate Thurzo
 */
@RunWith(Arquillian.class)
public class ChangesetEnableTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_serviceContext = new ServiceContext();

		_serviceContext.setScopeGroupId(_group.getGroupId());
		_serviceContext.setUserId(TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() {
		_changesetManager.disableChangesets();

		try {
			_blogsEntryLocalService.deleteEntry(_blogsEntry.getEntryId());
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testEnableChangeset() {

		// Clean up entries for changeset enabling

		_commerceUserSegmentEntryPersistence.removeAll();
		_commerceUserSegmentEntryVersionPersistence.removeAll();

		_changesetManager.enableChangesets();

		Optional<ChangesetBaselineCollection> productionBaselineOptional =
			_changesetBaselineManager.getProductionBaseline();

		Assert.assertTrue(
			"Production baseline is not created",
			productionBaselineOptional.isPresent());

		List<ChangesetBaselineEntry> baselineEntries =
			_changesetBaselineManager.getBaselineEntries(
				() -> ChangesetConstants.PRODUCTION_BASELINE_NAME);

		Assert.assertTrue(
			"Production baseline is not empty", baselineEntries.isEmpty());
	}

	@Test
	public void testEnableChangesetWithData() throws Exception {

		// Create data first for baseline

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.HUNGARY, RandomTestUtil.randomString());

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			_commerceUserSegmentEntryLocalService.addCommerceUserSegmentEntry(
				nameMap, RandomTestUtil.randomString(), true, false, 1.0D,
				_serviceContext);

		_changesetManager.enableChangesets();

		Optional<ChangesetBaselineCollection> productionBaselineOptional =
			_changesetBaselineManager.getProductionBaseline();

		Assert.assertTrue(
			"Production baseline is not created",
			productionBaselineOptional.isPresent());

		List<ChangesetBaselineEntry> baselineEntries =
			_changesetBaselineManager.getBaselineEntries(
				() -> ChangesetConstants.PRODUCTION_BASELINE_NAME);

		long classNameId = _portal.getClassNameId(
			CommerceUserSegmentEntryVersion.class.getName());

		CommerceUserSegmentEntryVersion commerceUserSegmentEntryVersion =
			_commerceUserSegmentEntryLocalService.getLatestVersion(
				commerceUserSegmentEntry);

		boolean found = false;

		for (ChangesetBaselineEntry baselineEntry : baselineEntries) {
			if ((baselineEntry.getClassNameId() == classNameId) &&
				(baselineEntry.getClassPK() ==
					commerceUserSegmentEntryVersion.
						getCommerceUserSegmentEntryVersionId())) {

				found = true;

				break;
			}
		}

		if (!found) {
			Assert.fail("Entry version cannot be found in the baseline");
		}
	}

	@Test
	public void testEnableChangesetWithNotSupportedData() throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(TestPropsValues.getGroupId());

		_blogsEntry = BlogsTestUtil.addEntryWithWorkflow(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), true,
			serviceContext);

		_changesetManager.enableChangesets();

		Optional<ChangesetBaselineCollection> productionBaselineOptional =
			_changesetBaselineManager.getProductionBaseline();

		Assert.assertTrue(
			"Production baseline is not created",
			productionBaselineOptional.isPresent());

		List<ChangesetBaselineEntry> baselineEntries =
			_changesetBaselineManager.getBaselineEntries(
				() -> ChangesetConstants.PRODUCTION_BASELINE_NAME);

		long blogsEntryClassNameid = _portal.getClassNameId(
			BlogsEntry.class.getName());

		for (ChangesetBaselineEntry baselineEntry : baselineEntries) {
			if (baselineEntry.getClassNameId() == blogsEntryClassNameid) {
				Assert.fail(
					"Blogs is not supported, should not be in the baseline");
			}
		}
	}

	private BlogsEntry _blogsEntry;

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Inject
	private ChangesetBaselineManager _changesetBaselineManager;

	@Inject
	private ChangesetManager _changesetManager;

	@Inject
	private CommerceUserSegmentEntryLocalService
		_commerceUserSegmentEntryLocalService;

	@Inject
	private CommerceUserSegmentEntryPersistence
		_commerceUserSegmentEntryPersistence;

	@Inject
	private CommerceUserSegmentEntryVersionPersistence
		_commerceUserSegmentEntryVersionPersistence;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

}
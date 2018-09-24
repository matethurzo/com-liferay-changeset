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
import com.liferay.changeset.manager.ChangesetBaselineManager;
import com.liferay.changeset.model.ChangesetBaselineCollection;
import com.liferay.changeset.model.ChangesetBaselineEntry;
import com.liferay.changeset.service.ChangesetAwareServiceContext;
import com.liferay.changeset.service.ChangesetBaselineCollectionLocalService;
import com.liferay.changeset.service.ChangesetBaselineEntryLocalService;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

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
public class ChangesetBaselineManagerTest {

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

		_serviceContext = new ChangesetAwareServiceContext(
			new ServiceContext());

		_serviceContext.setScopeGroupId(_group.getGroupId());
		_serviceContext.setUserId(TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() throws Exception {
		List<ChangesetBaselineCollection> changesetBaselineCollections =
			_changesetBaselineCollectionLocalService.
				getChangesetBaselineCollections(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (ChangesetBaselineCollection changesetBaselineCollection :
				changesetBaselineCollections) {

			_changesetBaselineCollectionLocalService.
				deleteChangesetBaselineCollection(
					changesetBaselineCollection.
						getChangesetBaselineCollectionId());
		}

		List<ChangesetBaselineEntry> baselineEntries =
			_changesetBaselineEntryLocalService.getChangesetBaselineEntries(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (ChangesetBaselineEntry baselineEntry : baselineEntries) {
			_changesetBaselineEntryLocalService.deleteChangesetBaselineEntry(
				baselineEntry.getChangesetBaselineEntryId());
		}
	}

	@Test
	public void testCreateBaseline() throws Exception {
		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.HUNGARY, RandomTestUtil.randomString());

		_commerceUserSegmentEntryLocalService.addCommerceUserSegmentEntry(
			nameMap, RandomTestUtil.randomString(), true, false, 1.0D,
			_serviceContext);

		Supplier<? extends Serializable> baselineIdSupplier =
			() -> "Test baseline";

		_changesetBaselineManager.createBaseline(baselineIdSupplier);

		Optional<ChangesetBaselineCollection> baselineInformation =
			_changesetBaselineManager.getChangesetBaselineCollection(
				baselineIdSupplier);

		Assert.assertTrue(
			"Baseline not found", baselineInformation.isPresent());

		Assert.assertEquals(
			"Baseline name does not match",
			String.valueOf(baselineIdSupplier.get()),
			baselineInformation.get().getName());

		int entriesCount =
			_commerceUserSegmentEntryLocalService.
				getCommerceUserSegmentEntriesCount();

		int baselineEntriesCount =
			_changesetBaselineEntryLocalService.
				getChangesetBaselineEntriesCount();

		Assert.assertEquals(
			"Baseline contains different number of entries",
			entriesCount, baselineEntriesCount);
	}

	@Inject
	private ChangesetBaselineCollectionLocalService
		_changesetBaselineCollectionLocalService;

	@Inject
	private ChangesetBaselineEntryLocalService
		_changesetBaselineEntryLocalService;

	@Inject
	private ChangesetBaselineManager _changesetBaselineManager;

	@Inject
	private CommerceUserSegmentEntryLocalService
		_commerceUserSegmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private ChangesetAwareServiceContext _serviceContext;

}
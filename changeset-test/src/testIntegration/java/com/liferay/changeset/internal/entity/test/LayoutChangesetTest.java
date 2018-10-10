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

package com.liferay.changeset.internal.entity.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.changeset.constants.ChangesetConstants;
import com.liferay.changeset.manager.ChangesetBaselineManager;
import com.liferay.changeset.manager.ChangesetManager;
import com.liferay.changeset.model.ChangesetBaselineCollection;
import com.liferay.changeset.model.ChangesetBaselineEntry;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.model.ChangesetEntry;
import com.liferay.changeset.service.ChangesetBaselineEntryLocalService;
import com.liferay.changeset.service.ChangesetEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gergely Mathe
 */
@RunWith(Arquillian.class)
public class LayoutChangesetTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

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
			_layoutLocalService.deleteLayouts(
				_group.getGroupId(), true, _serviceContext);
			_layoutLocalService.deleteLayouts(
				_group.getGroupId(), false, _serviceContext);
		}
		catch (PortalException pe) {
		}
	}

	@Test
	public void testCriticalPath() throws Exception {

		// Enable changesets

		_changesetManager.enableChangesets();

		Assert.assertTrue(
			"Changeset support for layout is needed",
			_changesetManager.isChangesetSupported(Layout.class));

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

		// Create and update layout

		long changesetCollectionId =
			changesetCollectionOptional.get().getChangesetCollectionId();

		_changesetManager.checkout(changesetCollectionId);

		_serviceContext = ServiceContextThreadLocal.getServiceContext();

		Layout layout = _layoutLocalService.addLayout(
			_serviceContext.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "testLayout",
			"Test Layout", "Test Layout Description",
			LayoutConstants.TYPE_PORTLET, false, "/testlayout",
			_serviceContext);

		_layoutLocalService.updateName(
			layout, "testLayout2", LanguageUtil.getLanguageId(Locale.US));

		// Check changeset content

		List<ChangesetEntry> changesetEntries =
			_changesetManager.getChangesetEntries(changesetCollectionId);

		Assert.assertFalse(
			"Changeset entries should not be empty",
			changesetEntries.isEmpty());

		long changesetEntriesCount =
			_changesetEntryLocalService.getChangesetEntriesCount(
				changesetCollectionId);

		Assert.assertEquals(
			"There should be only 2 changeset entry", 2, changesetEntriesCount);

		// Read layout from local service - should return changeset one

		Layout modifiedLayout = _layoutLocalService.fetchLayout(
			layout.getPlid());

		Assert.assertNotNull("Layout should not be null", modifiedLayout);

		// Read layout from local service - should return production one

		_changesetManager.checkout(
			ChangesetConstants.PRODUCTION_BASELINE_COLLECTION_ID);

		Layout productionLayout = _layoutLocalService.fetchLayout(
			layout.getPlid());

		Assert.assertNull("Production layout should be null", productionLayout);

		_changesetManager.publish(changesetCollectionId);

		productionLayout = _layoutLocalService.fetchLayout(layout.getPlid());

		Assert.assertNotNull(
			"Production layout should exist", productionLayout);

		long productionBaselineCollectionId =
			productionBaselineOptional.get().getChangesetBaselineCollectionId();

		ChangesetBaselineEntry productionBaselineEntry =
			_changesetBaselineEntryLocalService.getChangesetBaselineEntry(
				productionBaselineCollectionId,
				_portal.getClassNameId(Layout.class.getName()),
				// TODO Replace this with LayoutVersion.class.getName()
				0L); // TODO Replace this with productionLayout.getVersionId()

		Assert.assertNotNull(
			"Production baseline entry was not created",
			productionBaselineEntry);
	}

	@Inject
	private ChangesetBaselineEntryLocalService
		_changesetBaselineEntryLocalService;

	@Inject
	private ChangesetBaselineManager _changesetBaselineManager;

	@Inject
	private ChangesetEntryLocalService _changesetEntryLocalService;

	@Inject
	private ChangesetManager _changesetManager;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

}
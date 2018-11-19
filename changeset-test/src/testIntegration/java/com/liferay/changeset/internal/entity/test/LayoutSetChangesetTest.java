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
import com.liferay.changeset.cqrs.manager.ChangesetCQRSManager;
import com.liferay.changeset.manager.ChangesetBaselineManager;
import com.liferay.changeset.manager.ChangesetManager;
import com.liferay.changeset.model.ChangesetBaselineCollection;
import com.liferay.changeset.model.ChangesetBaselineEntry;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.model.ChangesetEntry;
import com.liferay.changeset.service.ChangesetBaselineEntryLocalService;
import com.liferay.changeset.service.ChangesetEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.*;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
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
public class LayoutSetChangesetTest {

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
	}

	@After
	public void tearDown() {
		_changesetManager.disableChangesets();

		try {
			List<LayoutSet> layoutSets = _layoutSetLocalService.getLayoutSets(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (LayoutSet layoutSet : layoutSets) {
				_layoutSetLocalService.purgeLayoutSet(
					_group.getGroupId(), layoutSet.isPrivateLayout(),
					_serviceContext);
			}

			VirtualHost virtualHost = _virtualHostLocalService.fetchVirtualHost(
				"http://www.mydomain.com");

			if (virtualHost != null) {
				_virtualHostLocalService.deleteVirtualHost(virtualHost);
			}
		}
		catch (PortalException pe) {
		}
	}

	@Test
	public void testCriticalPathHybrid() throws Exception {
		_criticalPath("HYBRID");
	}

	@Test
	public void testCriticalPathIndex() throws Exception {
		_criticalPath("CQRS");
	}

	private void _criticalPath(String type) throws Exception {
		_serviceContext.setAttribute("repository-type", type);

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		// Enable changesets

		_changesetManager.enableChangesets();

		Assert.assertTrue(
			"Changeset support for layout set version is needed",
			_changesetManager.isChangesetSupported(LayoutVersion.class));

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

		// Create and update layout set

		long changesetCollectionId =
			changesetCollectionOptional.get().getChangesetCollectionId();

		_changesetManager.checkout(changesetCollectionId);

		_serviceContext = ServiceContextThreadLocal.getServiceContext();

		_serviceContext.setAttribute("cqrs-repository-enabled", Boolean.FALSE);

		_changesetCQRSManager.disableCQRSRepository();

		LayoutSet layoutSet;

		try {
			_serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

			layoutSet = _layoutSetLocalService.addLayoutSet(
				_group.getGroupId(), false);

			layoutSet = _layoutSetLocalService.updateVirtualHost(
				_group.getGroupId(), false, "http://www.mydomain.com");
		}
		finally {
			_serviceContext.setAttribute(
				"cqrs-repository-enabled", Boolean.TRUE);
			_changesetCQRSManager.enableCQRSRepository();
		}

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

		// Read layout set from local service - should return changeset one

		LayoutSet modifiedLayoutSet = _layoutSetLocalService.fetchLayoutSet(
			layoutSet.getLayoutSetId());

		Assert.assertNotNull(
			"Layout seg should not be null", modifiedLayoutSet);

		// Read layout set from local service - should return production one

		_changesetManager.checkout(
			ChangesetConstants.PRODUCTION_BASELINE_COLLECTION_ID);

		LayoutSet productionLayoutSet = _layoutSetLocalService.fetchLayoutSet(
			layoutSet.getLayoutSetId());

		Assert.assertNull(
			"Production layout set should be null", productionLayoutSet);

		_changesetManager.publish(changesetCollectionId);

		productionLayoutSet = _layoutSetLocalService.fetchLayoutSet(
			layoutSet.getLayoutSetId());

		Assert.assertNotNull(
			"Production layout set should exist", productionLayoutSet);

		long productionBaselineCollectionId =
			productionBaselineOptional.get().getChangesetBaselineCollectionId();

		ChangesetBaselineEntry productionBaselineEntry =
			_changesetBaselineEntryLocalService.getChangesetBaselineEntry(
				productionBaselineCollectionId,
				_portal.getClassNameId(LayoutSetVersion.class.getName()),
				productionLayoutSet.getVersionId());

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
	private ChangesetCQRSManager _changesetCQRSManager;

	@Inject
	private ChangesetEntryLocalService _changesetEntryLocalService;

	@Inject
	private ChangesetManager _changesetManager;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

	@Inject
	private VirtualHostLocalService _virtualHostLocalService;

}

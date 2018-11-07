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
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.model.BlogsEntryVersion;
import com.liferay.blogs.service.BlogsEntryLocalService;
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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
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
public class BlogsChangesetTest {

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

		_blogsEntryLocalService.purgeBlogsEntries(_group.getGroupId());

		try {
			_assetTagLocalService.deleteTag(_assetTag);
		}
		catch (Exception e) {
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
			"Changeset support for blogs entry version is needed",
			_changesetManager.isChangesetSupported(BlogsEntryVersion.class));

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

		// Create and update blogs entry

		long changesetCollectionId =
			changesetCollectionOptional.get().getChangesetCollectionId();

		_changesetManager.checkout(changesetCollectionId);

		_serviceContext = ServiceContextThreadLocal.getServiceContext();

		_serviceContext.setAttribute("cqrs-repository-enabled", Boolean.FALSE);

		_changesetCQRSManager.disableCQRSRepository();

		BlogsEntry blogsEntry;

		try {
			_serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

			blogsEntry = _blogsEntryLocalService.addEntry(
				_serviceContext.getUserId(), "Test Blogs Entry",
				"Test Blogs Entry Content", _serviceContext);

			// Add extra asset tag

			_assetTag = _assetTagLocalService.addTag(
				_serviceContext.getUserId(), _group.getGroupId(), "tag 1",
				_serviceContext);

			_serviceContext.setAssetTagNames(
				new String[] {_assetTag.getName()});

			blogsEntry = _blogsEntryLocalService.updateEntry(
				_serviceContext.getUserId(), blogsEntry.getEntryId(),
				"Test Blogs Entry Modified",
				"Test Blogs Entry Modified Content", _serviceContext);
		}
		finally {
			_serviceContext.setAttribute(
				"cqrs-repository-enabled", Boolean.TRUE);
			_changesetCQRSManager.enableCQRSRepository();
		}

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			BlogsEntryVersion.class.getName(), blogsEntry.getVersionId());

		List<AssetTag> tags = assetEntry.getTags();

		Assert.assertNotNull("Tags should not be null", tags);
		Assert.assertEquals("Tags should contain at least one", 1, tags.size());

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

		// Read blogs entry from local service - should return changeset one

		BlogsEntry modifiedBlogsEntry = _blogsEntryLocalService.fetchBlogsEntry(
			blogsEntry.getEntryId());

		Assert.assertNotNull(
			"Blogs entry should not be null", modifiedBlogsEntry);

		// Read blogs entry from local service - should return production one

		_changesetManager.checkout(
			ChangesetConstants.PRODUCTION_BASELINE_COLLECTION_ID);

		BlogsEntry productionBlogsEntry =
			_blogsEntryLocalService.fetchBlogsEntry(blogsEntry.getEntryId());

		Assert.assertNull(
			"Production blogs entry should be null", productionBlogsEntry);

		_changesetManager.publish(changesetCollectionId);

		productionBlogsEntry = _blogsEntryLocalService.fetchBlogsEntry(
			blogsEntry.getEntryId());

		Assert.assertNotNull(
			"Production blogs entry should exist", productionBlogsEntry);

		assetEntry = _assetEntryLocalService.fetchEntry(
			BlogsEntryVersion.class.getName(),
			productionBlogsEntry.getVersionId());

		tags = assetEntry.getTags();

		Assert.assertNotNull("Production tags should not be null", tags);

		Assert.assertTrue(
			"Production tags should contain at least 1 tag", tags.size() == 1);

		long productionBaselineCollectionId =
			productionBaselineOptional.get().getChangesetBaselineCollectionId();

		ChangesetBaselineEntry productionBaselineEntry =
			_changesetBaselineEntryLocalService.getChangesetBaselineEntry(
				productionBaselineCollectionId,
				_portal.getClassNameId(BlogsEntryVersion.class.getName()),
				productionBlogsEntry.getVersionId());

		Assert.assertNotNull(
			"Production baseline entry was not created",
			productionBaselineEntry);
	}

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	private AssetTag _assetTag;

	@Inject
	private AssetTagLocalService _assetTagLocalService;

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

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
	private Portal _portal;

	private ServiceContext _serviceContext;

}
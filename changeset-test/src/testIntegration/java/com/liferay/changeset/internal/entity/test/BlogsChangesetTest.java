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
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
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
import com.liferay.portal.kernel.model.Group;
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

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() {
		_changesetManager.disableChangesets();

		try {
			_blogsEntryLocalService.deleteEntries(_group.getGroupId());
		}
		catch (PortalException pe) {
		}
	}

	@Test
	public void testCriticalPath() throws Exception {

		// Enable changesets

		_changesetManager.enableChangesets();

		Assert.assertTrue(
			"Changeset support for blogs entry is needed",
			_changesetManager.isChangesetSupported(BlogsEntry.class));

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

		BlogsEntry blogsEntry = _blogsEntryLocalService.addEntry(
			_serviceContext.getUserId(), "Test Blogs Entry",
			"Test Blogs Entry Content", _serviceContext);

		_blogsEntryLocalService.updateEntry(
			_serviceContext.getUserId(), blogsEntry.getEntryId(),
			"Test Blogs Entry Modified", "Test Blogs Entry Modified Content",
			_serviceContext);

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

		long productionBaselineCollectionId =
			productionBaselineOptional.get().getChangesetBaselineCollectionId();

		ChangesetBaselineEntry productionBaselineEntry =
			_changesetBaselineEntryLocalService.getChangesetBaselineEntry(
				productionBaselineCollectionId,
				_portal.getClassNameId(BlogsEntry.class.getName()), // TODO Replace this with BlogsEntryVersion.class.getName()
				0L); // TODO Replace this with productionBlogsEntry.getVersionId()

		Assert.assertNotNull(
			"Production baseline entry was not created",
			productionBaselineEntry);
	}

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

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
	private Portal _portal;

	private ServiceContext _serviceContext;

}
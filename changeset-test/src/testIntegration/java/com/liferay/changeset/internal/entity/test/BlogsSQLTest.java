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
import com.liferay.blogs.service.persistence.BlogsEntryFinder;
import com.liferay.changeset.cqrs.manager.ChangesetCQRSManager;
import com.liferay.changeset.manager.ChangesetManager;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
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
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Gergely Mathe
 */
@RunWith(Arquillian.class)
public class BlogsSQLTest {

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

		_blogsEntryLocalService.purgeBlogsEntries(_group.getGroupId());
	}

	@Test
	public void testFindByEntryId() throws PortalException {
		_changesetManager.enableChangesets();

		Optional<ChangesetCollection> changesetCollectionOptional =
			_changesetManager.create(
				"testFindByEntryId", "testFindByEntryId description");

		long changesetCollectionId =
			changesetCollectionOptional.get().getChangesetCollectionId();

		_changesetManager.checkout(changesetCollectionId);

		_serviceContext = ServiceContextThreadLocal.getServiceContext();

		_serviceContext.setAttribute("cqrs-repository-enabled", Boolean.FALSE);
		_changesetCQRSManager.disableCQRSRepository();

		BlogsEntry blogsEntry = _blogsEntryLocalService.addEntry(
			_serviceContext.getUserId(), "Test Blogs Entry",
			"Test Blogs Entry Content", _serviceContext);

		blogsEntry = _blogsEntryLocalService.updateEntry(
			_serviceContext.getUserId(), blogsEntry.getEntryId(),
			"Test Blogs Entry Modified", "Test Blogs Entry Modified Content",
			_serviceContext);

		_serviceContext.setAttribute("cqrs-repository-enabled", Boolean.TRUE);
		_changesetCQRSManager.enableCQRSRepository();

		BlogsEntry modifiedBlogsEntry = _blogsEntryLocalService.fetchBlogsEntry(
			blogsEntry.getEntryId());

		Assert.assertEquals(
			modifiedBlogsEntry.getEntryId(), blogsEntry.getEntryId());

		Assert.assertEquals(
			modifiedBlogsEntry.getVersionId(), blogsEntry.getVersionId());
	}

	@Test
	public void testFindByGroupIds() throws PortalException {
		_changesetManager.enableChangesets();

		Optional<ChangesetCollection> changesetCollectionOptional =
			_changesetManager.create(
				"testFindByGroupIds", "testFindByGroupIds description");

		long changesetCollectionId =
			changesetCollectionOptional.get().getChangesetCollectionId();

		_changesetManager.checkout(changesetCollectionId);

		_serviceContext = ServiceContextThreadLocal.getServiceContext();

		_serviceContext.setAttribute("cqrs-repository-enabled", Boolean.FALSE);
		_changesetCQRSManager.disableCQRSRepository();

		BlogsEntry blogsEntry = _blogsEntryLocalService.addEntry(
			_serviceContext.getUserId(), "Test Blogs Entry",
			"Test Blogs Entry Content", _serviceContext);

		blogsEntry = _blogsEntryLocalService.updateEntry(
			_serviceContext.getUserId(), blogsEntry.getEntryId(),
			"Test Blogs Entry Modified", "Test Blogs Entry Modified Content",
			_serviceContext);

		_serviceContext.setAttribute("cqrs-repository-enabled", Boolean.TRUE);
		_changesetCQRSManager.enableCQRSRepository();

		QueryDefinition<BlogsEntry> queryDefinition = new QueryDefinition<>();

		List<BlogsEntry> blogsEntries = _blogsEntryFinder.findByGroupIds(
			_serviceContext.getCompanyId(), _serviceContext.getScopeGroupId(),
			new Date(), queryDefinition);

		Assert.assertEquals(1, blogsEntries.size());

		BlogsEntry modifiedBlogsEntry = blogsEntries.get(0);

		Assert.assertEquals(
			modifiedBlogsEntry.getEntryId(), blogsEntry.getEntryId());

		Assert.assertEquals(
			modifiedBlogsEntry.getVersionId(), blogsEntry.getVersionId());
	}

	@Inject
	private BlogsEntryFinder _blogsEntryFinder;

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Inject
	private ChangesetCQRSManager _changesetCQRSManager;

	@Inject
	private ChangesetManager _changesetManager;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

}

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
import com.liferay.blogs.test.util.BlogsTestUtil;
import com.liferay.changeset.manager.ChangesetBaselineManager;
import com.liferay.changeset.manager.ChangesetManager;
import com.liferay.changeset.model.ChangesetBaselineCollection;
import com.liferay.changeset.model.ChangesetBaselineEntry;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntryVersion;
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
import java.util.Locale;
import java.util.Map;

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
public class ChangesetPublishTest {

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

		_changesetManager.enableChangesets();

		_changesetManager.create(
			"Test", "Test Changeset Collection"
		).map(
			changesetCollection -> _changesetCollection = changesetCollection
		);
	}

	@After
	public void tearDown() {
		_changesetManager.disableChangesets();

		ServiceContextThreadLocal.popServiceContext();

		// TODO Clean up all entities that this test created

	}

	@Test
	public void testPublishChangesetWithSupportedEntity() throws Exception {
		CommerceUserSegmentEntry commerceUserSegmentEntry = _addSupportedEntity(
			_changesetCollection.getChangesetCollectionId());

		CommerceUserSegmentEntryVersion commerceUserSegmentEntryVersion =
			_commerceUserSegmentEntryLocalService.getLatestVersion(
				commerceUserSegmentEntry);

		long productionBaseLineCollectionId =
			_changesetBaselineManager.getProductionBaseline(
			).map(
				ChangesetBaselineCollection::getChangesetBaselineCollectionId
			).orElse(
				0L
			);

		ChangesetBaselineEntry productionChangesetBaselineEntry =
			_changesetBaselineManager.getBaselineEntry(
				productionBaseLineCollectionId,
				commerceUserSegmentEntryVersion.getModelClassName(),
				commerceUserSegmentEntryVersion.
					getCommerceUserSegmentEntryVersionId()
			).orElse(
				null
			);

		Assert.assertNull(
			"Production baseline entry already exists before publish",
			productionChangesetBaselineEntry);

		_changesetManager.publish(
			_changesetCollection.getChangesetCollectionId());

		productionChangesetBaselineEntry =
			_changesetBaselineManager.getBaselineEntry(
				productionBaseLineCollectionId,
				commerceUserSegmentEntryVersion.getModelClassName(),
				commerceUserSegmentEntryVersion.
					getCommerceUserSegmentEntryVersionId()
			).orElse(
				null
			);

		Assert.assertNotNull(
			"Production baseline entry does not exist after publish",
			productionChangesetBaselineEntry);
	}

	@Test
	public void testPublishChangesetWithUnsupportedEntity() throws Exception {
		BlogsEntry blogsEntry = BlogsTestUtil.addEntryWithWorkflow(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), true,
			_serviceContext);

		long productionBaseLineCollectionId =
			_changesetBaselineManager.getProductionBaseline(
			).map(
				ChangesetBaselineCollection::getChangesetBaselineCollectionId
			).orElse(
				0L
			);

		ChangesetBaselineEntry productionChangesetBaselineEntry =
			_changesetBaselineManager.getBaselineEntry(
				productionBaseLineCollectionId, blogsEntry.getModelClassName(),
				blogsEntry.getEntryId()
			).orElse(
				null
			);

		Assert.assertNull(
			"Production baseline entry exists for unsupported entity",
			productionChangesetBaselineEntry);

		_changesetManager.publish(
			_changesetCollection.getChangesetCollectionId());

		productionChangesetBaselineEntry =
			_changesetBaselineManager.getBaselineEntry(
				productionBaseLineCollectionId, blogsEntry.getModelClassName(),
				blogsEntry.getEntryId()
			).orElse(
				null
			);

		Assert.assertNull(
			"Production baseline entry exists for unsupported entity",
			productionChangesetBaselineEntry);
	}

	private CommerceUserSegmentEntry _addSupportedEntity(
			long changesetCollectionId)
		throws PortalException {

		CommerceUserSegmentEntry commerceUserSegmentEntry;

		try {
			_changesetManager.checkout(changesetCollectionId);

			_serviceContext = ServiceContextThreadLocal.getServiceContext();

			final Map<Locale, String> nameMap = new HashMap<>();

			nameMap.put(LocaleUtil.HUNGARY, RandomTestUtil.randomString());

			final String key = RandomTestUtil.randomString();

			commerceUserSegmentEntry =
				_commerceUserSegmentEntryLocalService.
					addCommerceUserSegmentEntry(
						nameMap, key, true, false, 1.0D, _serviceContext);
		}
		finally {
			_changesetManager.checkout(0L);

			_serviceContext = ServiceContextThreadLocal.popServiceContext();
		}

		return commerceUserSegmentEntry;
	}

	@Inject
	private ChangesetBaselineManager _changesetBaselineManager;

	private ChangesetCollection _changesetCollection;

	@Inject
	private ChangesetManager _changesetManager;

	@Inject
	private CommerceUserSegmentEntryLocalService
		_commerceUserSegmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

}
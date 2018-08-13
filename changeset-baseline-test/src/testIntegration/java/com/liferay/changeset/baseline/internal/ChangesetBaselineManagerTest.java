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

package com.liferay.changeset.baseline.internal;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.blogs.test.util.BlogsTestUtil;
import com.liferay.changeset.baseline.manager.ChangesetBaselineManager;
import com.liferay.changeset.baseline.manager.ChangesetBaselineManagerUtil;
import com.liferay.changeset.baseline.model.BaselineEntry;
import com.liferay.changeset.baseline.model.BaselineInformation;
import com.liferay.changeset.baseline.service.BaselineEntryLocalServiceUtil;
import com.liferay.changeset.baseline.service.BaselineInformationLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.List;
import java.util.function.Supplier;

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
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		List<BaselineInformation> baselineInformations =
			BaselineInformationLocalServiceUtil.getBaselineInformations(-1, -1);

		for (BaselineInformation baselineInformation : baselineInformations) {
			BaselineInformationLocalServiceUtil.deleteBaselineInformation(
				baselineInformation.getBaselineInformationId());
		}

		List<BaselineEntry> baselineEntries =
			BaselineEntryLocalServiceUtil.getBaselineEntries(-1, -1);

		for (BaselineEntry baselineEntry : baselineEntries) {
			BaselineEntryLocalServiceUtil.deleteBaselineEntry(
				baselineEntry.getBaselineEntryId());
		}
	}

	@Test
	public void testCreateBaseline() throws Exception {
		User user = UserLocalServiceUtil.getDefaultUser(
			CompanyThreadLocal.getCompanyId());

		Group group = GroupLocalServiceUtil.getGroup(
			CompanyThreadLocal.getCompanyId(), GroupConstants.GUEST);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(group.getGroupId());

		BlogsEntry blogsEntry = BlogsTestUtil.addEntryWithWorkflow(
			user.getUserId(), RandomTestUtil.randomString(), true,
			serviceContext);

		Supplier<? extends Serializable> baselineIdSupplier =
			() -> "Test baseline";

		ChangesetBaselineManager changesetBaselineManager =
			ChangesetBaselineManagerUtil.getChangesetManager();

		changesetBaselineManager.createBaseline(baselineIdSupplier);

		BaselineInformation baselineInformation =
			changesetBaselineManager.getBaselineInformation(baselineIdSupplier);

		Assert.assertNotNull(baselineInformation);
		Assert.assertEquals(
			String.valueOf(baselineIdSupplier.get()),
			baselineInformation.getName());

		int count = BaselineEntryLocalServiceUtil.getBaselineEntriesCount();

		Assert.assertEquals(1, count);

		BlogsEntryLocalServiceUtil.deleteBlogsEntry(blogsEntry.getEntryId());
	}

}
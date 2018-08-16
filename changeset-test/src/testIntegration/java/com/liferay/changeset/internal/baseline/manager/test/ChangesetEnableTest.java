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
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.test.util.BlogsTestUtil;
import com.liferay.changeset.constants.ChangesetConstants;
import com.liferay.changeset.manager.ChangesetBaselineManager;
import com.liferay.changeset.manager.ChangesetManager;
import com.liferay.changeset.model.ChangesetBaselineCollection;
import com.liferay.changeset.model.ChangesetBaselineEntry;
import com.liferay.changeset.service.ChangesetBaselineEntryLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
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
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		_changesetManager.disableChangesets();

		try {
			_blogsEntryLocalService.deleteEntry(_blogsEntry.getEntryId());
		}
		catch (Exception e) {
		}

		try {
			_journalArticleLocalService.deleteJournalArticle(
				_journalArticle.getId());
		}
		catch (Exception e) {
		}
	}

	@Test
	public void testEnableChangeset() throws Exception {
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

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			TestPropsValues.getGroupId(), 0);

		_changesetManager.enableChangesets();

		Optional<ChangesetBaselineCollection> productionBaselineOptional =
			_changesetBaselineManager.getProductionBaseline();

		Assert.assertTrue(
			"Production baseline is not created",
			productionBaselineOptional.isPresent());

		List<ChangesetBaselineEntry> baselineEntries =
			_changesetBaselineManager.getBaselineEntries(
				() -> ChangesetConstants.PRODUCTION_BASELINE_NAME);

		long journalArticleClassNameId = _portal.getClassNameId(
			JournalArticle.class.getName());

		boolean found = false;

		for (ChangesetBaselineEntry baselineEntry : baselineEntries) {
			if ((baselineEntry.getClassNameId() == journalArticleClassNameId) &&
				(baselineEntry.getClassPK() == journalArticle.getId())) {

				found = true;

				break;
			}
		}

		if (!found) {
			Assert.fail("Journal article cannot be found in the baseline");
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
	private ChangesetBaselineEntryLocalService
		_changesetBaselineEntryLocalService;

	@Inject
	private ChangesetBaselineManager _changesetBaselineManager;

	@Inject
	private ChangesetManager _changesetManager;

	private JournalArticle _journalArticle;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private Portal _portal;

}
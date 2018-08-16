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
import com.liferay.changeset.manager.ChangesetBaselineManagerUtil;
import com.liferay.changeset.model.ChangesetBaselineCollection;
import com.liferay.changeset.model.ChangesetBaselineEntry;
import com.liferay.changeset.service.ChangesetBaselineCollectionLocalServiceUtil;
import com.liferay.changeset.service.ChangesetBaselineEntryLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
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
		List<ChangesetBaselineCollection> changesetBaselineCollections =
			ChangesetBaselineCollectionLocalServiceUtil.
				getChangesetBaselineCollections(-1, -1);

		for (ChangesetBaselineCollection changesetBaselineCollection :
				changesetBaselineCollections) {

			ChangesetBaselineCollectionLocalServiceUtil.
				deleteChangesetBaselineCollection(
					changesetBaselineCollection.
						getChangesetBaselineCollectionId());
		}

		List<ChangesetBaselineEntry> baselineEntries =
			ChangesetBaselineEntryLocalServiceUtil.getChangesetBaselineEntries(
				-1, -1);

		for (ChangesetBaselineEntry baselineEntry : baselineEntries) {
			ChangesetBaselineEntryLocalServiceUtil.deleteChangesetBaselineEntry(
				baselineEntry.getChangesetBaselineEntryId());
		}
	}

	@Test
	public void testCreateBaseline() throws Exception {
		Group group = GroupLocalServiceUtil.getGroup(
			CompanyThreadLocal.getCompanyId(), GroupConstants.GUEST);

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			group.getGroupId(), 0);

		Supplier<? extends Serializable> baselineIdSupplier =
			() -> "Test baseline";

		ChangesetBaselineManager changesetBaselineManager =
			ChangesetBaselineManagerUtil.getChangesetManager();

		changesetBaselineManager.createBaseline(baselineIdSupplier);

		ChangesetBaselineCollection baselineInformation =
			changesetBaselineManager.getChangesetBaselineCollection(
				baselineIdSupplier);

		Assert.assertNotNull(baselineInformation);
		Assert.assertEquals(
			String.valueOf(baselineIdSupplier.get()),
			baselineInformation.getName());

		int count =
			ChangesetBaselineEntryLocalServiceUtil.
				getChangesetBaselineEntriesCount();

		Assert.assertEquals(1, count);

		JournalArticleLocalServiceUtil.deleteJournalArticle(
			journalArticle.getId());
	}

}
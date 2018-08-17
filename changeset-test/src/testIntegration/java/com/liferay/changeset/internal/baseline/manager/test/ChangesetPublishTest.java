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
import com.liferay.changeset.constants.ChangesetConstants;
import com.liferay.changeset.manager.ChangesetManager;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.model.ChangesetEntry;
import com.liferay.changeset.service.ChangesetEntryLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
public class ChangesetPublishTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_changesetManager.enableChangesets();

		Optional<ChangesetCollection> changesetCollectionOptional =
			_changesetManager.create("Test", "Test Changeset Collection");

		changesetCollectionOptional.ifPresent(
			changesetCollection -> _changesetCollection = changesetCollection);
	}

	@After
	public void tearDown() {
		_changesetManager.disableChangesets();
	}

	@Test
	public void testPublishChangeset() throws Exception {
		JournalArticle journalArticle = JournalTestUtil.addArticle(
			TestPropsValues.getGroupId(), 0);

		_changesetManager.publish(
			_changesetCollection.getChangesetCollectionId());

		ChangesetEntry invalidChangesetEntry =
			_changesetEntryLocalService.fetchChangesetEntry(
				_changesetCollection.getChangesetCollectionId(),
				journalArticle.getClassNameId(), journalArticle.getClassPK());

		Assert.assertNull(
			"Changeset collection ID was not updated for changeset entry",
			invalidChangesetEntry);

		ChangesetEntry validChangesetEntry =
			_changesetEntryLocalService.fetchChangesetEntry(
				ChangesetConstants.PRODUCTION_CHANGESET_COLLECTION_ID,
				journalArticle.getClassNameId(), journalArticle.getClassPK());

		Assert.assertNotNull(
			"Changeset entry was not updated with production collection ID",
			validChangesetEntry);

		// TODO Test that the index got updated properly when Journal supports CQRS
	}

	private ChangesetCollection _changesetCollection;

	@Inject
	private ChangesetEntryLocalService _changesetEntryLocalService;

	@Inject
	private ChangesetManager _changesetManager;

}

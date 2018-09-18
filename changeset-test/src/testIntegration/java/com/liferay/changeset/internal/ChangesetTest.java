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

package com.liferay.changeset.internal;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.changeset.manager.ChangesetBaselineManager;
import com.liferay.changeset.manager.ChangesetManager;
import com.liferay.changeset.model.ChangesetBaselineCollection;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.model.ChangesetEntry;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentCriterionLocalService;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.test.rule.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mate Thurzo
 */
@RunWith(Arquillian.class)
public class ChangesetTest {

	@Test
	public void criticalPath() throws Exception {

		// Enable changesets

		_changesetManager.enableChangesets();

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

		// Create user segment entry

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(Locale.US, "User Segment");

		CommerceUserSegmentEntry segmentEntry =
			_commerceUserSegmentEntryLocalService.addCommerceUserSegmentEntry(
				nameMap, "USERSEGMENT", true, false, 1.0d,
				new ServiceContext());

		_commerceUserSegmentCriterionLocalService.
			addCommerceUserSegmentCriterion(
				segmentEntry.getCommerceUserSegmentEntryId(), "user",
				StringPool.BLANK, 1.0, new ServiceContext());

		// Check changeset content

		List<ChangesetEntry> changesetEntries =
			_changesetManager.getChangesetEntries(
				changesetCollectionOptional.get().getChangesetCollectionId());

		Assert.assertFalse(
			"Changeset entries should not be empty",
			changesetEntries.isEmpty());
	}

	@Inject
	private ChangesetBaselineManager _changesetBaselineManager;

	@Inject
	private ChangesetManager _changesetManager;

	@Inject
	private CommerceUserSegmentCriterionLocalService
		_commerceUserSegmentCriterionLocalService;

	@Inject
	private CommerceUserSegmentEntryLocalService
		_commerceUserSegmentEntryLocalService;

}
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
import com.liferay.changeset.manager.ChangesetManager;
import com.liferay.changeset.model.ChangesetBaselineCollection;
import com.liferay.changeset.model.ChangesetBaselineEntry;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.model.ChangesetEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
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
 * @author Daniel Kocsis
 */
@RunWith(Arquillian.class)
public class ChangesetCreationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_changesetManager.enableChangesets();
	}

	@After
	public void tearDown() throws Exception {
		_changesetManager.disableChangesets();
	}

	@Test
	public void testCreateEmptyChangeset() {
		final String name = RandomTestUtil.randomString();
		final String descrtiption = RandomTestUtil.randomString();

		long changesetCollectionId =
			_changesetManager.create(name, descrtiption).map(
				ChangesetCollection::getChangesetCollectionId).orElse(0L);

		Assert.assertNotEquals(
			"Changeset collection must not be null", changesetCollectionId, 0L);

		List<ChangesetEntry> changesetEntries =
			_changesetManager.getChangesetEntries(changesetCollectionId);

		Assert.assertTrue(
			"Changeset entries must be empty", changesetEntries.isEmpty());

		Optional<ChangesetBaselineCollection> productionBaseline =
			_changesetBaselineManager.getProductionBaseline();

		Assert.assertTrue(
			"Production baseline must not be null",
			productionBaseline.isPresent());

		Optional<ChangesetBaselineCollection> changesetBaseline =
			_changesetBaselineManager.getChangesetBaselineCollection(
				() -> name);

		Assert.assertTrue(
			"Changeset baseline must not be null",
			changesetBaseline.isPresent());

		List<ChangesetBaselineEntry> productionChangesetBaselineEntries =
			_changesetBaselineManager.getChangesetBaselineEntries(
				() -> productionBaseline.map(
					ChangesetBaselineCollection::
						getChangesetBaselineCollectionId).get());

		List<ChangesetBaselineEntry> changesetBaselineEntries =
			_changesetBaselineManager.getChangesetBaselineEntries(
				() -> changesetBaseline.map(
					ChangesetBaselineCollection::
						getChangesetBaselineCollectionId).get());

		Assert.assertEquals(
			"Baselines must be equal", productionChangesetBaselineEntries,
			changesetBaselineEntries);
	}

	@Inject
	private ChangesetBaselineManager _changesetBaselineManager;

	@Inject
	private ChangesetManager _changesetManager;

}
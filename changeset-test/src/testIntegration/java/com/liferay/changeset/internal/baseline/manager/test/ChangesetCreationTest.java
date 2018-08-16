/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.changeset.internal.baseline.manager.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.changeset.manager.ChangesetBaselineManager;
import com.liferay.changeset.manager.ChangesetBaselineManagerUtil;
import com.liferay.changeset.manager.ChangesetManager;
import com.liferay.changeset.manager.ChangesetManagerUtil;
import com.liferay.changeset.model.ChangesetBaselineCollection;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
		_changesetManager.enableChangesets();
	}

	@After
	public void tearDown() throws Exception {
		_changesetManager.disableChangesets();
	}

	@Test
	public void testCreateEmptyChangeset() throws Exception {
		Optional<ChangesetBaselineCollection> productionBaseline =
			_changesetBaselineManager.getProductionBaseline();

		Assert.assertTrue(
			"Production baseline must not be null",
			productionBaseline.isPresent());

		final String name = RandomTestUtil.randomString();
		final String descrtiption = RandomTestUtil.randomString();

		Optional<ChangesetCollection> changesetCollection =
			_changesetManager.create(name, descrtiption);

		Assert.assertTrue(
			"Changeset collection must not be null",
			changesetCollection.isPresent());
	}

	@Inject
	private ChangesetManager _changesetManager;

	private final ChangesetBaselineManager _changesetBaselineManager =
		ChangesetBaselineManagerUtil.getChangesetManager();

}
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

package com.liferay.changeset;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.changeset.manager.ChangesetBaselineManager;
import com.liferay.changeset.manager.ChangesetManager;
import com.liferay.changeset.model.ChangesetBaselineCollection;
import com.liferay.portal.test.rule.Inject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

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

		_changesetManager.create("CHANGESET-1", "Changeset 1 description");

		// Check changeset baseline

		Optional<ChangesetBaselineCollection> changesetBaselineOptional =
			_changesetBaselineManager.getChangesetBaselineCollection(
				() -> "CHANGESET-1");

		// Check
	}

	@Inject
	private ChangesetManager _changesetManager;

	@Inject
	private ChangesetBaselineManager _changesetBaselineManager;



}

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

package com.liferay.changeset.manager;

import com.liferay.changeset.model.ChangesetBaselineCollection;
import com.liferay.changeset.model.ChangesetBaselineEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author Mate Thurzo
 */
public interface ChangesetBaselineManager {

	public Optional<ChangesetBaselineCollection> createBaseline(
		Supplier<? extends Serializable> baselineIdSupplier);

	public Optional<ChangesetBaselineCollection> createBaseline(
		Supplier<? extends Serializable> baselineIdSupplier,
		ChangesetBaselineCollection copyChangesetBaselineCollection);

	public List<ChangesetBaselineEntry> getBaselineEntries(
		Supplier<? extends Serializable> baselineIdSupplier);

	public Optional<ChangesetBaselineCollection> getChangesetBaselineCollection(
		Supplier<? extends Serializable> baselineIdSupplier);

	public List<ChangesetBaselineEntry> getChangesetBaselineEntries(
		Supplier<Long> baselineCollectionIdSupplier);

	public Optional<ChangesetBaselineCollection> getProductionBaseline();

	public void removeAllBaselines();

	public void removeBaseline(
		Supplier<? extends Serializable> baselineIdSupplier);

}
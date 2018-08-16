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

import com.liferay.changeset.configuration.ChangesetConfiguration;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.model.ChangesetEntry;

import java.util.List;
import java.util.Optional;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
public interface ChangesetManager {

	public Optional<ChangesetCollection> create(
		String name, String description);

	public void disableChangesets();

	public void enableChangesets();

	public Optional<ChangesetCollection> getChangesetCollection(
		long classNameId, long classPK);

	public Optional<ChangesetCollection> getChangesetCollection(
		String className, long classPK);

	public ChangesetConfiguration<?, ?> getChangesetConfigurationByIdentifier(
		String identifier);

	public ChangesetConfiguration<?, ?>
		getChangesetConfigurationByResourceClass(Class<?> clazz);

	public ChangesetConfiguration<?, ?> getChangesetConfigurationByVersionClass(
		Class<?> clazz);

	public ChangesetConfiguration<?, ?>
		getChangesetConfigurationByVersionClassName(String className);

	public List<ChangesetEntry> getChangesetEntries(long changesetCollectionId);

	public Optional<ChangesetEntry> getChangesetEntry(
		long classNameId, long classPK);

	public Optional<ChangesetEntry> getChangesetEntry(
		String className, long classPK);

	public boolean isChangesetSupported(Class<?> clazz);

	public boolean isChangesetSupported(String identifier);

	public void publish(long changesetCollectionId);

	public void remove(long changesetCollectionId);

	public void rollback(long changesetCollectionId);

}
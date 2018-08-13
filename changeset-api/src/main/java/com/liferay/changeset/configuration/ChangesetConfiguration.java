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

package com.liferay.changeset.configuration;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.service.BaseLocalService;

import java.io.Serializable;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Mate Thurzo
 */
@ProviderType
public interface ChangesetConfiguration<T, U> {

	public List<Supplier<? extends Collection<U>>> getBaselining();

	public String getIdentifier();

	public Indexer<U> getIndexer();

	public Class<T> getResourceEntityClass();

	public Class<U> getVersionEntityClass();

	public interface BaseliningStep<T, U> {

		public IndexerStep<T, U> baselining(
			Supplier<? extends Collection<U>> baseliningSupplier);

	}

	public interface Builder<T, U> {

		public ResourceEntityStep<T, U> identifier(String identifier);

	}

	public interface BuildStep {

		public ChangesetConfiguration build();

	}

	public interface IndexerStep<T, U> {

		public BuildStep indexer(
			Function<Class<U>, Indexer<U>> indexerFunction);

	}

	public interface ResourceEntityStep<T, U> {

		public VersionEntityStep<T, U> addResourceEntity(
			Class<T> resourceEntityClass,
			Function<T, Long> resourceEntityIdFunction,
			BaseLocalService resourceEntityLocalService);

	}

	public interface VersionEntityStep<T, U> {

			public BaseliningStep<T, U> addVersionEntity(
				Class<U> versionEntityClass,
				Function<U, Long> versionEntityIdSupplier,
				Function<U, ? extends Serializable>
					versionEntityVersionFunction,
				BaseLocalService versionEntityLocalService);

	}

}
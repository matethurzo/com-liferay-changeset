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

package com.liferay.changeset.internal.configuration;

import aQute.bnd.annotation.ProviderType;

import com.liferay.changeset.configuration.ChangesetConfiguration;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.service.BaseLocalService;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Mate Thurzo
 */
@ProviderType
public class ChangesetConfigurationImpl<T, U>
	implements ChangesetConfiguration<T, U> {

	@Override
	public List<Supplier<? extends Collection<U>>> getBaselining() {
		return _baseliningSuppliers;
	}

	public static class BuilderImpl<T, U> implements Builder<T, U> {

		public BuilderImpl() {
			_changesetConfiguration = new ChangesetConfigurationImpl<>();
		}

		public ResourceEntityStep<T, U> identifier(String identifier) {
			_changesetConfiguration._identifier = identifier;

			return new ResourceEntityStepImpl<>();
		}

		public class BaseliningStepImpl<T, U> implements BaseliningStep<T, U> {

			public IndexerStep<T, U> baselining(
				Supplier<? extends Collection<U>> baseliningSupplier) {

				_changesetConfiguration._baseliningSuppliers.add(
					baseliningSupplier);

				return new IndexerStepImpl<>();
			}

		}

		public class BuildStepImpl implements BuildStep {

			@Override
			public ChangesetConfiguration build() {
				return _changesetConfiguration;
			}

		}

		public class IndexerStepImpl<T, U> implements IndexerStep<T, U> {

			public BuildStep indexer(
				Function<Class<U>, Indexer<U>> indexerFunction) {

				return new BuildStepImpl();
			}

		}

		public class ResourceEntityStepImpl<T, U>
			implements ResourceEntityStep<T, U> {

			public VersionEntityStep<T, U> addResourceEntity(
				Class<T> resourceEntityClass,
				Function<T, Long> resourceEntityIdFunction,
				BaseLocalService resourceEntityLocalService) {

				return new VersionEntityStepImpl<>();
			}

		}

		public class VersionEntityStepImpl<T, U>
			implements VersionEntityStep<T, U> {

			public BaseliningStep<T, U> addVersionEntity(
				Class<U> versionEntityClass,
				Function<U, Long> versionEntityIdSupplier,
				Function<U, ? extends Serializable>
					versionEntityVersionFunction,
				BaseLocalService versionEntityLocalService) {

				return new BaseliningStepImpl<>();
			}

		}

		private final ChangesetConfigurationImpl _changesetConfiguration;

	}

	private ChangesetConfigurationImpl() {
	}

	private final List<Supplier<? extends Collection<U>>> _baseliningSuppliers =
		new ArrayList<>();
	private String _identifier;

}
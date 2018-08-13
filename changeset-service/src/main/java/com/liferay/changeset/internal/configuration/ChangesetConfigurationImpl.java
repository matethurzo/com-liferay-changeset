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

	@Override
	public String getIdentifier() {
		return _identifier;
	}

	@Override
	public Class<T> getResourceEntityClass() {
		return _resouceEntityInformation.getEntityClass();
	}

	@Override
	public Class<U> getVersionEntityClass() {
		return _versionEntityInformation.getEntityClass();
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

				_changesetConfiguration._versionEntityInformation.
					setIndexerFunction(indexerFunction);

				return new BuildStepImpl();
			}

		}

		public class ResourceEntityStepImpl<T, U>
			implements ResourceEntityStep<T, U> {

			public VersionEntityStep<T, U> addResourceEntity(
				Class<T> resourceEntityClass,
				Function<T, Long> resourceEntityIdFunction,
				BaseLocalService resourceEntityLocalService) {

				_changesetConfiguration._resouceEntityInformation =
					new EntityInformation<>(
						resourceEntityClass, resourceEntityIdFunction,
						resourceEntityLocalService);

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

				_changesetConfiguration._versionEntityInformation =
					new EntityInformation<>(
						versionEntityClass, versionEntityIdSupplier,
						versionEntityLocalService);

				_changesetConfiguration._versionEntityInformation.
					setVersionFunction(versionEntityVersionFunction);

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
	private EntityInformation<T> _resouceEntityInformation;
	private EntityInformation<U> _versionEntityInformation;

	private static class EntityInformation<T> {

		public EntityInformation(
			Class<T> entityClass, Function<T, Long> entityIdSupplier,
			BaseLocalService entityLocalService) {

			_class = entityClass;
			_idSupplier = entityIdSupplier;
			_baseLocalService = entityLocalService;
		}

		public BaseLocalService getBaseLocalService() {
			return _baseLocalService;
		}

		public Class<T> getEntityClass() {
			return _class;
		}

		public Function<T, Long> getIdSupplier() {
			return _idSupplier;
		}

		public Function<Class<T>, Indexer<T>> getIndexerFunction() {
			return _indexerFunction;
		}

		public Function<T, ? extends Serializable> getVersionFunction() {
			return _versionFunction;
		}

		public void setIndexerFunction(
			Function<Class<T>, Indexer<T>> indexerFunction) {

			_indexerFunction = indexerFunction;
		}

		public void setVersionFunction(
			Function<T, ? extends Serializable> versionFunction) {

			_versionFunction = versionFunction;
		}

		private final BaseLocalService _baseLocalService;
		private final Class<T> _class;
		private final Function<T, Long> _idSupplier;
		private Function<Class<T>, Indexer<T>> _indexerFunction;
		private Function<T, ? extends Serializable> _versionFunction;

	}

}
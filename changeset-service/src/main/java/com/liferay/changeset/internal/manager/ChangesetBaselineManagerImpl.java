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

package com.liferay.changeset.internal.manager;

import com.liferay.changeset.configuration.ChangesetConfiguration;
import com.liferay.changeset.configuration.ChangesetConfigurationRegistrar;
import com.liferay.changeset.constants.ChangesetConstants;
import com.liferay.changeset.exception.NoSuchBaselineEntryException;
import com.liferay.changeset.internal.configuration.ChangesetConfigurationImpl;
import com.liferay.changeset.manager.ChangesetBaselineManager;
import com.liferay.changeset.model.ChangesetBaselineCollection;
import com.liferay.changeset.model.ChangesetBaselineEntry;
import com.liferay.changeset.service.ChangesetBaselineCollectionLocalService;
import com.liferay.changeset.service.ChangesetBaselineEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = ChangesetBaselineManager.class)
public class ChangesetBaselineManagerImpl implements ChangesetBaselineManager {

	@Override
	public Optional<ChangesetBaselineCollection> createBaseline(
		Supplier<? extends Serializable> baselineIdSupplier) {

		return createBaseline(baselineIdSupplier, null);
	}

	@Override
	public Optional<ChangesetBaselineCollection> createBaseline(
		Supplier<? extends Serializable> baselineIdSupplier,
		ChangesetBaselineCollection copyChangesetBaselineCollection) {

		// todo: need to figure out a proper way to user handling

		final ChangesetBaselineCollection changesetBaselineCollection =
			_changesetBaselineCollectionLocalService.
				addChangesetBaselineCollection(
					PrincipalThreadLocal.getUserId(),
					String.valueOf(baselineIdSupplier.get()));

		if (copyChangesetBaselineCollection == null) {
			_addDefaultBaselineVersions(changesetBaselineCollection);
		}
		else {
			List<ChangesetBaselineEntry> changesetBaselineEntries =
				_changesetBaselineEntryLocalService.getChangesetBaselineEntries(
					copyChangesetBaselineCollection.
						getChangesetBaselineCollectionId());

			changesetBaselineEntries.forEach(
				changesetBaselineEntry ->
					_changesetBaselineEntryLocalService.
						addChangesetBaselineEntry(
							changesetBaselineCollection.
								getChangesetBaselineCollectionId(),
							changesetBaselineEntry.getClassNameId(),
							changesetBaselineEntry.getClassPK(),
							changesetBaselineEntry.getResourcePrimKey(),
							changesetBaselineEntry.getVersion()));
		}

		return Optional.of(changesetBaselineCollection);
	}

	@Override
	public List<ChangesetBaselineEntry> getBaselineEntries(
		Supplier<? extends Serializable> baselineIdSupplier) {

		Optional<ChangesetBaselineCollection> baselineCollectionOptional =
			_changesetBaselineCollectionLocalService.
				getChangesetBaselineCollectionByName(
					String.valueOf(baselineIdSupplier.get()));

		if (!baselineCollectionOptional.isPresent()) {
			return Collections.emptyList();
		}

		long collectionId = baselineCollectionOptional.map(
			ChangesetBaselineCollection::getChangesetBaselineCollectionId
		).orElse(
			0L
		);

		return _changesetBaselineEntryLocalService.getChangesetBaselineEntries(
			collectionId);
	}

	@Override
	public Optional<ChangesetBaselineEntry> getBaselineEntry(
		long baselineId, long classNameId, long classPK) {

		try {
			ChangesetBaselineEntry changesetBaselineEntries =
				_changesetBaselineEntryLocalService.getChangesetBaselineEntry(
					baselineId, classNameId, classPK);

			return Optional.of(changesetBaselineEntries);
		}
		catch (NoSuchBaselineEntryException nsbee) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to find changeset baseline entry", nsbee);
			}

			return Optional.empty();
		}
	}

	@Override
	public Optional<ChangesetBaselineEntry> getBaselineEntry(
		long baselineId, String className, long classPK) {

		long classNameId = _portal.getClassNameId(className);

		return getBaselineEntry(baselineId, classNameId, classPK);
	}

	public double getBaselineVersion(
		long changesetBaselineId, long classNameId, long classPK) {

		return 0;
	}

	@Override
	public Optional<ChangesetBaselineCollection> getChangesetBaselineCollection(
		Supplier<? extends Serializable> baselineIdSupplier) {

		return _changesetBaselineCollectionLocalService.
			getChangesetBaselineCollectionByName(
				String.valueOf(baselineIdSupplier.get()));
	}

	@Override
	public List<ChangesetBaselineEntry> getChangesetBaselineEntries(
		Supplier<Long> baselineCollectionIdSupplier) {

		return _changesetBaselineEntryLocalService.getChangesetBaselineEntries(
			baselineCollectionIdSupplier.get());
	}

	@Override
	public Optional<ChangesetBaselineEntry> getLatestBaselineEntry(
		long changesetBaselineCollectionId, long resourcePrimKey) {

		ChangesetBaselineEntry changesetBaselineEntry =
			_changesetBaselineEntryLocalService.
				fetchLatestChangesetBaselineEntry(
					changesetBaselineCollectionId, resourcePrimKey);

		return Optional.ofNullable(changesetBaselineEntry);
	}

	@Override
	public Optional<ChangesetBaselineCollection> getProductionBaseline() {
		return getChangesetBaselineCollection(
			() -> ChangesetConstants.PRODUCTION_BASELINE_NAME);
	}

	@Override
	public void removeAllBaselines() {
		List<ChangesetBaselineCollection> changesetBaselineCollections =
			_changesetBaselineCollectionLocalService.
				getChangesetBaselineCollections(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		changesetBaselineCollections.forEach(this::_removeBaselineCollection);
	}

	@Override
	public void removeBaseline(
		Supplier<? extends Serializable> baselineIdSupplier) {

		Optional<ChangesetBaselineCollection> baselineCollectionOptional =
			_changesetBaselineCollectionLocalService.
				getChangesetBaselineCollectionByName(
					String.valueOf(baselineIdSupplier.get()));

		baselineCollectionOptional.ifPresent(this::_removeBaselineCollection);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		unbind = "removeChangesetConfigurationRegistrar"
	)
	protected void addChangesetConfigurationRegistrar(
		ChangesetConfigurationRegistrar<?, ?> changesetConfigurationRegistrar) {

		_changesetConfigurationRegistrars.add(changesetConfigurationRegistrar);
	}

	protected void removeChangesetConfigurationRegistrar(
		ChangesetConfigurationRegistrar<?, ?> changesetConfigurationRegistrar) {

		_changesetConfigurationRegistrars.remove(
			changesetConfigurationRegistrar);
	}

	private void _addChangesetBaselineEntry(
		long changesetBaselineCollectionId, Object object,
		ChangesetConfiguration<?, ClassedModel> changesetConfiguration) {

		Class<?> versionEntityClass =
			changesetConfiguration.getVersionEntityClass();

		long classNameId = _portal.getClassNameId(versionEntityClass.getName());

		ClassedModel versionEntity = (ClassedModel)object;

		Function<ClassedModel, Serializable> versionEntityIdFunction =
			changesetConfiguration.
				getVersionEntityIdFromVersionEntityFunction();

		long classPK = (long)versionEntityIdFunction.apply(versionEntity);

		Function<ClassedModel, Serializable>
			resourceEntityIdFromVersionEntityFunction =
				changesetConfiguration.
					getResourceEntityIdFromVersionEntityFunction();

		long resourcePrimKey =
			(long)resourceEntityIdFromVersionEntityFunction.apply(
				versionEntity);

		Function<ClassedModel, ? extends Serializable> versionFunction =
			changesetConfiguration.getVersionFunction();

		double version = (Double)versionFunction.apply(versionEntity);

		_changesetBaselineEntryLocalService.addChangesetBaselineEntry(
			changesetBaselineCollectionId, classNameId, classPK,
			resourcePrimKey, version);
	}

	private void _addDefaultBaselineVersions(
		ChangesetBaselineCollection changesetBaselineCollection) {

		Map<Class<?>, ChangesetConfiguration<?, ?>> changesetConfigurations =
			new HashMap<>();

		Stream<ChangesetConfigurationRegistrar<?, ?>> registrarStream =
			_changesetConfigurationRegistrars.stream();

		registrarStream.filter(
			Objects::nonNull
		).forEach(
			registrar -> {
				ChangesetConfiguration<?, ?> changesetConfiguration =
					registrar.changesetConfiguration(
						new ChangesetConfigurationImpl.BuilderImpl<>());

				changesetConfigurations.put(
					changesetConfiguration.getVersionEntityClass(),
					changesetConfiguration);
			}
		);

		Collection<ChangesetConfiguration<?, ?>> values =
			changesetConfigurations.values();

		// todo: _addChangesetBaselineEntry calls local services.
		// As ParallelStream uses a ForkJoinPool behind the scenes with multiple
		// threads and Spring transaction management uses ThreadLocals for
		// storing transaction state.
		// --> Calling services from parallel streams won't work :)

		Stream<ChangesetConfiguration<?, ?>> stream = values.stream();

		stream.filter(
			Objects::nonNull
		).map(
			ChangesetConfiguration::getBaselining
		).flatMap(
			List::stream
		).map(
			Supplier::get
		).flatMap(
			Collection::stream
		).map(
			object -> (ClassedModel)object
		).forEach(
			object -> _addChangesetBaselineEntry(
				changesetBaselineCollection.getChangesetBaselineCollectionId(),
				object,
				(ChangesetConfiguration<?, ClassedModel>)
					changesetConfigurations.get(object.getModelClass()))
		);
	}

	private void _removeBaselineCollection(
		ChangesetBaselineCollection baselineCollection) {

		_changesetBaselineEntryLocalService.deleteChangesetBaselineEntries(
			baselineCollection.getChangesetBaselineCollectionId());

		try {
			_changesetBaselineCollectionLocalService.
				deleteChangesetBaselineCollection(
					baselineCollection.getChangesetBaselineCollectionId());
		}
		catch (PortalException pe) {
			_log.error("Unable to remove baseline collection", pe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ChangesetBaselineManagerImpl.class);

	@Reference
	private ChangesetBaselineCollectionLocalService
		_changesetBaselineCollectionLocalService;

	@Reference
	private ChangesetBaselineEntryLocalService
		_changesetBaselineEntryLocalService;

	private final List<ChangesetConfigurationRegistrar<?, ?>>
		_changesetConfigurationRegistrars = new ArrayList<>();

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}
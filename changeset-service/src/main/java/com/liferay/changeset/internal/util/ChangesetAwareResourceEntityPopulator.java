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

package com.liferay.changeset.internal.util;

import com.liferay.changeset.configuration.ChangesetConfiguration;
import com.liferay.changeset.manager.ChangesetBaselineManager;
import com.liferay.changeset.manager.ChangesetManager;
import com.liferay.changeset.model.ChangesetBaselineCollection;
import com.liferay.changeset.model.ChangesetEntry;
import com.liferay.changeset.util.ResourceEntityPopulator;
import com.liferay.changeset.util.ResourceEntityPopulatorRegistry;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	immediate = true, property = "changeset.aware=true",
	service = ResourceEntityPopulator.class
)
public class ChangesetAwareResourceEntityPopulator
	<T extends BaseModel, U extends BaseModel>
		implements ResourceEntityPopulator<T, U> {

	public Class<T> getResourceEntityClass() {
		return null;
	}

	public String getResourceEntityClassName() {
		return getClass().getName();
	}

	public List<T> populate(List<T> resourceEntities) {
		if (ListUtil.isEmpty(resourceEntities)) {
			return resourceEntities;
		}

		T resourceEntity = resourceEntities.get(0);

		if (!_changesetManager.isChangesetSupported(
				resourceEntity.getModelClass())) {

			return resourceEntities;
		}

		Stream<T> resourceEntitiesStream = resourceEntities.stream();

		return resourceEntitiesStream.map(
			this::populate
		).filter(
			Objects::nonNull
		).collect(
			Collectors.toList()
		);
	}

	public T populate(T resourceEntity) {
		if (resourceEntity == null) {
			return null;
		}

		if (!_changesetManager.isChangesetSupported(
				resourceEntity.getModelClass())) {

			return resourceEntity;
		}

		Optional<Long> currentChangesetCollectionId =
			_changesetManager.getCurrentChangesetCollectionId();

		if (!currentChangesetCollectionId.isPresent()) {
			return resourceEntity;
		}

		long changesetCollectionId = currentChangesetCollectionId.get();

		long resourcePrimKey = _getResourcePrimKey(resourceEntity);

		Optional<U> versionEntityOptional = _getVersionEntityFromChangeset(
			changesetCollectionId, resourcePrimKey);

		if (!versionEntityOptional.isPresent()) {
			versionEntityOptional = _getVersionEntityFromBaseline(
				changesetCollectionId, resourcePrimKey);
		}

		return versionEntityOptional.filter(
			this::_isVersionEntityStatusAllowed
		).map(
			versionEntity -> populate(resourceEntity, versionEntity)
		).orElse(
			null
		);
	}

	public T populate(T resourceEntity, U versionEntity) {
		ResourceEntityPopulator<T, U> resourceEntityPopulator =
			_resourceEntityPopulatorRegistry.getResourceEntityPopulator(
				resourceEntity.getModelClassName());

		return resourceEntityPopulator.populate(resourceEntity, versionEntity);
	}

	private long _getResourcePrimKey(T resourceEntity) {
		Optional<ChangesetConfiguration<?, ?>> changesetConfigurationOptional =
			_changesetManager.getChangesetConfigurationByResourceClass(
				resourceEntity.getModelClass());

		return changesetConfigurationOptional.map(
			changesetConfiguration ->
				(ChangesetConfiguration<T, U>)changesetConfiguration
		).map(
			changesetConfiguration -> changesetConfiguration.
				getResourceEntityIdFromResourceEntityFunction().apply(
					resourceEntity)
		).map(
			GetterUtil::getLong
		).orElse(
			0L
		);
	}

	private U _getVersionEntityByClassNameIdClassPK(
		long versionEntityClassNameId, long versionEntityId) {

		Optional<ChangesetConfiguration<?, ?>> changesetConfigurationOptional =
			_changesetManager.getChangesetConfigurationByVersionClassName(
				_portal.getClassName(versionEntityClassNameId));

		return changesetConfigurationOptional.map(
			changesetConfiguration ->
				(ChangesetConfiguration<T, U>)changesetConfiguration
		).map(
			changesetConfiguration -> changesetConfiguration.
				getVersionEntityFunction().apply(versionEntityId)
		).orElse(
			null
		);
	}

	private Optional<U> _getVersionEntityFromBaseline(
		long changesetCollectionId, long resourcePrimKey) {

		Optional<ChangesetBaselineCollection>
			changesetBaselineCollectionOptional =
				_changesetManager.getChangesetBaselineCollection(
					changesetCollectionId);

		return changesetBaselineCollectionOptional.map(
			ChangesetBaselineCollection::getChangesetBaselineCollectionId
		).flatMap(
			changesetBaselineCollectionId ->
				_changesetBaselineManager.getLatestBaselineEntry(
					changesetBaselineCollectionId, resourcePrimKey)
		).map(
			changesetBaselineEntry -> _getVersionEntityByClassNameIdClassPK(
				changesetBaselineEntry.getClassNameId(),
				changesetBaselineEntry.getClassPK())
		);
	}

	private Optional<U> _getVersionEntityFromChangeset(
		long changesetCollectionId, long resourcePrimKey) {

		Optional<ChangesetEntry> changesetEntryOptional =
			_changesetManager.getLatestChangesetEntry(
				changesetCollectionId, resourcePrimKey);

		return changesetEntryOptional.map(
			changesetEntry -> _getVersionEntityByClassNameIdClassPK(
				changesetEntry.getClassNameId(), changesetEntry.getClassPK())
		);
	}

	private boolean _isVersionEntityStatusAllowed(U versionEntity) {
		if (versionEntity == null) {
			return false;
		}

		Optional<ChangesetConfiguration<?, ?>> changesetConfigurationOptional =
			_changesetManager.getChangesetConfigurationByVersionClass(
				versionEntity.getModelClass());

		if (!changesetConfigurationOptional.isPresent()) {
			return false;
		}

		ChangesetConfiguration<T, U> changesetConfiguration =
			(ChangesetConfiguration<T, U>)changesetConfigurationOptional.get();

		Integer[] allowedStatuses = changesetConfiguration.getAllowedStatuses();

		Set<Integer> allowedStatusesSet = Arrays.stream(
			allowedStatuses).collect(Collectors.toSet());

		int status = changesetConfiguration.getStatusFunction().apply(
			versionEntity);

		return allowedStatusesSet.contains(status);
	}

	@Reference
	private ChangesetBaselineManager _changesetBaselineManager;

	@Reference
	private ChangesetManager _changesetManager;

	@Reference
	private Portal _portal;

	@Reference
	private ResourceEntityPopulatorRegistry _resourceEntityPopulatorRegistry;

}
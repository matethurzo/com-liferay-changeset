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

import static com.liferay.changeset.constants.ChangesetConstants.PRODUCTION_BASELINE_NAME;

import com.liferay.changeset.configuration.ChangesetConfiguration;
import com.liferay.changeset.constants.ChangesetConstants;
import com.liferay.changeset.manager.ChangesetBaselineManager;
import com.liferay.changeset.manager.ChangesetManager;
import com.liferay.changeset.model.ChangesetBaselineCollection;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.model.ChangesetEntry;
import com.liferay.changeset.service.ChangesetCollectionLocalService;
import com.liferay.changeset.service.ChangesetEntryLocalService;
import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.util.Portal;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = ChangesetManager.class)
public class ChangesetManagerImpl implements ChangesetManager {

	@Override
	public Optional<ChangesetCollection> create(
		String name, String description) {

		// todo: resolve groupId, userId!!!

		ChangesetCollection changesetCollection = null;

		try {
			changesetCollection =
				_changesetCollectionLocalService.addChangesetCollection(
					PrincipalThreadLocal.getUserId(),
					CompanyThreadLocal.getCompanyId(), name, description);

			Optional<ChangesetBaselineCollection>
				productionChangesetBaselineCollection =
					_changesetBaselineManager.getChangesetBaselineCollection(
						() -> PRODUCTION_BASELINE_NAME);

			_changesetBaselineManager.createBaseline(
				() -> name,
				productionChangesetBaselineCollection.orElseThrow(
					() -> new IllegalStateException(
						"Unable to determine production baseline")));
		}
		catch (PortalException pe) {
			_log.error("Unable to create new ChangesetCollection", pe);
		}

		return Optional.ofNullable(changesetCollection);
	}

	@Override
	public void disableChangesets() {
		_changesetBaselineManager.removeBaseline(
			() -> PRODUCTION_BASELINE_NAME);
	}

	@Override
	public void enableChangesets() {
		_changesetBaselineManager.createBaseline(
			() -> PRODUCTION_BASELINE_NAME);
	}

	@Override
	public Optional<ChangesetCollection> getChangesetCollection(
		long classNameId, long classPK) {

		DynamicQuery dynamicQuery = _changesetEntryLocalService.dynamicQuery();

		Property classNameIdProperty = PropertyFactoryUtil.forName(
			"classNameId");
		Property classPKProperty = PropertyFactoryUtil.forName("classPK");

		Conjunction conjunction = RestrictionsFactoryUtil.conjunction();

		conjunction.add(classNameIdProperty.eq(classNameId));
		conjunction.add(classPKProperty.eq(classPK));

		dynamicQuery.add(conjunction);

		List<ChangesetEntry> changesetEntries =
			_changesetEntryLocalService.dynamicQuery(dynamicQuery);

		Stream<ChangesetEntry> stream = changesetEntries.stream();

		return stream.filter(
			Objects::nonNull
		).findFirst(
		).map(
			changesetEntry ->
				_changesetCollectionLocalService.fetchChangesetCollection(
					changesetEntry.getChangesetCollectionId())
		);
	}

	@Override
	public Optional<ChangesetCollection> getChangesetCollection(
		String className, long classPK) {

		long classNameId = _portal.getClassNameId(className);

		return getChangesetCollection(classNameId, classPK);
	}

	public ChangesetConfiguration<?, ?> getChangesetConfigurationByIdentifier(
		String identifier) {

		return _configurationsByIdentifier.get(identifier);
	}

	public ChangesetConfiguration<?, ?>
		getChangesetConfigurationByResourceClass(Class<?> clazz) {

		return _configurationsByResourceClass.get(clazz);
	}

	public ChangesetConfiguration<?, ?> getChangesetConfigurationByVersionClass(
			Class<?> clazz) {

		return _configurationsByVersionClass.get(clazz);
	}

	@Override
	public List<ChangesetEntry> getChangesetEntries(
		long changesetCollectionId) {

		// TODO implement

		return Collections.emptyList();
	}

	@Override
	public Optional<ChangesetEntry> getChangesetEntry(
		long classNameId, long classPK) {

		Optional<ChangesetCollection> changesetCollectionOptional =
			getChangesetCollection(classNameId, classPK);

		ChangesetEntry changesetEntry = null;

		if (changesetCollectionOptional.isPresent()) {
			ChangesetCollection changesetCollection =
				changesetCollectionOptional.get();

			changesetEntry = _changesetEntryLocalService.fetchChangesetEntry(
				changesetCollection.getChangesetCollectionId(), classNameId,
				classPK);
		}

		return Optional.ofNullable(changesetEntry);
	}

	@Override
	public Optional<ChangesetEntry> getChangesetEntry(
		String className, long classPK) {

		long classNameId = _portal.getClassNameId(className);

		return getChangesetEntry(classNameId, classPK);
	}

	@Override
	public boolean isChangesetSupported(Class<?> clazz) {
		if (_configurationsByResourceClass.containsKey(clazz) ||
			_configurationsByVersionClass.containsKey(clazz)) {

			return true;
		}

		return false;
	}

	public boolean isChangesetSupported(String identifier) {
		return _configurationsByIdentifier.containsKey(identifier);
	}

	@Override
	public void publish(long changesetCollectionId) {
	}

	@Override
	public void remove(long changesetCollectionId) {
	}

	@Override
	public void rollback(long changesetCollectionId) {
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setChangesetConfiguration(
		ChangesetConfiguration<?, ?> changesetConfiguration) {

		_configurationsByIdentifier.put(
			changesetConfiguration.getIdentifier(), changesetConfiguration);
		_configurationsByResourceClass.put(
			changesetConfiguration.getResourceEntityClass(),
			changesetConfiguration);
		_configurationsByVersionClass.put(
			changesetConfiguration.getVersionEntityClass(),
			changesetConfiguration);
	}

	protected void unsetChangesetConfiguration(
		ChangesetConfiguration<?, ?> changesetConfiguration) {

		_configurationsByIdentifier.remove(
			changesetConfiguration.getIdentifier());
		_configurationsByResourceClass.remove(
			changesetConfiguration.getResourceEntityClass());
		_configurationsByVersionClass.remove(
			changesetConfiguration.getVersionEntityClass());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ChangesetManagerImpl.class);

	@Reference
	private ChangesetBaselineManager _changesetBaselineManager;

	@Reference
	private ChangesetCollectionLocalService _changesetCollectionLocalService;

	@Reference
	private ChangesetEntryLocalService _changesetEntryLocalService;

	private final Map<String, ChangesetConfiguration<?, ?>>
		_configurationsByIdentifier = new HashMap<>();
	private final Map<Class<?>, ChangesetConfiguration<?, ?>>
		_configurationsByResourceClass = new HashMap<>();
	private final Map<Class<?>, ChangesetConfiguration<?, ?>>
		_configurationsByVersionClass = new HashMap<>();

	@Reference
	private Portal _portal;

}
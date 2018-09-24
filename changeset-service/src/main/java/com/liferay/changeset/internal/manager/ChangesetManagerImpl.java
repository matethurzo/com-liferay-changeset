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
import com.liferay.changeset.internal.configuration.ChangesetConfigurationImpl;
import com.liferay.changeset.internal.search.ChangesetIndexerPostProcessor;
import com.liferay.changeset.manager.ChangesetBaselineManager;
import com.liferay.changeset.manager.ChangesetManager;
import com.liferay.changeset.model.ChangesetBaselineCollection;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.model.ChangesetEntry;
import com.liferay.changeset.service.ChangesetBaselineEntryLocalService;
import com.liferay.changeset.service.ChangesetCollectionLocalService;
import com.liferay.changeset.service.ChangesetEntryLocalService;
import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.util.Portal;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
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
	public Optional<ChangesetEntry> addChangesetEntry(
		long changesetCollectionId, long resourcePrimKey, long classNameId,
		long classPK) {

		long userId = PrincipalThreadLocal.getUserId();

		try {
			ChangesetEntry changesetEntry =
				_changesetEntryLocalService.addChangesetEntry(
					userId, changesetCollectionId, resourcePrimKey, classNameId,
					classPK);

			return Optional.of(changesetEntry);
		}
		catch (PortalException pe) {
			_log.error("Unable to create changeset entry", pe);
		}

		return Optional.empty();
	}

	@Override
	public Optional<ChangesetEntry> addChangesetEntry(
		long changesetCollectionId, long resourcePrimKey, String className,
		long classPK) {

		long classNameId = _portal.getClassNameId(className);

		return addChangesetEntry(
			changesetCollectionId, resourcePrimKey, classNameId, classPK);
	}

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
						() -> ChangesetConstants.PRODUCTION_BASELINE_NAME);

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
		_changesetBaselineManager.removeAllBaselines();

		List<ChangesetCollection> changesetCollections =
			_changesetCollectionLocalService.getChangesetCollections(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (ChangesetCollection changesetCollection : changesetCollections) {
			List<ChangesetEntry> changesetEntries =
				_changesetEntryLocalService.getChangesetEntries(
					changesetCollection.getChangesetCollectionId());

			changesetEntries.forEach(
				_changesetEntryLocalService::deleteChangesetEntry);

			_changesetCollectionLocalService.deleteChangesetCollection(
				changesetCollection);
		}
	}

	@Override
	public void enableChangesets() {
		_changesetBaselineManager.createBaseline(
			() -> ChangesetConstants.PRODUCTION_BASELINE_NAME);
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

	public Optional<ChangesetConfiguration<?, ?>>
		getChangesetConfigurationByIdentifier(String identifier) {

		return Optional.ofNullable(_configurationsByIdentifier.get(identifier));
	}

	public Optional<ChangesetConfiguration<?, ?>>
		getChangesetConfigurationByResourceClass(Class<?> clazz) {

		return Optional.ofNullable(_configurationsByResourceClass.get(clazz));
	}

	@Override
	public Optional<ChangesetConfiguration<?, ?>>
		getChangesetConfigurationByResourceClassName(String className) {

		Set<Class<?>> keySet = _configurationsByResourceClass.keySet();

		for (Class<?> key : keySet) {
			String name = key.getName();

			if (name.equals(className)) {
				return Optional.ofNullable(
					_configurationsByResourceClass.get(key));
			}
		}

		return Optional.empty();
	}

	public Optional<ChangesetConfiguration<?, ?>>
		getChangesetConfigurationByVersionClass(Class<?> clazz) {

		return Optional.ofNullable(_configurationsByVersionClass.get(clazz));
	}

	@Override
	public Optional<ChangesetConfiguration<?, ?>>
		getChangesetConfigurationByVersionClassName(String className) {

		Set<Class<?>> keySet = _configurationsByVersionClass.keySet();

		for (Class<?> key : keySet) {
			String name = key.getName();

			if (name.equals(className)) {
				return Optional.ofNullable(
					_configurationsByVersionClass.get(key));
			}
		}

		return Optional.empty();
	}

	@Override
	public List<ChangesetEntry> getChangesetEntries(
		long changesetCollectionId) {

		return _changesetEntryLocalService.getChangesetEntries(
			changesetCollectionId);
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
	public boolean isChangesetEnabled() {
		Optional<ChangesetBaselineCollection> productionBaselineOptional =
			_changesetBaselineManager.getProductionBaseline();

		return productionBaselineOptional.isPresent();
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
		long productionChangesetBaselineCollectionId =
			_changesetBaselineManager.getProductionBaseline(
			).map(
				ChangesetBaselineCollection::getChangesetBaselineCollectionId
			).orElseThrow(
				() -> new IllegalStateException(
					"Unable to determine production baseline")
			);

		List<ChangesetEntry> changesetEntries = getChangesetEntries(
			changesetCollectionId);

		changesetEntries.forEach(
			changesetEntry ->
				_changesetBaselineEntryLocalService.addChangesetBaselineEntry(
					productionChangesetBaselineCollectionId,
					changesetEntry.getClassNameId(),
					changesetEntry.getClassPK(),
					changesetEntry.getResourcePrimKey(), 1.0));

		// TODO What version should we set here?
		// TODO What else needs to be done during publish?

	}

	@Override
	public void remove(long changesetCollectionId) {
	}

	@Override
	public void rollback(long changesetCollectionId) {
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		unbind = "removeChangesetConfigurationRegistrar"
	)
	protected void addChangesetConfigurationRegistrar(
		ChangesetConfigurationRegistrar<?, ?> changesetConfigurationRegistrar) {

		ChangesetConfiguration<?, ?> changesetConfiguration =
			changesetConfigurationRegistrar.changesetConfiguration(
				new ChangesetConfigurationImpl.BuilderImpl<>());

		_addChangesetConfiguration(changesetConfiguration);

		_wrapIndexer(changesetConfiguration);
	}

	protected void removeChangesetConfigurationRegistrar(
		ChangesetConfigurationRegistrar<?, ?> changesetConfigurationRegistrar) {

		ChangesetConfiguration<?, ?> changesetConfiguration =
			changesetConfigurationRegistrar.changesetConfiguration(
				new ChangesetConfigurationImpl.BuilderImpl<>());

		_removeChangesetConfiguration(changesetConfiguration);
	}

	private void _addChangesetConfiguration(
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

	private void _removeChangesetConfiguration(
		ChangesetConfiguration<?, ?> changesetConfiguration) {

		_configurationsByIdentifier.remove(
			changesetConfiguration.getIdentifier());
		_configurationsByResourceClass.remove(
			changesetConfiguration.getResourceEntityClass());
		_configurationsByVersionClass.remove(
			changesetConfiguration.getVersionEntityClass());
	}

	private void _wrapIndexer(
		ChangesetConfiguration<?, ?> changesetConfiguration) {

		Indexer indexer = changesetConfiguration.getIndexer();

		// todo: occasionally null because indexer registration is wrong

		if (indexer == null) {
			return;
		}

		final Bundle bundle = FrameworkUtil.getBundle(
			ChangesetManagerImpl.class);

		final BundleContext bundleContext = bundle.getBundleContext();

		String indexerClassName = indexer.getClassName();

		Dictionary<String, Object> properties = new Hashtable<>();

		// todo: it won't work this way, indexer class name will be the same if we move forward with wrapping

		properties.put("indexer.class.name", indexerClassName);

		bundleContext.registerService(
			IndexerPostProcessor.class, _CHANGESET_INDEXER_POST_PROCESSOR,
			properties);
	}

	private static final ChangesetIndexerPostProcessor
		_CHANGESET_INDEXER_POST_PROCESSOR = new ChangesetIndexerPostProcessor();

	private static final Log _log = LogFactoryUtil.getLog(
		ChangesetManagerImpl.class);

	@Reference
	private ChangesetBaselineEntryLocalService
		_changesetBaselineEntryLocalService;

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
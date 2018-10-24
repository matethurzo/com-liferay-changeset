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
import com.liferay.changeset.constants.ChangesetPortletKeys;
import com.liferay.changeset.cqrs.manager.ChangesetCQRSManager;
import com.liferay.changeset.internal.configuration.ChangesetConfigurationImpl;
import com.liferay.changeset.internal.search.ChangesetIndexerPostProcessor;
import com.liferay.changeset.internal.search.ChangesetIndexingUtil;
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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

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
	public void checkout(long changesetCollectionId) {
		if (Validator.isNull(changesetCollectionId)) {
			return;
		}

		ChangesetCollection changesetCollection =
			_changesetCollectionLocalService.fetchChangesetCollection(
				changesetCollectionId);

		if (changesetCollection == null) {
			throw new SystemException(
				"Unable to checkout changeset collection with id " +
					changesetCollectionId);
		}

		long userId = PrincipalThreadLocal.getUserId();

		User user = _userLocalService.fetchUser(userId);

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				userId, !user.isDefaultUser());

		portalPreferences.setValue(
			ChangesetPortletKeys.CHANGESET_ADMIN,
			_RECENT_CHANGESET_COLLECTION_ID,
			String.valueOf(changesetCollectionId));

		ServiceContext serviceContext =
			ServiceContextThreadLocal.popServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();
		}

		serviceContext.setAttribute(
			_CHANGESET_COLLECTION_ID_ATTRIBUTE, changesetCollectionId);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
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

	public Optional<ChangesetCollection> createAndCheckout(
		String name, String description) {

		Optional<ChangesetCollection> changesetCollectionOptional = create(
			name, description);

		checkout(
			changesetCollectionOptional.map(
				ChangesetCollection::getChangesetCollectionId
			).get());

		return changesetCollectionOptional;
	}

	@Override
	public void disableChangesets() {
		if (!isChangesetEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to disable changesets because it is not enabled " +
						"currently");
			}

			return;
		}

		_changesetBaselineManager.removeAllBaselines();

		List<ChangesetCollection> changesetCollections =
			_changesetCollectionLocalService.getChangesetCollections(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (ChangesetCollection changesetCollection : changesetCollections) {
			_changesetCollectionLocalService.deleteChangesetCollection(
				changesetCollection);
		}
	}

	@Override
	public void enableChangesets() {
		if (isChangesetEnabled()) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to enable changesets because it is already " +
						"enabled");
			}

			return;
		}

		_changesetBaselineManager.createBaseline(
			() -> ChangesetConstants.PRODUCTION_BASELINE_NAME);
	}

	@Override
	public Optional<ChangesetBaselineCollection> getChangesetBaselineCollection(
		long changesetCollectionId) {

		if (ChangesetConstants.PRODUCTION_BASELINE_COLLECTION_ID ==
				changesetCollectionId) {

			return _changesetBaselineManager.getProductionBaseline();
		}

		ChangesetCollection changesetCollection =
			_changesetCollectionLocalService.fetchChangesetCollection(
				changesetCollectionId);

		if (changesetCollection == null) {
			return Optional.empty();
		}

		return _changesetBaselineManager.getChangesetBaselineCollection(
			changesetCollection::getName);
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
	public Optional<ChangesetEntry> getChangesetEntry(long changesetEntryId) {
		return Optional.ofNullable(
			_changesetEntryLocalService.fetchChangesetEntry(changesetEntryId));
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
	public Optional<Long> getCurrentChangesetCollectionId() {
		long userId = PrincipalThreadLocal.getUserId();

		User user = _userLocalService.fetchUser(userId);

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				userId, !user.isDefaultUser());

		long recentChangesetCollectionId = GetterUtil.getLong(
			portalPreferences.getValue(
				ChangesetPortletKeys.CHANGESET_ADMIN,
				_RECENT_CHANGESET_COLLECTION_ID));

		if (recentChangesetCollectionId == 0L) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			recentChangesetCollectionId = GetterUtil.getLong(
				serviceContext.getAttribute(
					_CHANGESET_COLLECTION_ID_ATTRIBUTE));
		}

		return Optional.of(recentChangesetCollectionId);
	}

	@Override
	public Optional<ChangesetEntry> getLatestChangesetEntry(
		long changesetCollectionId, long resourcePrimKey) {

		ChangesetEntry latestChangesetEntry =
			_changesetEntryLocalService.fetchLatestChangesetEntry(
				changesetCollectionId, resourcePrimKey);

		return Optional.ofNullable(latestChangesetEntry);
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
		List<ChangesetEntry> changesetEntries = getChangesetEntries(
			changesetCollectionId);

		changesetEntries.forEach(this::_publishChangesetEntry);
	}

	@Override
	public void remove(long changesetCollectionId) {
	}

	@Override
	public void rollback(long changesetCollectionId) {
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

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		unbind = "_removeChangesetConfigurationRegistrar"
	)
	private void _addChangesetConfigurationRegistrar(
		ChangesetConfigurationRegistrar<?, ?> changesetConfigurationRegistrar) {

		ChangesetConfiguration<?, ?> changesetConfiguration =
			changesetConfigurationRegistrar.changesetConfiguration(
				new ChangesetConfigurationImpl.BuilderImpl<>());

		_addChangesetConfiguration(changesetConfiguration);

		_wrapIndexer(changesetConfiguration);
	}

	private void _publishChangesetEntry(ChangesetEntry changesetEntry) {
		Optional<ChangesetBaselineCollection> productionBaselineOptional =
			_changesetBaselineManager.getProductionBaseline();

		long productionChangesetBaselineCollectionId =
			productionBaselineOptional.map(
				ChangesetBaselineCollection::getChangesetBaselineCollectionId
			).orElseThrow(
				() -> new IllegalStateException(
					"Unable to determine production baseline")
			);

		_changesetBaselineEntryLocalService.addChangesetBaselineEntry(
			productionChangesetBaselineCollectionId,
			changesetEntry.getClassNameId(), changesetEntry.getClassPK(),
			changesetEntry.getResourcePrimKey(), 1.0);

		// TODO What version should we set here? Is it needed at all?

		String resourceEntityClassName =
			getChangesetConfigurationByVersionClassName(
				changesetEntry.getClassName()
			).map(
				ChangesetConfiguration::getResourceEntityClass
			).map(
				Class::getName
			).get();

		// TODO Add resourceClassNameId field to ChangesetEntry to avoid the
		// above call

		_changesetCQRSManager.disableCQRSRepository();

		ChangesetIndexingUtil.index(
			CompanyThreadLocal.getCompanyId(),
			ChangesetConstants.PRODUCTION_BASELINE_COLLECTION_ID,
			changesetEntry.getChangesetEntryId(), resourceEntityClassName,
			changesetEntry.getResourcePrimKey());

		_changesetCQRSManager.enableCQRSRepository();
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

	private void _removeChangesetConfigurationRegistrar(
		ChangesetConfigurationRegistrar<?, ?> changesetConfigurationRegistrar) {

		ChangesetConfiguration<?, ?> changesetConfiguration =
			changesetConfigurationRegistrar.changesetConfiguration(
				new ChangesetConfigurationImpl.BuilderImpl<>());

		_removeChangesetConfiguration(changesetConfiguration);
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

		// todo: it won't work this way, indexer class name will be the same if
		// we move forward with wrapping

		properties.put("indexer.class.name", indexerClassName);

		bundleContext.registerService(
			IndexerPostProcessor.class, _CHANGESET_INDEXER_POST_PROCESSOR,
			properties);
	}

	private static final String _CHANGESET_COLLECTION_ID_ATTRIBUTE =
		"changesetCollectionId";

	private static final ChangesetIndexerPostProcessor
		_CHANGESET_INDEXER_POST_PROCESSOR = new ChangesetIndexerPostProcessor();

	private static final String _RECENT_CHANGESET_COLLECTION_ID =
		"recentChangesetCollectionId";

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
	private ChangesetCQRSManager _changesetCQRSManager;

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

	@Reference
	private PortalPreferencesLocalService _portalPreferencesLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
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
import com.liferay.changeset.manager.ChangesetManager;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = ChangesetManager.class)
public class ChangesetManagerImpl implements ChangesetManager {

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

	private final Map<String, ChangesetConfiguration<?, ?>>
		_configurationsByIdentifier = new HashMap<>();
	private final Map<Class<?>, ChangesetConfiguration<?, ?>>
		_configurationsByResourceClass = new HashMap<>();
	private final Map<Class<?>, ChangesetConfiguration<?, ?>>
		_configurationsByVersionClass = new HashMap<>();

}
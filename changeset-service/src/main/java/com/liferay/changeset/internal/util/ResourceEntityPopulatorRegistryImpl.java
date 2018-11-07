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

import com.liferay.changeset.util.ResourceEntityPopulator;
import com.liferay.changeset.util.ResourceEntityPopulatorRegistry;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Gergely Mathe
 */
@Component(immediate = true, service = ResourceEntityPopulatorRegistry.class)
public class ResourceEntityPopulatorRegistryImpl
	implements ResourceEntityPopulatorRegistry {

	@Deactivate
	public void deactivate() {
		_resourceEntityPopulatorServiceTracker.close();
	}

	@Override
	public <T, U> ResourceEntityPopulator<T, U> getResourceEntityPopulator(
		Class<T> resourceEntityClass) {

		return getResourceEntityPopulator(resourceEntityClass.getName());
	}

	@Override
	public <T, U> ResourceEntityPopulator<T, U> getResourceEntityPopulator(
		String resourceEntityClassName) {

		return (ResourceEntityPopulator<T, U>)_resourceEntityPopulators.get(
			resourceEntityClassName);
	}

	@Override
	public Set<ResourceEntityPopulator> getResourceEntityPopulators() {
		return new HashSet<>(_resourceEntityPopulators.values());
	}

	@Override
	public void register(
		String resourceEntityClassName,
		ResourceEntityPopulator<?, ?> resourceEntityPopulator) {

		_resourceEntityPopulators.put(
			resourceEntityClassName, resourceEntityPopulator);
	}

	@Override
	public void unregister(String resourceEntityClassName) {
		_resourceEntityPopulators.remove(resourceEntityClassName);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		unbind = "_removeResourceEntityPopulator"
	)
	private void _addResourceEntityPopulator(
		ResourceEntityPopulator<?, ?> resourceEntityPopulator) {

		_resourceEntityPopulators.put(
			resourceEntityPopulator.getResourceEntityClassName(),
			resourceEntityPopulator);
	}

	private void _removeResourceEntityPopulator(
		ResourceEntityPopulator<?, ?> resourceEntityPopulator) {

		_resourceEntityPopulators.remove(
			resourceEntityPopulator.getResourceEntityClassName());
	}

	private final Map<String, ResourceEntityPopulator<?, ?>>
		_resourceEntityPopulators = new ConcurrentHashMap<>();
	private ServiceTracker
		<ResourceEntityPopulator<?, ?>, ResourceEntityPopulator<?, ?>>
			_resourceEntityPopulatorServiceTracker;

}
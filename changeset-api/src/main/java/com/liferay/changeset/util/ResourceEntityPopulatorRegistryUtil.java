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

package com.liferay.changeset.util;

import com.liferay.portal.kernel.model.BaseModel;

import java.util.Set;

/**
 * @author Gergely Mathe
 */
public class ResourceEntityPopulatorRegistryUtil {

	public static <T extends BaseModel, U extends BaseModel>
		ResourceEntityPopulator<T, U>
			getResourceEntityPopulator(Class<T> resourceEntityClass) {

		return _resourceEntityPopulatorRegistry.getResourceEntityPopulator(
			resourceEntityClass);
	}

	public static <T extends BaseModel, U extends BaseModel>
		ResourceEntityPopulator<T, U>
			getResourceEntityPopulator(String resourceEntityClassName) {

		return _resourceEntityPopulatorRegistry.getResourceEntityPopulator(
			resourceEntityClassName);
	}

	public static Set<ResourceEntityPopulator> getResourceEntityPopulators() {
		return _resourceEntityPopulatorRegistry.getResourceEntityPopulators();
	}

	public static void register(
		String resourceEntityClassName,
		ResourceEntityPopulator<?, ?> resourceEntityPopulator) {

		_resourceEntityPopulatorRegistry.register(
			resourceEntityClassName, resourceEntityPopulator);
	}

	public static void setResourceEntityPopulatorRegistry(
		ResourceEntityPopulatorRegistry resourceEntityPopulatorRegistry) {

		_resourceEntityPopulatorRegistry = resourceEntityPopulatorRegistry;
	}

	public static void unregister(String resourceEntityClassName) {
		_resourceEntityPopulatorRegistry.unregister(resourceEntityClassName);
	}

	private static ResourceEntityPopulatorRegistry
		_resourceEntityPopulatorRegistry;

}
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

package com.liferay.changeset.cqrs.search;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.Set;

/**
 * @author Daniel Kocsis
 */
public class DocumentModelMapperRegistryUtil {

	public static <T> DocumentModelMapper<T> getDocumentModelMapper(
		Class<T> clazz) {

		return _documentModelMapperRegistry.getDocumentModelMapper(clazz);
	}

	public static <T> DocumentModelMapper<T> getDocumentModelMapper(
		String className) {

		return _documentModelMapperRegistry.getDocumentModelMapper(className);
	}

	public static Set<DocumentModelMapper> getDocumentModelMappers() {
		return _documentModelMapperRegistry.getDocumentModelMappers();
	}

	public static void register(
		String className, DocumentModelMapper<?> documentModelMapper) {

		_documentModelMapperRegistry.register(className, documentModelMapper);
	}

	public static void unregister(String className) {
		_documentModelMapperRegistry.unregister(className);
	}

	private static volatile DocumentModelMapperRegistry
		_documentModelMapperRegistry =
			ServiceProxyFactory.newServiceTrackedInstance(
				DocumentModelMapperRegistry.class,
				DocumentModelMapperRegistry.class,
				"_documentModelMapperRegistry", false);

}
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

package com.liferay.changeset.cqrs.internal.search;

import com.liferay.changeset.cqrs.search.DocumentModelMapper;
import com.liferay.changeset.cqrs.search.DocumentModelMapperRegistry;

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
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = DocumentModelMapperRegistry.class)
public class DocumentModelMapperRegistryImpl
	implements DocumentModelMapperRegistry {

	@Deactivate
	public void deactivate() {
		_documentModelMapperServiceTracker.close();
	}

	@Override
	public <T> DocumentModelMapper<T> getDocumentModelMapper(Class<T> clazz) {
		return getDocumentModelMapper(clazz.getName());
	}

	@Override
	public <T> DocumentModelMapper<T> getDocumentModelMapper(String className) {
		return (DocumentModelMapper<T>)_documentModelMappers.get(className);
	}

	@Override
	public Set<DocumentModelMapper> getDocumentModelMappers() {
		return new HashSet<>(_documentModelMappers.values());
	}

	@Override
	public void register(
		String className, DocumentModelMapper<?> documentModelMapper) {

		_documentModelMappers.put(className, documentModelMapper);
	}

	@Override
	public void unregister(String className) {
		_documentModelMappers.remove(className);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC, unbind = "_removeDocumentModelMapper"
	)
	private void _addDocumentModelMapper(
		DocumentModelMapper<?> documentModelMapper) {

		_documentModelMappers.put(
			documentModelMapper.getModelClass().getName(), documentModelMapper);
	}

	private void _removeDocumentModelMapper(
		DocumentModelMapper<?> documentModelMapper) {

		_documentModelMappers.remove(
			documentModelMapper.getModelClass().getName());
	}

	private final Map<String, DocumentModelMapper<?>> _documentModelMappers =
		new ConcurrentHashMap<>();
	private ServiceTracker<DocumentModelMapper<?>, DocumentModelMapper<?>>
		_documentModelMapperServiceTracker;

}
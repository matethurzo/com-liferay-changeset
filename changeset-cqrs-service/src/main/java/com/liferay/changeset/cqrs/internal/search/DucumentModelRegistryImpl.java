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
import com.liferay.osgi.util.StringPlus;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = DocumentModelMapperRegistry.class)
public class DucumentModelRegistryImpl implements DocumentModelMapperRegistry {

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
	public Set<DocumentModelMapper<?>> getDocumentModelMappers() {
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

	@Activate
	private void _activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_documentModelMapperServiceTracker =
			new ServiceTracker<>(
				bundleContext, DocumentModelMapper.class.getName(),
				new ServiceTrackerCustomizer
					<DocumentModelMapper<?>, DocumentModelMapper<?>>() {

					@Override
					public DocumentModelMapper<?> addingService(
						ServiceReference<DocumentModelMapper<?>>
							serviceReference) {

						DocumentModelMapper<?> documentModelMapper =
							bundleContext.getService(serviceReference);

						List<String> modelClassNames = StringPlus.asList(
							serviceReference.getProperty("model.class.name"));

						modelClassNames.forEach(
							modelClassName -> _documentModelMappers.put(
								modelClassName, documentModelMapper));

						return documentModelMapper;
					}

					@Override
					public void modifiedService(
						ServiceReference<DocumentModelMapper<?>>
							serviceReference,
						DocumentModelMapper<?> indexer) {
					}

					@Override
					public void removedService(
						ServiceReference<DocumentModelMapper<?>>
							serviceReference,
						DocumentModelMapper<?> documentModelMapper) {

						List<String> modelClassNames = StringPlus.asList(
							serviceReference.getProperty("model.class.name"));

						modelClassNames.forEach(_documentModelMappers::remove);

						bundleContext.ungetService(serviceReference);
					}

				});

		_documentModelMapperServiceTracker.open();
	}

	private final Map<String, DocumentModelMapper<?>> _documentModelMappers =
		new ConcurrentHashMap<>();
	private ServiceTracker<DocumentModelMapper<?>, DocumentModelMapper<?>>
		_documentModelMapperServiceTracker;

}
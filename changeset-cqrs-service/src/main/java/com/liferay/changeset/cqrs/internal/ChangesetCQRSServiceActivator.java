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

package com.liferay.changeset.cqrs.internal;

import com.liferay.changeset.cqrs.search.DocumentModelMapperRegistry;
import com.liferay.changeset.cqrs.search.DocumentModelMapperRegistryUtil;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Mate Thurzo
 */
public class ChangesetCQRSServiceActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_serviceTracker =
			new ServiceTracker
				<DocumentModelMapperRegistry, DocumentModelMapperRegistry>(
					bundleContext, DocumentModelMapperRegistry.class.getName(),
					null) {

				@Override
				public DocumentModelMapperRegistry addingService(
					ServiceReference<DocumentModelMapperRegistry>
						serviceReference) {

					DocumentModelMapperRegistry documentModelMapperRegistry =
						bundleContext.getService(serviceReference);

					DocumentModelMapperRegistryUtil.
						setDocumentModelMapperRegistry(
							documentModelMapperRegistry);

					return documentModelMapperRegistry;
				}

			};

		_serviceTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		_serviceTracker.close();
	}

	private ServiceTracker
		<DocumentModelMapperRegistry, DocumentModelMapperRegistry>
			_serviceTracker;

}
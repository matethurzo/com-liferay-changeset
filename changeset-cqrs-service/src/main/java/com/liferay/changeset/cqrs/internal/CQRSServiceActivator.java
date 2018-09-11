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

import com.liferay.changeset.configuration.ChangesetConfiguration;
import com.liferay.changeset.cqrs.internal.search.ChangesetAwareIndexer;
import com.liferay.portal.kernel.search.Indexer;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Daniel Kocsis
 */
public class CQRSServiceActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_changesetConfigurationServiceTracker =
			new ServiceTracker<ChangesetConfiguration, ChangesetConfiguration>(
				bundleContext, ChangesetConfiguration.class.getName(), null) {

				@Override
				public ChangesetConfiguration addingService(
					ServiceReference<ChangesetConfiguration> serviceReference) {

					ChangesetConfiguration changesetConfiguration =
						bundleContext.getService(serviceReference);

					Indexer indexer = changesetConfiguration.getIndexer();

					if (indexer == null) {
						return changesetConfiguration;
					}

					bundleContext.registerService(
						Indexer.class, new ChangesetAwareIndexer(indexer),
						new Hashtable<>());

					return changesetConfiguration;
				}

				@Override
				public void modifiedService(
					ServiceReference<ChangesetConfiguration> serviceReference,
					ChangesetConfiguration changesetConfiguration) {

					super.modifiedService(
						serviceReference, changesetConfiguration);
				}

				@Override
				public void removedService(
					ServiceReference<ChangesetConfiguration> serviceReference,
					ChangesetConfiguration changesetConfiguration) {

					super.removedService(
						serviceReference, changesetConfiguration);
				}

			};

		_changesetConfigurationServiceTracker.open();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		_changesetConfigurationServiceTracker.close();
	}

	private ServiceTracker<ChangesetConfiguration, ChangesetConfiguration>
		_changesetConfigurationServiceTracker;

}
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

package com.liferay.changeset.internal;

import com.liferay.changeset.configuration.ChangesetConfiguration;
import com.liferay.changeset.internal.search.ChangesetIndexerPostProcessor;
import com.liferay.changeset.manager.ChangesetManager;
import com.liferay.changeset.manager.ChangesetManagerUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Mate Thurzo
 */
public class ChangesetServiceActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_serviceTracker =
			new ServiceTracker<ChangesetManager, ChangesetManager>(
				bundleContext, ChangesetManager.class.getName(), null) {

				@Override
				public ChangesetManager addingService(
					ServiceReference<ChangesetManager> serviceReference) {

					ChangesetManager changesetManager =
						bundleContext.getService(serviceReference);

					ChangesetManagerUtil.setChangesetManager(changesetManager);

					return changesetManager;
				}

			};

		_serviceTracker.open();

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

					String indexerClassName = indexer.getClassName();

					Dictionary<String, Object> properties = new Hashtable<>();

					properties.put("indexer.class.name", indexerClassName);

					bundleContext.registerService(
						IndexerPostProcessor.class,
						new ChangesetIndexerPostProcessor(), properties);

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
	public void stop(BundleContext bundleContext) throws Exception {
		_serviceTracker.close();

		_changesetConfigurationServiceTracker.close();
	}

	private ServiceTracker<ChangesetConfiguration, ChangesetConfiguration>
		_changesetConfigurationServiceTracker;
	private ServiceTracker<ChangesetManager, ChangesetManager> _serviceTracker;

}
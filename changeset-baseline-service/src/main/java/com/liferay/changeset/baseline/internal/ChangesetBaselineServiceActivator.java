/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.changeset.baseline.internal;

import com.liferay.changeset.baseline.manager.ChangesetBaselineManager;
import com.liferay.changeset.baseline.manager.ChangesetBaselineManagerUtil;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Mate Thurzo
 */
public class ChangesetBaselineServiceActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_serviceTracker =
			new ServiceTracker
				<ChangesetBaselineManager, ChangesetBaselineManager>(
					bundleContext, ChangesetBaselineManager.class.getName(),
					null) {

				@Override
				public ChangesetBaselineManager addingService(
					ServiceReference<ChangesetBaselineManager>
					serviceReference) {

					ChangesetBaselineManager changesetManager =
						bundleContext.getService(serviceReference);

					ChangesetBaselineManagerUtil.setChangesetBaselineManager(
						changesetManager);

					return changesetManager;
				}

			};

		_serviceTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		_serviceTracker.close();
	}

	private ServiceTracker<ChangesetBaselineManager, ChangesetBaselineManager>
		_serviceTracker;

}
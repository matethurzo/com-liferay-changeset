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

package com.liferay.changeset.playground.internal;

import com.liferay.changeset.manager.ChangesetManager;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class ChangesetPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {

		// todo: changeset should be added as well and its ID must be out into the ServiceContext

		if (!_changesetManager.isChangesetEnabled()) {

			// todo: re-enable if needed

			// _changesetManager.enableChangesets();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Changeset production baseline is successfully created");
			}
		}
	}

	@Override
	public void portalInstanceUnregistered(Company company) throws Exception {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ChangesetPortalInstanceLifecycleListener.class);

	@Reference
	private ChangesetManager _changesetManager;

}
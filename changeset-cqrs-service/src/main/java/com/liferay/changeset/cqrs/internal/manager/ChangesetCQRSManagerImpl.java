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

package com.liferay.changeset.cqrs.internal.manager;

import com.liferay.changeset.constants.ChangesetConstants;
import com.liferay.changeset.cqrs.manager.ChangesetCQRSManager;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gergely Mathe
 */
@Component(immediate = true, service = ChangesetCQRSManager.class)
public class ChangesetCQRSManagerImpl implements ChangesetCQRSManager {

	public void disableCQRSRepository() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.popServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();
		}

		serviceContext.setAttribute(
			ChangesetConstants.CQRS_REPOSITORY_ENABLED, Boolean.FALSE);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
	}

	public void enableCQRSRepository() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.popServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();
		}

		serviceContext.setAttribute(
			ChangesetConstants.CQRS_REPOSITORY_ENABLED, Boolean.TRUE);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
	}

	public boolean isCQRSRepositoryEnabled() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return true;
		}

		return GetterUtil.getBoolean(
			serviceContext.getAttribute(
				ChangesetConstants.CQRS_REPOSITORY_ENABLED),
			true);
	}

}
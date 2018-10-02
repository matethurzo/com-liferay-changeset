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

package com.liferay.changeset.service;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Daniel Kocsis
 */
public class ChangesetAwareServiceContext extends ServiceContext {

	public ChangesetAwareServiceContext(ServiceContext serviceContext) {
		if (serviceContext == null) {
			_serviceContext = new ServiceContext();
		}
		else if (serviceContext instanceof ChangesetAwareServiceContext) {
			ChangesetAwareServiceContext changesetAwareServiceContext =
				(ChangesetAwareServiceContext)serviceContext;

			_serviceContext = changesetAwareServiceContext._serviceContext;
		}
		else {
			_serviceContext = serviceContext;
		}
	}

	public long getChangesetCollectionId() {
		return GetterUtil.getLong(
			_serviceContext.getAttribute(_CHANGESET_COLLECTION_ID_ATTRIBUTE));
	}

	public void setChangesetCollectionId(long changesetCollectionId) {
		_serviceContext.setAttribute(
			_CHANGESET_COLLECTION_ID_ATTRIBUTE, changesetCollectionId);
	}

	private static final String _CHANGESET_COLLECTION_ID_ATTRIBUTE =
		"changesetCollectionId";

	private final ServiceContext _serviceContext;

}
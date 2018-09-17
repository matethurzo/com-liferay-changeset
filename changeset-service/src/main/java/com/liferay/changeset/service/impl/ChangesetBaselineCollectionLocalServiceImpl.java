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

package com.liferay.changeset.service.impl;

import com.liferay.changeset.model.ChangesetBaselineCollection;
import com.liferay.changeset.service.base.ChangesetBaselineCollectionLocalServiceBaseImpl;
import com.liferay.portal.kernel.model.User;

import java.util.Date;
import java.util.Optional;

/**
 * The implementation of the changeset baseline collection local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.changeset.service.ChangesetBaselineCollectionLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetBaselineCollectionLocalServiceBaseImpl
 * @see com.liferay.changeset.service.ChangesetBaselineCollectionLocalServiceUtil
 */
public class ChangesetBaselineCollectionLocalServiceImpl
	extends ChangesetBaselineCollectionLocalServiceBaseImpl {

	@Override
	public ChangesetBaselineCollection addChangesetBaselineCollection(
		long userId, String name) {

		User user = userLocalService.fetchUser(userId);

		ChangesetBaselineCollection baselineInformation =
			changesetBaselineCollectionPersistence.create(
				counterLocalService.increment());

		Date now = new Date();

		baselineInformation.setCompanyId(user.getCompanyId());
		baselineInformation.setUserId(userId);
		baselineInformation.setUserName(user.getFullName());
		baselineInformation.setCreateDate(now);
		baselineInformation.setModifiedDate(now);
		baselineInformation.setName(name);

		changesetBaselineCollectionPersistence.update(baselineInformation);

		return baselineInformation;
	}

	@Override
	public Optional<ChangesetBaselineCollection>
		getChangesetBaselineCollectionByName(String name) {

		return Optional.ofNullable(
			changesetBaselineCollectionPersistence.fetchByName(name));
	}

}
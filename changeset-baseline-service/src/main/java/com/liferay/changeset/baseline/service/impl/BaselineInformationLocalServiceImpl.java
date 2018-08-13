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

package com.liferay.changeset.baseline.service.impl;

import com.liferay.changeset.baseline.model.BaselineInformation;
import com.liferay.changeset.baseline.service.base.BaselineInformationLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.util.Date;
import java.util.Optional;

/**
 * The implementation of the baseline information local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.changeset.baseline.service.BaselineInformationLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BaselineInformationLocalServiceBaseImpl
 * @see com.liferay.changeset.baseline.service.BaselineInformationLocalServiceUtil
 */
public class BaselineInformationLocalServiceImpl
	extends BaselineInformationLocalServiceBaseImpl {

	@Override
	public BaselineInformation addBaselineInformation(
		long userId, String name) {

		User user = userLocalService.fetchUser(userId);

		BaselineInformation baselineInformation =
			baselineInformationPersistence.create(
				counterLocalService.increment());

		Date now = new Date();

		baselineInformation.setCompanyId(user.getCompanyId());
		baselineInformation.setUserId(userId);
		baselineInformation.setUserName(user.getFullName());
		baselineInformation.setCreateDate(now);
		baselineInformation.setModifiedDate(now);
		baselineInformation.setName(name);

		baselineInformationPersistence.update(baselineInformation);

		return baselineInformation;
	}

	public Optional<BaselineInformation> getBaseLineInformationByName(
		String name) {

		try {
			return Optional.ofNullable(
				baselineInformationPersistence.findByName(name));
		}
		catch (PortalException pe) {
			pe.printStackTrace();

			return Optional.empty();
		}
	}

}
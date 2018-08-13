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

import com.liferay.changeset.baseline.model.BaselineEntry;
import com.liferay.changeset.baseline.model.BaselineInformation;
import com.liferay.changeset.baseline.service.base.BaselineEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.model.User;

import java.util.Date;

/**
 * The implementation of the baseline entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.changeset.baseline.service.BaselineEntryLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BaselineEntryLocalServiceBaseImpl
 * @see com.liferay.changeset.baseline.service.BaselineEntryLocalServiceUtil
 */
public class BaselineEntryLocalServiceImpl
	extends BaselineEntryLocalServiceBaseImpl {

	@Override
	public BaselineEntry addBaselineEntry(
		long baselineInformationId, long classNameId, long classPK,
		double version) {

		BaselineInformation baselineInformation =
			baselineInformationLocalService.fetchBaselineInformation(
				baselineInformationId);

		User user = userLocalService.fetchUser(baselineInformation.getUserId());

		BaselineEntry baselineEntry = baselineEntryPersistence.create(
			counterLocalService.increment());

		baselineEntry.setBaselineInformationId(baselineInformationId);
		baselineEntry.setCompanyId(user.getCompanyId());
		baselineEntry.setUserId(user.getUserId());
		baselineEntry.setUserName(user.getFullName());

		Date now = new Date();

		baselineEntry.setCreateDate(now);
		baselineEntry.setModifiedDate(now);

		baselineEntry.setClassNameId(classNameId);
		baselineEntry.setClassPK(classPK);
		baselineEntry.setVersion(version);

		baselineEntryPersistence.update(baselineEntry);

		return baselineEntry;
	}

}
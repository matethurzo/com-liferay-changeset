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
import com.liferay.changeset.model.ChangesetBaselineEntry;
import com.liferay.changeset.service.base.ChangesetBaselineEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.model.User;

import java.util.Date;
import java.util.List;

/**
 * The implementation of the changeset baseline entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.changeset.service.ChangesetBaselineEntryLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetBaselineEntryLocalServiceBaseImpl
 * @see com.liferay.changeset.service.ChangesetBaselineEntryLocalServiceUtil
 */
public class ChangesetBaselineEntryLocalServiceImpl
	extends ChangesetBaselineEntryLocalServiceBaseImpl {

	@Override
	public ChangesetBaselineEntry addChangesetBaselineEntry(
		long changesetBaselineCollectionId, long classNameId, long classPK,
		double version) {

		ChangesetBaselineCollection changesetBaselineCollection =
			changesetBaselineCollectionLocalService.
				fetchChangesetBaselineCollection(changesetBaselineCollectionId);

		User user = userLocalService.fetchUser(
			changesetBaselineCollection.getUserId());

		ChangesetBaselineEntry changesetBaselineEntry =
			changesetBaselineEntryPersistence.create(
				counterLocalService.increment());

		changesetBaselineEntry.setChangesetBaselineCollectionId(
			changesetBaselineCollectionId);
		changesetBaselineEntry.setCompanyId(user.getCompanyId());
		changesetBaselineEntry.setUserId(user.getUserId());
		changesetBaselineEntry.setUserName(user.getFullName());

		Date now = new Date();

		changesetBaselineEntry.setCreateDate(now);
		changesetBaselineEntry.setModifiedDate(now);

		changesetBaselineEntry.setClassNameId(classNameId);
		changesetBaselineEntry.setClassPK(classPK);
		changesetBaselineEntry.setVersion(version);

		changesetBaselineEntryPersistence.update(changesetBaselineEntry);

		return changesetBaselineEntry;
	}

	@Override
	public void deleteChangesetBaselineEntries(
		long changesetBaselineCollectionId) {

		changesetBaselineEntryPersistence.removeByChangesetBaselineCollectionId(
			changesetBaselineCollectionId);
	}

	@Override
	public List<ChangesetBaselineEntry> getChangesetBaselineEntries(
		long changesetBaselineCollectionId) {

		return changesetBaselineEntryPersistence.
			findByChangesetBaselineCollectionId(changesetBaselineCollectionId);
	}

}
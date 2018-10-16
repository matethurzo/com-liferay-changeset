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

import com.liferay.changeset.configuration.ChangesetConfiguration;
import com.liferay.changeset.configuration.ChangesetConfigurationRegistrar;
import com.liferay.changeset.cqrs.manager.ChangesetCQRSManager;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntryVersion;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntryVersionModel;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Indexer;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = ChangesetConfigurationRegistrar.class)
public class CommerceUserSegmentConfigurationRegistrar
	implements ChangesetConfigurationRegistrar
		<CommerceUserSegmentEntry, CommerceUserSegmentEntryVersion> {

	@Override
	public ChangesetConfiguration changesetConfiguration(
		ChangesetConfiguration.Builder
			<CommerceUserSegmentEntry, CommerceUserSegmentEntryVersion>
				builder) {

		return builder.identifier(
			"commerce-user-segment"
		).addResourceEntity(
			CommerceUserSegmentEntry.class,
			CommerceUserSegmentEntry::getCommerceUserSegmentEntryId,
			CommerceUserSegmentEntry::getVersionId,
			_commerceUserSegmentEntryLocalService
		).addVersionEntity(
			CommerceUserSegmentEntryVersion.class,
			CommerceUserSegmentEntryVersion::getCommerceUserSegmentEntryId,
			CommerceUserSegmentEntryVersion::
				getCommerceUserSegmentEntryVersionId,
			CommerceUserSegmentEntryVersionModel::getVersion, null
		).baselining(

			// TODO extract this to some helper

			() -> {
			List<CommerceUserSegmentEntryVersion>
				commerceUserSegmentEntryVersions = new ArrayList<>();

			_changesetCQRSManager.disableCQRSRepository();

			List<CommerceUserSegmentEntry> commerceUserSegmentEntries =
				_commerceUserSegmentEntryLocalService.
					getCommerceUserSegmentEntries(
						QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			commerceUserSegmentEntries.forEach(
				commerceUserSegmentEntry ->
					commerceUserSegmentEntryVersions.add(
						_commerceUserSegmentEntryLocalService.getLatestVersion(
							commerceUserSegmentEntry)));

			_changesetCQRSManager.enableCQRSRepository();

			return commerceUserSegmentEntryVersions;
		}
		).indexer(
			clazz -> _commerceUserSegmentEntryIndexer
		).build();
	}

	@Reference
	private ChangesetCQRSManager _changesetCQRSManager;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.user.segment.internal.search.CommerceUserSegmentEntryIndexer)"
	)
	private Indexer _commerceUserSegmentEntryIndexer;

	@Reference
	private CommerceUserSegmentEntryLocalService
		_commerceUserSegmentEntryLocalService;

}
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
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalService;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;

import java.util.Random;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
//@Component(immediate = true, service = ChangesetConfigurationRegistrar.class)
//public class CommerceUserSegmentConfigurationRegistrar
//	implements ChangesetConfigurationRegistrar
//		<CommerceUserSegmentEntry, CommerceUserSegmentEntryVersion> {
//
//	@Override
//	public ChangesetConfiguration changesetConfiguration(
//		ChangesetConfiguration.Builder
//			<CommerceUserSegmentEntry, CommerceUserSegmentEntryVersion>
//				 builder) {
//
//		return builder.identifier(
//			"commerce-user-segment"
//		).addResourceEntity(
//			CommerceUserSegmentEntry.class,
//			CommerceUserSegmentEntry::getCommerceUserSegmentEntryId,
//			_commerceUserSegmentEntryLocalService
//		).addVersionEntity(
//			CommerceUserSegmentEntryVersion.class,
//			CommerceUserSegmentEntryVersion::getCommerceUserSegmentEntryId,
//			CommerceUserSegmentEntryVersionModel::getVersion,
//			_commerceUserSegmentEntryLocalService
//		).baselining( //			() ->

// _commerceUserSegmentEntryVersionPersistence.findAll()

//		).indexer(
//			IndexerRegistryUtil::getIndexer
//		).build();
//	}
//
//	@Reference
//	private CommerceUserSegmentEntryLocalService
//		_commerceUserSegmentEntryLocalService;
//
//	@Reference
//	private CommerceUserSegmentEntryVersionPersistence
//		_commerceUserSegmentEntryVersionPersistence;
//
//}
@Component(immediate = true, service = ChangesetConfigurationRegistrar.class)
public class CommerceUserSegmentConfigurationRegistrar
	implements ChangesetConfigurationRegistrar
		<CommerceUserSegmentEntry, CommerceUserSegmentEntry> {

	// TODO Modify this to use CommerceUserSegmentEntryVersion, it fails for some reason right now

	@Override
	public ChangesetConfiguration changesetConfiguration(
		ChangesetConfiguration.Builder
			<CommerceUserSegmentEntry, CommerceUserSegmentEntry>
				builder) {

		return builder.identifier(
			"commerce-user-segment"
		).addResourceEntity(
			CommerceUserSegmentEntry.class,
			CommerceUserSegmentEntry::getCommerceUserSegmentEntryId,
			_commerceUserSegmentEntryLocalService
		).addVersionEntity(
			CommerceUserSegmentEntry.class,
			CommerceUserSegmentEntry::getCommerceUserSegmentEntryId,
			CommerceUserSegmentEntry::getCommerceUserSegmentEntryId,
			commerceUserSegmentEntry -> {
				Random random = new Random();

				return random.nextDouble();
			},
			_commerceUserSegmentEntryLocalService
		).baselining(
			() ->
				_commerceUserSegmentEntryLocalService.
					getCommerceUserSegmentEntries(-1, -1)
		).indexer(
			IndexerRegistryUtil::getIndexer
		).build();
	}

	@Reference
	private CommerceUserSegmentEntryLocalService
		_commerceUserSegmentEntryLocalService;

}
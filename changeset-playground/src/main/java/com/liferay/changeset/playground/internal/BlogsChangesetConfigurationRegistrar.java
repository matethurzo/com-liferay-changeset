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

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.changeset.configuration.ChangesetConfiguration;
import com.liferay.changeset.configuration.ChangesetConfigurationRegistrar;
import com.liferay.changeset.cqrs.manager.ChangesetCQRSManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Indexer;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

// TODO Replace at marked places, once versioning code is created

/**
 * @author Gergely Mathe
 */
@Component(immediate = true, service = ChangesetConfigurationRegistrar.class)
public class BlogsChangesetConfigurationRegistrar
	implements ChangesetConfigurationRegistrar<BlogsEntry, BlogsEntry> {

	@Override
	public ChangesetConfiguration changesetConfiguration(
		ChangesetConfiguration.Builder<BlogsEntry, BlogsEntry> builder) { // TODO Replace with <BlogsEntry, BlogsEntryVersion>

		return builder.identifier(
			"blogs"
		).addResourceEntity(
			BlogsEntry.class,
			BlogsEntry::getEntryId,
			BlogsEntry::getEntryId, // TODO Replace with BlogsEntry::getVersionId
			_blogsEntryLocalService
		).addVersionEntity(
			BlogsEntry.class, // TODO Replace with BlogsEntryVersion.class
			blogsEntry -> 0L, // TODO Replace with BlogsEntryVersion::getEntryId
			blogsEntry -> 0L, // TODO Replace with BlogsEntryVersion::getVersionId
			blogsEntry -> 1.0D, // TODO Replace with BlogsEntryVersion::getVersion
			null
		).baselining(
			() -> {
				_changesetCQRSManager.disableCQRSRepository();

				List<BlogsEntry> blogsEntries =
					_blogsEntryLocalService.getBlogsEntries(
						QueryUtil.ALL_POS, QueryUtil.ALL_POS); // TODO Extend with fetching and returning latest versions

				_changesetCQRSManager.enableCQRSRepository();

				return blogsEntries;
			}
		).indexer(
			clazz -> _blogsEntryIndexer
		).build();
	}

	@Reference
	private ChangesetCQRSManager _changesetCQRSManager;

	@Reference(
		target = "(model.class.name=com.liferay.blogs.internal.search.BlogsEntryIndexer)"
	)
	private Indexer _blogsEntryIndexer;

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

}

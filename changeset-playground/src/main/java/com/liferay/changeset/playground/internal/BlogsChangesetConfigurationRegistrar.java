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
import com.liferay.blogs.model.BlogsEntryVersion;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.service.persistence.BlogsEntryUtil;
import com.liferay.blogs.service.persistence.BlogsEntryVersionUtil;
import com.liferay.changeset.configuration.ChangesetConfiguration;
import com.liferay.changeset.configuration.ChangesetConfigurationRegistrar;
import com.liferay.changeset.cqrs.manager.ChangesetCQRSManager;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(immediate = true, service = ChangesetConfigurationRegistrar.class)
public class BlogsChangesetConfigurationRegistrar
	implements ChangesetConfigurationRegistrar<BlogsEntry, BlogsEntryVersion> {

	@Override
	public ChangesetConfiguration changesetConfiguration(
		ChangesetConfiguration.Builder<BlogsEntry, BlogsEntryVersion> builder) {

		return builder.identifier(
			"blogs"
		).addResourceEntity(
			BlogsEntry.class, BlogsEntryUtil::fetchByPrimaryKey,
			BlogsEntry::getEntryId, BlogsEntry::getVersionId,
			_blogsEntryLocalService
		).addVersionEntity(
			BlogsEntryVersion.class, BlogsEntryVersion::getEntryId,
			BlogsEntryVersionUtil::fetchByPrimaryKey,
			BlogsEntryVersion::getBlogsEntryVersionId,
			BlogsEntryVersion::getVersion, null,
			new Integer[]
				{
					WorkflowConstants.STATUS_APPROVED,
					WorkflowConstants.STATUS_DRAFT
				},
			BlogsEntryVersion::getStatus
		).baselining(
			() -> {
				_changesetCQRSManager.disableCQRSRepository();

				List<BlogsEntryVersion> blogsEntryVersions =
					_blogsEntryLocalService.getBlogsEntryVersions();

				_changesetCQRSManager.enableCQRSRepository();

				return blogsEntryVersions;
			}
		).indexer(
			clazz -> _blogsEntryIndexer
		).build();
	}

	@Reference(
		target = "(model.class.name=com.liferay.blogs.internal.search.BlogsEntryIndexer)"
	)
	private Indexer _blogsEntryIndexer;

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference
	private ChangesetCQRSManager _changesetCQRSManager;

}
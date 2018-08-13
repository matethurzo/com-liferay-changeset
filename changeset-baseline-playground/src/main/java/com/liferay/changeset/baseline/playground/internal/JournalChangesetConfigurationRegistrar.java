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

package com.liferay.changeset.baseline.playground.internal;

import com.liferay.changeset.configuration.ChangesetConfiguration;
import com.liferay.changeset.configuration.ChangesetConfigurationRegistrar;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = ChangesetConfigurationRegistrar.class)
public class JournalChangesetConfigurationRegistrar
	implements ChangesetConfigurationRegistrar
		<JournalArticleResource, JournalArticle> {

	@Override
	public ChangesetConfiguration changesetConfiguration(
		ChangesetConfiguration.Builder
			<JournalArticleResource, JournalArticle> builder) {

		return builder.identifier(
			"journal"
		).addResourceEntity(
			JournalArticleResource.class,
			JournalArticleResource::getResourcePrimKey,
			_journalArticleResourceLocalService
		).addVersionEntity(
			JournalArticle.class, JournalArticle::getId,
			JournalArticle::getVersion, _journalArticleLocalService
		).baselining(
			_journalArticleLocalService::getArticles
		).indexer(
			IndexerRegistryUtil::getIndexer
		).build();
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

}
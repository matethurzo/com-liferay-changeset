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

package com.liferay.changeset.internal.search;

import com.liferay.changeset.configuration.ChangesetConfiguration;
import com.liferay.changeset.manager.ChangesetManager;
import com.liferay.changeset.manager.ChangesetManagerUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Locale;
import java.util.Optional;

/**
 * @author Daniel Kocsis
 */
public class ChangesetIndexerPostProcessor implements IndexerPostProcessor {

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter booleanFilter, SearchContext searchContext)
		throws Exception {
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {
	}

	@Override
	public void postProcessDocument(Document document, Object object) {
		if (!(object instanceof BaseModel) ||
			!ChangesetIndexingUtil.isRunPostProcessor()) {

			return;
		}

		BaseModel baseModel = (BaseModel)object;

		String entryClassName = baseModel.getModelClassName();

		ChangesetManager changesetManager =
			ChangesetManagerUtil.getChangesetManager();

		Optional<ChangesetConfiguration<?, ?>> changesetConfigurationOptional =
			changesetManager.getChangesetConfigurationByResourceClassName(
				entryClassName);

		if (!changesetConfigurationOptional.isPresent()) {
			return;
		}

		ChangesetConfiguration changesetConfiguration =
			changesetConfigurationOptional.get();

		String versionClassName =
			changesetConfiguration.getVersionEntityClass().getName();

		long versionId =
			(long)changesetConfiguration.
				getVersionEntityIdFromResourceEntityFunction().apply(object);

		Optional<Long> changesetCollectionIdOptional =
			ChangesetIndexingUtil.getChangesetCollectionId(
				versionClassName, versionId);

		if (!changesetCollectionIdOptional.isPresent()) {
			return;
		}

		Optional<Long> changesetEntryIdOptional =
			ChangesetIndexingUtil.getChangesetEntryId(
				versionClassName, versionId);

		if (!changesetEntryIdOptional.isPresent()) {
			return;
		}

		ChangesetIndexingUtil.index(
			CompanyThreadLocal.getCompanyId(),
			changesetCollectionIdOptional.get(), changesetEntryIdOptional.get(),
			baseModel);
	}

	@Override
	public void postProcessFullQuery(
			BooleanQuery fullQuery, SearchContext searchContext)
		throws Exception {
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter booleanFilter,
			SearchContext searchContext)
		throws Exception {

		String entryClassName = GetterUtil.getString(
			searchContext.getAttribute(Field.ENTRY_CLASS_NAME));
		long entryClassPK = GetterUtil.getLong(
			searchContext.getAttribute(Field.ENTRY_CLASS_PK));

		Optional<Long> chanegetCollectionIdOptional =
			ChangesetIndexingUtil.getChangesetCollectionId(
				entryClassName, entryClassPK);

		chanegetCollectionIdOptional.ifPresent(
			chanegetCollectionId -> searchQuery.addRequiredTerm(
				ChangesetIndexingUtil.CHANGESET_COLLECTION_ID_FIELD,
				chanegetCollectionId)
		);

		Optional<Long> changesetIdOptional =
			ChangesetIndexingUtil.getChangesetEntryId(
				entryClassName, entryClassPK);

		changesetIdOptional.ifPresent(
			changesetEntryId -> searchQuery.addRequiredTerm(
				ChangesetIndexingUtil.CHANGESET_ENTRY_ID_FIELD,
				changesetEntryId)
		);
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {
	}

	@Override
	public void postProcessSummary(
		Summary summary, Document document, Locale locale, String snippet) {
	}

}
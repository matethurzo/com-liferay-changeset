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

import com.liferay.changeset.manager.ChangesetManager;
import com.liferay.changeset.manager.ChangesetManagerUtil;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.model.ChangesetEntry;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
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
	public void postProcessDocument(Document document, Object obj) {
		String entryClassName = document.get(Field.ENTRY_CLASS_NAME);
		long entryClassPK = GetterUtil.getLong(
			document.get(Field.ENTRY_CLASS_PK));

		Optional<Long> chanegetCollectionIdOptional = _getChangesetCollectionId(
			entryClassName, entryClassPK);

		chanegetCollectionIdOptional.ifPresent(
			chanegetCollectionId -> document.addKeyword(
				_CHANGESET_COLLECTION_ID_FIELD, chanegetCollectionId)
		);

		Optional<Long> changesetIdOptional = _getChangesetEntryId(
			entryClassName, entryClassPK);

		changesetIdOptional.ifPresent(
			changesetId -> document.addKeyword(
				_CHANGESET_ENTRY_ID_FIELD, changesetId));
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

		Optional<Long> chanegetCollectionIdOptional = _getChangesetCollectionId(
			entryClassName, entryClassPK);

		chanegetCollectionIdOptional.ifPresent(
			chanegetCollectionId -> searchQuery.addRequiredTerm(
				_CHANGESET_COLLECTION_ID_FIELD, chanegetCollectionId)
		);

		Optional<Long> changesetIdOptional = _getChangesetEntryId(
			entryClassName, entryClassPK);

		changesetIdOptional.ifPresent(
			changesetEntryId -> searchQuery.addRequiredTerm(
				_CHANGESET_ENTRY_ID_FIELD, changesetEntryId)
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

	private Optional<Long> _getChangesetCollectionId(
		String className, long classPK) {

		ChangesetManager changesetManager =
			ChangesetManagerUtil.getChangesetManager();

		Optional<ChangesetCollection> changesetColectionOptional =
			changesetManager.getChangesetCollection(className, classPK);

		return changesetColectionOptional.map(
			ChangesetCollection::getChangesetCollectionId);
	}

	private Optional<Long> _getChangesetEntryId(
		String className, long classPK) {

		ChangesetManager changesetManager =
			ChangesetManagerUtil.getChangesetManager();

		Optional<ChangesetEntry> changesetEntryOptional =
			changesetManager.getChangesetEntry(className, classPK);

		return changesetEntryOptional.map(ChangesetEntry::getChangesetEntryId);
	}

	private static final String _CHANGESET_COLLECTION_ID_FIELD =
		"changesetCollectionId";

	private static final String _CHANGESET_ENTRY_ID_FIELD = "changesetEntryId";

}
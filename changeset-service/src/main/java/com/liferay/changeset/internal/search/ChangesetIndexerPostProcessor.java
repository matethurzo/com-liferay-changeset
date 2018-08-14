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

import com.liferay.changeset.manager.ChangesetManagerUtil;
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

		Optional<Long> changesetIdOptional = _getChangesetId(
			entryClassName, entryClassPK);

		changesetIdOptional.ifPresent(
			changesetId -> document.addKeyword(
				_CHANGESET_ID_FIELD, changesetId));
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

		Optional<Long> changesetIdOptional = _getChangesetId(
			entryClassName, entryClassPK);

		changesetIdOptional.ifPresent(
			changesetEntry -> searchQuery.addRequiredTerm(
				_CHANGESET_ID_FIELD, changesetEntry)
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

	private Optional<Long> _getChangesetId(String className, long classPK) {
		Optional<ChangesetEntry> changesetEntryOptional =
			ChangesetManagerUtil.getChangesetEntry(className, classPK);

		return changesetEntryOptional.map(ChangesetEntry::getChangesetEntryId);
	}

	private static final String _CHANGESET_ID_FIELD = "changesetId";

}
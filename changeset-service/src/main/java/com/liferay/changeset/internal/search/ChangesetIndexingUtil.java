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

import com.liferay.changeset.cqrs.search.DocumentModelMapper;
import com.liferay.changeset.cqrs.search.DocumentModelMapperRegistryUtil;
import com.liferay.changeset.manager.ChangesetManager;
import com.liferay.changeset.manager.ChangesetManagerUtil;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.model.ChangesetEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistryUtil;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Daniel Kocsis
 */
public class ChangesetIndexingUtil {

	public static final String CHANGESET_COLLECTION_ID_FIELD =
		"changesetCollectionId";

	public static final String CHANGESET_ENTRY_ID_FIELD = "changesetEntryId";

	public static Optional<Long> getChangesetCollectionId(
		String className, long classPK) {

		ChangesetManager changesetManager =
			ChangesetManagerUtil.getChangesetManager();

		Optional<ChangesetCollection> changesetColectionOptional =
			changesetManager.getChangesetCollection(className, classPK);

		return changesetColectionOptional.map(
			ChangesetCollection::getChangesetCollectionId);
	}

	public static Optional<Long> getChangesetEntryId(
		String className, long classPK) {

		ChangesetManager changesetManager =
			ChangesetManagerUtil.getChangesetManager();

		Optional<ChangesetEntry> changesetEntryOptional =
			changesetManager.getChangesetEntry(className, classPK);

		return changesetEntryOptional.map(ChangesetEntry::getChangesetEntryId);
	}

	public static void index(
		long companyId, long changesetCollectionId, long changesetEntryId,
		BaseModel baseModel) {

		Class<?> modelClass = baseModel.getModelClass();

		if (!_changesetManager.isChangesetEnabled() ||
			!_changesetManager.isChangesetSupported(modelClass)) {

			return;
		}

		final Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			modelClass);

		final Document baseDocument;

		_runPostProcessor = false;

		try {
			baseDocument = indexer.getDocument(baseModel);
		}
		catch (SearchException se) {
			_log.error(
				"Unable to get document with indexer " + indexer.getClass(),
				se);

			return;
		}

		_runPostProcessor = true;

		final DocumentModelMapper documentModelMapper =
			DocumentModelMapperRegistryUtil.getDocumentModelMapper(modelClass);

		if (documentModelMapper == null) {
			return;
		}

		final Document mappedDocument = documentModelMapper.map(baseModel);

		Document document = _mergeDocuments(baseDocument, mappedDocument);

		_populateChangesetFields(
			changesetCollectionId, changesetEntryId, document);

		Optional<ChangesetEntry> changesetEntryOptional =
			_changesetManager.getChangesetEntry(changesetEntryId);

		if (!changesetEntryOptional.isPresent()) {
			_log.error(
				"Unable to find changeset entry with id " + changesetEntryId);

			return;
		}

		ChangesetEntry changesetEntry = changesetEntryOptional.get();

		document.addUID(
			"CHANGESET_COLLECTION_" + changesetCollectionId,
			"RESOURCE_PRIM_KEY_" + changesetEntry.getResourcePrimKey(),
			document.getUID());

		try {
			IndexWriterHelperUtil.updateDocument(
				indexer.getSearchEngineId(), companyId, document, true);
		}
		catch (SearchException se) {
			_log.error("Unable to update index document", se);
		}
	}

	public static void index(
		long companyId, long changesetCollectionId, long changesetEntryId,
		String className, long classPK) {

		PersistedModelLocalService persistedModelLocalService =
			PersistedModelLocalServiceRegistryUtil.
				getPersistedModelLocalService(className);

		try {
			PersistedModel persistedModel =
				persistedModelLocalService.getPersistedModel(classPK);

			if (persistedModel == null) {
				return;
			}

			BaseModel baseModel = (BaseModel)persistedModel;

			index(
				companyId, changesetCollectionId, changesetEntryId, baseModel);
		}
		catch (PortalException pe) {
			StringBundler sb = new StringBundler(4);

			sb.append("Unable to get model object for indexing: ");
			sb.append(className);
			sb.append(", ");
			sb.append(classPK);

			_log.error(sb.toString(), pe);
		}
	}

	public static boolean isRunPostProcessor() {
		return _runPostProcessor;
	}

	public static void setRunPostProcessor(boolean runPostProcessor) {
		_runPostProcessor = runPostProcessor;
	}

	private static Document _mergeDocuments(
		Document baseDocument, Document modelDocument) {

		Document resultDocument = new DocumentImpl();

		baseDocument.getFields().forEach(
			(key, field) -> resultDocument.add(field));

		Map<String, Field> fields = modelDocument.getFields();

		Stream<Map.Entry<String, Field>> stream = fields.entrySet().stream();

		stream.filter(
			entry -> !resultDocument.hasField(entry.getKey())
		).forEach(
			e -> resultDocument.add(e.getValue())
		);

		return resultDocument;
	}

	private static void _populateChangesetFields(
		long changesetCollectionId, long changesetEntryId, Document document) {

		document.addKeyword(
			CHANGESET_COLLECTION_ID_FIELD, changesetCollectionId);

		document.addKeyword(CHANGESET_ENTRY_ID_FIELD, changesetEntryId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ChangesetIndexingUtil.class);

	private static final ChangesetManager _changesetManager =
		ChangesetManagerUtil.getChangesetManager();
	private static boolean _runPostProcessor = true;

}
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
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetVersion;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.persistence.LayoutSetUtil;
import com.liferay.portal.kernel.service.persistence.LayoutSetVersionUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = ChangesetConfigurationRegistrar.class)
public class LayoutSetChangesetConfigurationRegistrar
	implements ChangesetConfigurationRegistrar<LayoutSet, LayoutSetVersion> {

	@Override
	public ChangesetConfiguration changesetConfiguration(
		ChangesetConfiguration.Builder<LayoutSet, LayoutSetVersion> builder) {

		return builder.identifier(
			"layout"
		).addResourceEntity(
			LayoutSet.class, LayoutSetUtil::fetchByPrimaryKey,
			LayoutSet::getLayoutSetId, LayoutSet::getVersionId,
			_layoutSetLocalService
		).addVersionEntity(
			LayoutSetVersion.class, LayoutSetVersion::getLayoutSetId,
			LayoutSetVersionUtil::fetchByPrimaryKey,
			LayoutSetVersion::getLayoutSetVersionId,
			LayoutSetVersion::getVersion, null,
			new Integer[]
				{
					WorkflowConstants.STATUS_APPROVED,
					WorkflowConstants.STATUS_DRAFT
				},
			LayoutSetVersion::getStatus
		).baselining(
			() -> {
				_changesetCQRSManager.disableCQRSRepository();

				List<LayoutSetVersion> layoutSetVersions =
					_layoutSetLocalService.getLayoutSetVersions();

				_changesetCQRSManager.enableCQRSRepository();

				return layoutSetVersions;
			}
		).indexer(
			IndexerRegistryUtil::nullSafeGetIndexer
		).build();
	}

	@Reference
	private ChangesetCQRSManager _changesetCQRSManager;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

}

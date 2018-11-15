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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutVersion;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.persistence.LayoutUtil;
import com.liferay.portal.kernel.service.persistence.LayoutVersionUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


/**
 * @author Gergely Mathe
 */
@Component(immediate = true, service = ChangesetConfigurationRegistrar.class)
public class LayoutChangesetConfigurationRegistrar
	implements ChangesetConfigurationRegistrar<Layout, LayoutVersion> {

	@Override
	public ChangesetConfiguration changesetConfiguration(
		ChangesetConfiguration.Builder<Layout, LayoutVersion> builder) {

		return builder.identifier(
			"layout"
		).addResourceEntity(
			Layout.class, LayoutUtil::fetchByPrimaryKey, Layout::getPlid,
			Layout::getVersionId, _layoutLocalService
		).addVersionEntity(
			LayoutVersion.class, LayoutVersion::getPlid,
			LayoutVersionUtil::fetchByPrimaryKey,
			LayoutVersion::getLayoutVersionId, LayoutVersion::getVersion, null,
			new Integer[]
				{
					WorkflowConstants.STATUS_APPROVED,
					WorkflowConstants.STATUS_DRAFT
				},
			LayoutVersion::getStatus
		).baselining(
			() -> {
				_changesetCQRSManager.disableCQRSRepository();

				List<LayoutVersion> layoutVersions =
					_layoutLocalService.getLayoutVersions();

				_changesetCQRSManager.enableCQRSRepository();

				return layoutVersions;
			}
		).indexer(
			IndexerRegistryUtil::nullSafeGetIndexer
		).build();
	}

	@Reference
	private ChangesetCQRSManager _changesetCQRSManager;

	@Reference
	private LayoutLocalService _layoutLocalService;

}

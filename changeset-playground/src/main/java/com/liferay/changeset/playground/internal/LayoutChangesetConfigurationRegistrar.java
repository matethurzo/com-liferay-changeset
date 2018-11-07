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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.service.LayoutLocalService;

import com.liferay.portal.kernel.service.persistence.LayoutUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

// TODO Replace at marked places, once versioning code is created

/**
 * @author Gergely Mathe
 */
@Component(immediate = true, service = ChangesetConfigurationRegistrar.class)
public class LayoutChangesetConfigurationRegistrar
	implements ChangesetConfigurationRegistrar<Layout, Layout> { // TODO Replace with <Layout, LayoutVersion>

	@Override
	public ChangesetConfiguration changesetConfiguration(
		ChangesetConfiguration.Builder<Layout, Layout> builder) { // TODO Replace with <Layout, LayoutVersion>

		return builder.identifier(
			"layout"
		).addResourceEntity(
			Layout.class, LayoutUtil::fetchByPrimaryKey,
			Layout::getPlid,
			layout -> 0L,
			// TODO Replace with Layout::getVersionId
			_layoutLocalService
		).addVersionEntity(
			Layout.class,
			// TODO Replace with LayoutVersion.class
			layout -> 0L,
			// TODO Replace with LayoutVersion::getPlid
			serializable -> null,
			// TODO Replace with LayoutVersionUtil::fetchByPrimaryKey
			layout -> 0L,
			// TODO Replace with LayoutVersion::getVersionId
			layout -> 1.0D,
			// TODO Replace with LayoutVersion::getVersion
			null, new Integer[] {WorkflowConstants.STATUS_APPROVED},
			layout -> 0
			// TODO Replace with LayoutVersion::getStatus
		).baselining(
			() -> {
				_changesetCQRSManager.disableCQRSRepository();

				List<Layout> layouts = _layoutLocalService.getLayouts(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				// TODO Extend with fetching and returning latest versions

				_changesetCQRSManager.enableCQRSRepository();

				return layouts;
			}
		).indexer(
			clazz -> _layoutIndexer
		).build();
	}

	@Reference
	private ChangesetCQRSManager _changesetCQRSManager;

	@Reference(
		// TODO This does not seem to exist for Layout, we may need to create it
		target = "(model.class.name=)"
	)
	private Indexer _layoutIndexer;

	@Reference
	private LayoutLocalService _layoutLocalService;

}

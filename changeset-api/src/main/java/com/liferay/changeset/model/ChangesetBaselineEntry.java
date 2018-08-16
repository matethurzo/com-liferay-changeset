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

package com.liferay.changeset.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the ChangesetBaselineEntry service. Represents a row in the &quot;ChangesetBaselineEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetBaselineEntryModel
 * @see com.liferay.changeset.model.impl.ChangesetBaselineEntryImpl
 * @see com.liferay.changeset.model.impl.ChangesetBaselineEntryModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.changeset.model.impl.ChangesetBaselineEntryImpl")
@ProviderType
public interface ChangesetBaselineEntry extends ChangesetBaselineEntryModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.changeset.model.impl.ChangesetBaselineEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ChangesetBaselineEntry, Long> CHANGESET_BASELINE_ENTRY_ID_ACCESSOR =
		new Accessor<ChangesetBaselineEntry, Long>() {
			@Override
			public Long get(ChangesetBaselineEntry changesetBaselineEntry) {
				return changesetBaselineEntry.getChangesetBaselineEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ChangesetBaselineEntry> getTypeClass() {
				return ChangesetBaselineEntry.class;
			}
		};
}
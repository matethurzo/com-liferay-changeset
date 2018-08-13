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

package com.liferay.changeset.baseline.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the BaselineInformation service. Represents a row in the &quot;BaselineInformation&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see BaselineInformationModel
 * @see com.liferay.changeset.baseline.model.impl.BaselineInformationImpl
 * @see com.liferay.changeset.baseline.model.impl.BaselineInformationModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.changeset.baseline.model.impl.BaselineInformationImpl")
@ProviderType
public interface BaselineInformation extends BaselineInformationModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.changeset.baseline.model.impl.BaselineInformationImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<BaselineInformation, Long> BASELINE_INFORMATION_ID_ACCESSOR =
		new Accessor<BaselineInformation, Long>() {
			@Override
			public Long get(BaselineInformation baselineInformation) {
				return baselineInformation.getBaselineInformationId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<BaselineInformation> getTypeClass() {
				return BaselineInformation.class;
			}
		};
}
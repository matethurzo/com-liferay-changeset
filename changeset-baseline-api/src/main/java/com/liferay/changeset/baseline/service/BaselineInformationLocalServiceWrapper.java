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

package com.liferay.changeset.baseline.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BaselineInformationLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see BaselineInformationLocalService
 * @generated
 */
@ProviderType
public class BaselineInformationLocalServiceWrapper
	implements BaselineInformationLocalService,
		ServiceWrapper<BaselineInformationLocalService> {
	public BaselineInformationLocalServiceWrapper(
		BaselineInformationLocalService baselineInformationLocalService) {
		_baselineInformationLocalService = baselineInformationLocalService;
	}

	/**
	* Adds the baseline information to the database. Also notifies the appropriate model listeners.
	*
	* @param baselineInformation the baseline information
	* @return the baseline information that was added
	*/
	@Override
	public com.liferay.changeset.baseline.model.BaselineInformation addBaselineInformation(
		com.liferay.changeset.baseline.model.BaselineInformation baselineInformation) {
		return _baselineInformationLocalService.addBaselineInformation(baselineInformation);
	}

	@Override
	public com.liferay.changeset.baseline.model.BaselineInformation addBaselineInformation(
		long userId, String name) {
		return _baselineInformationLocalService.addBaselineInformation(userId,
			name);
	}

	/**
	* Creates a new baseline information with the primary key. Does not add the baseline information to the database.
	*
	* @param baselineInformationId the primary key for the new baseline information
	* @return the new baseline information
	*/
	@Override
	public com.liferay.changeset.baseline.model.BaselineInformation createBaselineInformation(
		long baselineInformationId) {
		return _baselineInformationLocalService.createBaselineInformation(baselineInformationId);
	}

	/**
	* Deletes the baseline information from the database. Also notifies the appropriate model listeners.
	*
	* @param baselineInformation the baseline information
	* @return the baseline information that was removed
	*/
	@Override
	public com.liferay.changeset.baseline.model.BaselineInformation deleteBaselineInformation(
		com.liferay.changeset.baseline.model.BaselineInformation baselineInformation) {
		return _baselineInformationLocalService.deleteBaselineInformation(baselineInformation);
	}

	/**
	* Deletes the baseline information with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param baselineInformationId the primary key of the baseline information
	* @return the baseline information that was removed
	* @throws PortalException if a baseline information with the primary key could not be found
	*/
	@Override
	public com.liferay.changeset.baseline.model.BaselineInformation deleteBaselineInformation(
		long baselineInformationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _baselineInformationLocalService.deleteBaselineInformation(baselineInformationId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _baselineInformationLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _baselineInformationLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _baselineInformationLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.baseline.model.impl.BaselineInformationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _baselineInformationLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.baseline.model.impl.BaselineInformationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _baselineInformationLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _baselineInformationLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _baselineInformationLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.changeset.baseline.model.BaselineInformation fetchBaselineInformation(
		long baselineInformationId) {
		return _baselineInformationLocalService.fetchBaselineInformation(baselineInformationId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _baselineInformationLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the baseline information with the primary key.
	*
	* @param baselineInformationId the primary key of the baseline information
	* @return the baseline information
	* @throws PortalException if a baseline information with the primary key could not be found
	*/
	@Override
	public com.liferay.changeset.baseline.model.BaselineInformation getBaselineInformation(
		long baselineInformationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _baselineInformationLocalService.getBaselineInformation(baselineInformationId);
	}

	@Override
	public java.util.Optional<com.liferay.changeset.baseline.model.BaselineInformation> getBaseLineInformationByName(
		String name) {
		return _baselineInformationLocalService.getBaseLineInformationByName(name);
	}

	/**
	* Returns a range of all the baseline informations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.baseline.model.impl.BaselineInformationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of baseline informations
	* @param end the upper bound of the range of baseline informations (not inclusive)
	* @return the range of baseline informations
	*/
	@Override
	public java.util.List<com.liferay.changeset.baseline.model.BaselineInformation> getBaselineInformations(
		int start, int end) {
		return _baselineInformationLocalService.getBaselineInformations(start,
			end);
	}

	/**
	* Returns the number of baseline informations.
	*
	* @return the number of baseline informations
	*/
	@Override
	public int getBaselineInformationsCount() {
		return _baselineInformationLocalService.getBaselineInformationsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _baselineInformationLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _baselineInformationLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _baselineInformationLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the baseline information in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param baselineInformation the baseline information
	* @return the baseline information that was updated
	*/
	@Override
	public com.liferay.changeset.baseline.model.BaselineInformation updateBaselineInformation(
		com.liferay.changeset.baseline.model.BaselineInformation baselineInformation) {
		return _baselineInformationLocalService.updateBaselineInformation(baselineInformation);
	}

	@Override
	public BaselineInformationLocalService getWrappedService() {
		return _baselineInformationLocalService;
	}

	@Override
	public void setWrappedService(
		BaselineInformationLocalService baselineInformationLocalService) {
		_baselineInformationLocalService = baselineInformationLocalService;
	}

	private BaselineInformationLocalService _baselineInformationLocalService;
}
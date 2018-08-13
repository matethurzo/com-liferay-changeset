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

package com.liferay.changeset.baseline.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.changeset.baseline.model.BaselineEntry;
import com.liferay.changeset.baseline.service.BaselineEntryLocalService;
import com.liferay.changeset.baseline.service.persistence.BaselineEntryPersistence;
import com.liferay.changeset.baseline.service.persistence.BaselineInformationPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the baseline entry local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.changeset.baseline.service.impl.BaselineEntryLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.changeset.baseline.service.impl.BaselineEntryLocalServiceImpl
 * @see com.liferay.changeset.baseline.service.BaselineEntryLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class BaselineEntryLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements BaselineEntryLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.changeset.baseline.service.BaselineEntryLocalServiceUtil} to access the baseline entry local service.
	 */

	/**
	 * Adds the baseline entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param baselineEntry the baseline entry
	 * @return the baseline entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BaselineEntry addBaselineEntry(BaselineEntry baselineEntry) {
		baselineEntry.setNew(true);

		return baselineEntryPersistence.update(baselineEntry);
	}

	/**
	 * Creates a new baseline entry with the primary key. Does not add the baseline entry to the database.
	 *
	 * @param baselineEntryId the primary key for the new baseline entry
	 * @return the new baseline entry
	 */
	@Override
	@Transactional(enabled = false)
	public BaselineEntry createBaselineEntry(long baselineEntryId) {
		return baselineEntryPersistence.create(baselineEntryId);
	}

	/**
	 * Deletes the baseline entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param baselineEntryId the primary key of the baseline entry
	 * @return the baseline entry that was removed
	 * @throws PortalException if a baseline entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public BaselineEntry deleteBaselineEntry(long baselineEntryId)
		throws PortalException {
		return baselineEntryPersistence.remove(baselineEntryId);
	}

	/**
	 * Deletes the baseline entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param baselineEntry the baseline entry
	 * @return the baseline entry that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public BaselineEntry deleteBaselineEntry(BaselineEntry baselineEntry) {
		return baselineEntryPersistence.remove(baselineEntry);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(BaselineEntry.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return baselineEntryPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.baseline.model.impl.BaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return baselineEntryPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.baseline.model.impl.BaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return baselineEntryPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return baselineEntryPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return baselineEntryPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public BaselineEntry fetchBaselineEntry(long baselineEntryId) {
		return baselineEntryPersistence.fetchByPrimaryKey(baselineEntryId);
	}

	/**
	 * Returns the baseline entry with the primary key.
	 *
	 * @param baselineEntryId the primary key of the baseline entry
	 * @return the baseline entry
	 * @throws PortalException if a baseline entry with the primary key could not be found
	 */
	@Override
	public BaselineEntry getBaselineEntry(long baselineEntryId)
		throws PortalException {
		return baselineEntryPersistence.findByPrimaryKey(baselineEntryId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(baselineEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(BaselineEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("baselineEntryId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(baselineEntryLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(BaselineEntry.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"baselineEntryId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(baselineEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(BaselineEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("baselineEntryId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return baselineEntryLocalService.deleteBaselineEntry((BaselineEntry)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return baselineEntryPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the baseline entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.baseline.model.impl.BaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of baseline entries
	 * @param end the upper bound of the range of baseline entries (not inclusive)
	 * @return the range of baseline entries
	 */
	@Override
	public List<BaselineEntry> getBaselineEntries(int start, int end) {
		return baselineEntryPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of baseline entries.
	 *
	 * @return the number of baseline entries
	 */
	@Override
	public int getBaselineEntriesCount() {
		return baselineEntryPersistence.countAll();
	}

	/**
	 * Updates the baseline entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param baselineEntry the baseline entry
	 * @return the baseline entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BaselineEntry updateBaselineEntry(BaselineEntry baselineEntry) {
		return baselineEntryPersistence.update(baselineEntry);
	}

	/**
	 * Returns the baseline entry local service.
	 *
	 * @return the baseline entry local service
	 */
	public BaselineEntryLocalService getBaselineEntryLocalService() {
		return baselineEntryLocalService;
	}

	/**
	 * Sets the baseline entry local service.
	 *
	 * @param baselineEntryLocalService the baseline entry local service
	 */
	public void setBaselineEntryLocalService(
		BaselineEntryLocalService baselineEntryLocalService) {
		this.baselineEntryLocalService = baselineEntryLocalService;
	}

	/**
	 * Returns the baseline entry persistence.
	 *
	 * @return the baseline entry persistence
	 */
	public BaselineEntryPersistence getBaselineEntryPersistence() {
		return baselineEntryPersistence;
	}

	/**
	 * Sets the baseline entry persistence.
	 *
	 * @param baselineEntryPersistence the baseline entry persistence
	 */
	public void setBaselineEntryPersistence(
		BaselineEntryPersistence baselineEntryPersistence) {
		this.baselineEntryPersistence = baselineEntryPersistence;
	}

	/**
	 * Returns the baseline information local service.
	 *
	 * @return the baseline information local service
	 */
	public com.liferay.changeset.baseline.service.BaselineInformationLocalService getBaselineInformationLocalService() {
		return baselineInformationLocalService;
	}

	/**
	 * Sets the baseline information local service.
	 *
	 * @param baselineInformationLocalService the baseline information local service
	 */
	public void setBaselineInformationLocalService(
		com.liferay.changeset.baseline.service.BaselineInformationLocalService baselineInformationLocalService) {
		this.baselineInformationLocalService = baselineInformationLocalService;
	}

	/**
	 * Returns the baseline information persistence.
	 *
	 * @return the baseline information persistence
	 */
	public BaselineInformationPersistence getBaselineInformationPersistence() {
		return baselineInformationPersistence;
	}

	/**
	 * Sets the baseline information persistence.
	 *
	 * @param baselineInformationPersistence the baseline information persistence
	 */
	public void setBaselineInformationPersistence(
		BaselineInformationPersistence baselineInformationPersistence) {
		this.baselineInformationPersistence = baselineInformationPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.changeset.baseline.model.BaselineEntry",
			baselineEntryLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.changeset.baseline.model.BaselineEntry");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return BaselineEntryLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return BaselineEntry.class;
	}

	protected String getModelClassName() {
		return BaselineEntry.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = baselineEntryPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = BaselineEntryLocalService.class)
	protected BaselineEntryLocalService baselineEntryLocalService;
	@BeanReference(type = BaselineEntryPersistence.class)
	protected BaselineEntryPersistence baselineEntryPersistence;
	@BeanReference(type = com.liferay.changeset.baseline.service.BaselineInformationLocalService.class)
	protected com.liferay.changeset.baseline.service.BaselineInformationLocalService baselineInformationLocalService;
	@BeanReference(type = BaselineInformationPersistence.class)
	protected BaselineInformationPersistence baselineInformationPersistence;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameLocalService.class)
	protected com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService;
	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}
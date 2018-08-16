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

package com.liferay.changeset.internal.baseline.manager.test;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class JournalTestUtil {

	public static JournalArticle addArticle(long groupId, long folderId)
		throws Exception {

		return addArticle(groupId, folderId, StringPool.BLANK, true);
	}

	public static JournalArticle addArticle(
			long groupId, long folderId, long classNameId, String articleId,
			boolean autoArticleId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> contentMap,
			String layoutUuid, Locale defaultLocale, Date expirationDate,
			boolean workflowEnabled, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		String content = DDMStructureTestUtil.getSampleStructuredContent(
			contentMap, LocaleUtil.toLanguageId(defaultLocale));

		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm(
			_locales, defaultLocale);

		long ddmGroupId = GetterUtil.getLong(
			serviceContext.getAttribute("ddmGroupId"), groupId);

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			ddmGroupId, JournalArticle.class.getName(), ddmForm, defaultLocale);

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			ddmGroupId, ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		boolean neverExpire = true;

		int expirationDateDay = 0;
		int expirationDateMonth = 0;
		int expirationDateYear = 0;
		int expirationDateHour = 0;
		int expirationDateMinute = 0;

		if (expirationDate != null) {
			neverExpire = false;

			Calendar expirationCal = CalendarFactoryUtil.getCalendar(
				TestPropsValues.getUser().getTimeZone());

			expirationCal.setTime(expirationDate);

			expirationDateMonth = expirationCal.get(Calendar.MONTH);
			expirationDateDay = expirationCal.get(Calendar.DATE);
			expirationDateYear = expirationCal.get(Calendar.YEAR);
			expirationDateHour = expirationCal.get(Calendar.HOUR_OF_DAY);
			expirationDateMinute = expirationCal.get(Calendar.MINUTE);
		}

		Calendar displayCal = CalendarFactoryUtil.getCalendar(
			TestPropsValues.getUser().getTimeZone());

		int displayDateDay = displayCal.get(Calendar.DATE);
		int displayDateMonth = displayCal.get(Calendar.MONTH);
		int displayDateYear = displayCal.get(Calendar.YEAR);
		int displayDateHour = displayCal.get(Calendar.HOUR_OF_DAY);
		int displayDateMinute = displayCal.get(Calendar.MINUTE);

		if (workflowEnabled) {
			serviceContext = (ServiceContext)serviceContext.clone();

			if (approved) {
				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_PUBLISH);
			}
			else {
				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_SAVE_DRAFT);
			}
		}

		return JournalArticleLocalServiceUtil.addArticle(
			serviceContext.getUserId(), groupId, folderId, classNameId, 0,
			articleId, autoArticleId, JournalArticleConstants.VERSION_DEFAULT,
			titleMap, descriptionMap, content, ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey(), layoutUuid, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, 0, 0, 0, 0,
			0, true, true, false, null, null, null, null, serviceContext);
	}

	public static JournalArticle addArticle(
			long groupId, long folderId, String articleId,
			boolean autoArticleId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");

		return addArticle(
			groupId, folderId, articleId, autoArticleId, serviceContext);
	}

	public static JournalArticle addArticle(
			long groupId, long folderId, String articleId,
			boolean autoArticleId, ServiceContext serviceContext)
		throws Exception {

		return addArticle(
			groupId, folderId, JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			articleId, autoArticleId,
			_getLocalizedMap(RandomTestUtil.randomString()),
			_getLocalizedMap(RandomTestUtil.randomString()),
			_getLocalizedMap(RandomTestUtil.randomString()), null,
			LocaleUtil.getSiteDefault(), null, false, false, serviceContext);
	}

	private static Map<Locale, String> _getLocalizedMap(String value) {
		Map<Locale, String> valuesMap = new HashMap<>();

		for (Locale locale : _locales) {
			valuesMap.put(locale, value);
		}

		return valuesMap;
	}

	private static final Locale[] _locales =
		{LocaleUtil.US, LocaleUtil.GERMANY, LocaleUtil.SPAIN};

}
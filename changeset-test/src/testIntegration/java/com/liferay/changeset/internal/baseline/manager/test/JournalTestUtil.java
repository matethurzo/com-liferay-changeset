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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class JournalTestUtil {

	public static JournalArticle addArticle(long groupId, long folderId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");

		String content = getSampleStructuredContent(
			_getLocalizedMap(RandomTestUtil.randomString()),
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()));

		boolean neverExpire = true;

		Calendar displayCal = CalendarFactoryUtil.getCalendar(
			TestPropsValues.getUser().getTimeZone());

		int displayDateDay = displayCal.get(Calendar.DATE);
		int displayDateMonth = displayCal.get(Calendar.MONTH);
		int displayDateYear = displayCal.get(Calendar.YEAR);
		int displayDateHour = displayCal.get(Calendar.HOUR_OF_DAY);
		int displayDateMinute = displayCal.get(Calendar.MINUTE);

		return JournalArticleLocalServiceUtil.addArticle(
			serviceContext.getUserId(), groupId, folderId,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, 0, StringPool.BLANK,
			true, JournalArticleConstants.VERSION_DEFAULT,
			_getLocalizedMap(RandomTestUtil.randomString()),
			_getLocalizedMap(RandomTestUtil.randomString()), content,
			"BASIC-WEB-CONTENT", "BASIC-WEB-CONTENT", null, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			0, 0, 0, 0, 0, neverExpire, 0, 0, 0, 0, 0, true, true, false, null,
			null, null, null, serviceContext);
	}

	public static String getSampleStructuredContent(
		Map<Locale, String> contents, String defaultLocale) {

		return getSampleStructuredContent(
			"name", Collections.singletonList(contents), defaultLocale);
	}

	public static String getSampleStructuredContent(
		String name, List<Map<Locale, String>> contents, String defaultLocale) {

		StringBundler sb = new StringBundler();

		for (Map<Locale, String> map : contents) {
			for (Locale locale : map.keySet()) {
				sb.append(LocaleUtil.toLanguageId(locale));
				sb.append(StringPool.COMMA);
			}

			sb.setIndex(sb.index() - 1);
		}

		Document document = createDocumentContent(sb.toString(), defaultLocale);

		Element rootElement = document.getRootElement();

		for (Map<Locale, String> map : contents) {
			Element dynamicElementElement = rootElement.addElement(
				"dynamic-element");

			dynamicElementElement.addAttribute("index-type", "keyword");
			dynamicElementElement.addAttribute("name", name);
			dynamicElementElement.addAttribute("type", "text");

			for (Map.Entry<Locale, String> entry : map.entrySet()) {
				Element element = dynamicElementElement.addElement(
					"dynamic-content");

				element.addAttribute(
					"language-id", LocaleUtil.toLanguageId(entry.getKey()));
				element.addCDATA(entry.getValue());
			}
		}

		return document.asXML();
	}

	protected static Document createDocumentContent(
		String availableLocales, String defaultLocale) {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute("available-locales", availableLocales);
		rootElement.addAttribute("default-locale", defaultLocale);
		rootElement.addElement("request");

		return document;
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
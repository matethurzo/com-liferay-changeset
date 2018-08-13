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

package com.liferay.changeset.manager;

/**
 * @author Mate Thurzo
 */
public class ChangesetManagerUtil {

	public static ChangesetManager getChangesetManager() {
		if (_changesetManager != null) {
			return _changesetManager;
		}

		throw new NullPointerException("Changeset manager is null");
	}

	public static boolean isChangesetSupported(Class<?> clazz) {
		return _changesetManager.isChangesetSupported(clazz);
	}

	public static boolean isChangesetSupported(String identifier) {
		return _changesetManager.isChangesetSupported(identifier);
	}

	public static void setChangesetManager(ChangesetManager changesetManager) {
		if (_changesetManager != null) {
			changesetManager = _changesetManager;

			return;
		}

		_changesetManager = changesetManager;
	}

	private static ChangesetManager _changesetManager;

}
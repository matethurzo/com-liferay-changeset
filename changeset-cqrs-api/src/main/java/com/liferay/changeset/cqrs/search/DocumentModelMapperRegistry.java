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

package com.liferay.changeset.cqrs.search;

import java.util.Set;

/**
 * @author Daniel Kocsis
 */
public interface DocumentModelMapperRegistry {

	public <T> DocumentModelMapper<T> getDocumentModelMapper(Class<T> clazz);

	public <T> DocumentModelMapper<T> getDocumentModelMapper(String className);

	public Set<DocumentModelMapper<?>> getDocumentModelMappers();

	public void register(
		String className, DocumentModelMapper<?> documentModelMapper);

	public void unregister(String className);

}
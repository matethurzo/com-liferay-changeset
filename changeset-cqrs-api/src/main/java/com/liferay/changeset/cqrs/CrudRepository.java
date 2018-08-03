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

package com.liferay.changeset.cqrs;

import java.util.Optional;

/**
 * @author Daniel Kocsis
 */
public interface CrudRepository<T extends AggregateRoot<ID>, ID>
	extends Repository {

	public long count();

	public void delete(T aggregateRoot);

	public void deleteAll();

	public Iterable<T> findAll();

	public Optional<T> findById(ID id);

	public T save(T aggregateRoot);

}
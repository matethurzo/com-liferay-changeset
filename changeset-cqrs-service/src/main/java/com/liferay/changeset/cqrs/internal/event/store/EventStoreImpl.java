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

package com.liferay.changeset.cqrs.internal.event.store;

import com.liferay.changeset.cqrs.event.DomainEvent;
import com.liferay.changeset.cqrs.event.store.EventStore;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = EventStore.class)
public class EventStoreImpl implements EventStore {

	@Override
	public void add(DomainEvent domainEvent) {
		Queue<DomainEvent> domainEventQueue = _eventStore.get(
			domainEvent.getChangesetId());

		if (domainEventQueue == null) {
			domainEventQueue = new ConcurrentLinkedDeque<>();

			Queue<DomainEvent> newDomainEventsQueue = _eventStore.putIfAbsent(
				domainEvent.getChangesetId(), domainEventQueue);

			// putIfAbsent returns a real value if the map contained
			// the key before

			if (newDomainEventsQueue != null) {
				domainEventQueue = newDomainEventsQueue;
			}
		}

		domainEventQueue.add(domainEvent);
	}

	private final Map<Long, Queue<DomainEvent>> _eventStore =
		new ConcurrentHashMap<>();

}
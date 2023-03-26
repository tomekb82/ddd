package com.example.demo.event_sourcing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class PersistenceEventStore implements EventStore {

    private final UUID eventId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final UUID correlationId = UUID.randomUUID();
    private final UUID causationId = UUID.randomUUID();

    Map<AggregateId, PersistenceEvents> eventStore = new HashMap<>();

    @Override
    public DomainEvents loadStream(AggregateId id) {
        if(eventStore.get(id) == null) {
            eventStore.put(id, new PersistenceEvents(new ArrayList<>()));
        }
        return eventStore.get(id).extract();
    }

    @Override
    public void append(DomainEvent event) {
        eventStore.get(event.getAggregateId())
                .events()
                .add(PersistenceEvent
                        .enrich(event)
                        .toBuilder()
                        .eventId(eventId)
                        .userId(userId)
                        .correlationId(correlationId)
                        .causationId(causationId)
                        .build());
    }

    @Override
    public DomainEvents getEvents(AggregateId aggregateId) {
        return eventStore.get(aggregateId).extract();
    }
}

package com.example.demo.event_sourcing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class DomainEventStore implements EventStore{
    Map<AggregateId, DomainEvents> eventStore = new HashMap<>();

    @Override
    public DomainEvents loadStream(AggregateId id) {
        if(eventStore.get(id) == null) {
            eventStore.put(id, new DomainEvents(new ArrayList<>()));
        }
        return eventStore.get(id);
    }

    @Override
    public void append(DomainEvent event) {
        if(eventStore.get(event.getAggregateId()) == null) {
            eventStore.put(event.getAggregateId(), new DomainEvents(new ArrayList<>()));
        }
        eventStore.get(event.getAggregateId()).events().add(event);
    }

    @Override
    public DomainEvents getEvents(AggregateId aggregateId) {
        return eventStore.get(aggregateId);
    }
}

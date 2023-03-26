package com.example.demo.event_sourcing;

import java.util.List;
import java.util.stream.Collectors;

record PersistenceEvents(List<PersistenceEvent> events) {

    public DomainEvents extract() {
        List<DomainEvent> events = this.events.stream()
                .map(PersistenceEvent::toDomainEvent)
                .collect(Collectors.toList());
        return new DomainEvents(events);
    }
}

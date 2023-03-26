package com.example.demo.event_sourcing;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
abstract class DomainEvent {

    private final AggregateId aggregateId;

    private final EventType type;

    private final EventVersion version;

}

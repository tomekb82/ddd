package com.example.demo.event_sourcing;

interface EventStore {
    DomainEvents loadStream(AggregateId id);

    void append(DomainEvent event) ;

    DomainEvents getEvents(AggregateId aggregateId);
}

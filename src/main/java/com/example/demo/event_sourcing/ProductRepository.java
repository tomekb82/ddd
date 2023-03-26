package com.example.demo.event_sourcing;

interface ProductRepository {
    Product load(ProductId id);

    void append(DomainEvent events);

    DomainEvents getEvents(AggregateId aggregateId);
}

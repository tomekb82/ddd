package com.example.demo.purchase;

import java.util.List;

interface DomainEventPublisher {

    void publish(IDomainEvent event);

    default void publish(List<IDomainEvent> events) {
        events.forEach(this::publish);
    }
}

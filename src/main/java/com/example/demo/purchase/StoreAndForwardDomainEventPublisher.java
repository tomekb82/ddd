package com.example.demo.purchase;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class StoreAndForwardDomainEventPublisher implements DomainEventPublisher{

    private final JustForwardDomainEventPublisher domainEventPublisher;
    private final EventsStorage eventsStorage;

    @Override
    public void publish(IDomainEvent event) {
        //eventsStorage.save(event);
    }

    public void publicPeriodically() {
        List<IDomainEvent> events = eventsStorage.yetToPublish();
        events.forEach(domainEventPublisher::publish);
        //eventsStorage.markAsSent(events);
    }
}

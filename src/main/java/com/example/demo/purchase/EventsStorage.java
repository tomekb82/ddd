package com.example.demo.purchase;

import java.util.List;

interface EventsStorage {

    void save(IDomainEvent  event);

    List<IDomainEvent> yetToPublish();

    void markAsSent(List<IDomainEvent> events);
}

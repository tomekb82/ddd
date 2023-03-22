package com.example.demo.purchase;

import java.util.ArrayList;
import java.util.List;

public class JustForwardDomainEventPublisher implements DomainEventPublisher{

    List<IDomainEvent> events = new ArrayList<>();

    @Override
    public void publish(IDomainEvent event) {
        events.add(event);
    }
}

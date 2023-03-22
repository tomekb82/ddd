package com.example.demo.purchase;

import java.util.ArrayList;
import java.util.List;

public class EventsDatabase implements EventsStorage{

    List<IDomainEvent> events = new ArrayList<>();

    @Override
    public void save(IDomainEvent event) {
        events.add(event);
    }

    @Override
    public List<IDomainEvent> yetToPublish() {
        return null;
    }

    @Override
    public void markAsSent(List<IDomainEvent> events) {

    }
}

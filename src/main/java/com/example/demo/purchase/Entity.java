package com.example.demo.purchase;

import java.util.ArrayList;
import java.util.List;

abstract class Entity {

    private List<IDomainEvent> events = new ArrayList<>();

    protected void raise(IDomainEvent event){
        events.add(event);
    }

    public List<IDomainEvent> getChanges() {
        return events;
    }

    public void clearEvent() {
        events.clear();
    }
}

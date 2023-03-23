package com.example.demo.reservations.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDatabaseRepository implements EventRepository {

    Map<EventId, Event> events = new HashMap<>();


    @Override
    public void cancel(EventId eventId) {
        events.remove(eventId);
    }

    @Override
    public Event getById(EventId eventId) {
        return events.get(eventId);
    }

    @Override
    public List<Event> getAll() {
        return events.values().stream().toList();
    }

    @Override
    public Event update(Event event) {
        return events.put(event.eventId, event);
    }

    @Override
    public Event add(Event event) {
        return events.put(event.eventId, event);
    }
}

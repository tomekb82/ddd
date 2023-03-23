package com.example.demo.reservation;

import java.util.List;

public interface EventRepository {
    void cancel(EventId eventId);

    Event getById(EventId eventId);

    List<Event> getAll();

    Event update(Event event);

    Event add(Event event);
}

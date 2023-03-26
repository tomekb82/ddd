package com.example.demo.event_sourcing;

import com.google.gson.Gson;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder(toBuilder = true)
class PersistenceEvent {
    private final UUID eventId;
    private final UUID aggregateId;
    private final UUID userId;
    private final UUID correlationId;
    private final UUID causationId;
    private final Integer version;
    private final String eventType;
    private final String eventData;

    public static PersistenceEvent enrich(DomainEvent event) {
        return PersistenceEvent.builder()
                .aggregateId(event.getAggregateId().id())
                .eventType(event.getType().type())
                .eventData(new Gson().toJson(event))
                .version(event.getVersion().version())
                .build();
    }

    public DomainEvent toDomainEvent() {
        Class aClass = new EventMapper().events.get(eventType);
        return (DomainEvent) new Gson().fromJson(eventData, aClass);
    }
}

package com.example.demo.subscription;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventSourceSubscriptionRepository implements SubscriptionRepository{

    private final Map<SubscriptionId, List<DomainEvent>> storedEvents = new HashMap<>();

    @Override
    public void save(Subscription subscription) {
        List<DomainEvent> currentSteam = storedEvents.getOrDefault(subscription.id(), new ArrayList<>());
        currentSteam.addAll(subscription.getPendingEvents());
        storedEvents.put(subscription.id(), currentSteam);
        subscription.flushEvents();
    }

    @Override
    public Subscription findById(SubscriptionId subscriptionId) {
        return Subscription.recreateFrom(storedEvents.get(subscriptionId), new Subscription(null, subscriptionId));
    }
}

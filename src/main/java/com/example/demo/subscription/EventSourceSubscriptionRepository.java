package com.example.demo.subscription;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventSourceSubscriptionRepository implements SubscriptionRepository{

    private final Map<SubscriptionId, List<DomainEvent>> storedEvents = new HashMap<>();

    private final Map<SubscriptionId, Subscription> snapshots = new HashMap<>();

    @Override
    public void save(Subscription subscription) {
        List<DomainEvent> currentSteam = storedEvents.getOrDefault(subscription.id(), new ArrayList<>());
        currentSteam.addAll(subscription.getPendingEvents());
        storedEvents.put(subscription.id(), currentSteam);
        subscription.flushEvents();
    }

    @Override
    public Subscription findById(SubscriptionId subscriptionId) {
        return Subscription.recreateFrom(storedEvents.get(subscriptionId),
                new Subscription(Clock.systemDefaultZone(), subscriptionId));
    }

    @Override
    public void saveSnapshot(SubscriptionId subscriptionId) {
        snapshots.put(subscriptionId, findById(subscriptionId));
        storedEvents.remove(subscriptionId);
    }

    @Override
    public Subscription getSnapshot(SubscriptionId subscriptionId) {
        return snapshots.get(subscriptionId);
    }
}

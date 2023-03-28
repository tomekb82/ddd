package com.example.demo.subscription;

import java.util.HashMap;
import java.util.Map;

public class InMemorySubscriptionRepository implements SubscriptionRepository{

    private final Map<SubscriptionId, Subscription> subs = new HashMap<>();

    @Override
    public void save(Subscription subscription) {
        subs.put(subscription.id(), subscription);
    }

    @Override
    public Subscription findById(SubscriptionId subscriptionId) {
        return subs
                .values()
                .stream()
                .filter(sub -> sub.id().equals(subscriptionId))
                .findAny()
                .orElse(null);
    }
}

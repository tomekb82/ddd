package com.example.demo.subscription;

public interface SubscriptionRepository {

    void save(Subscription subscription);

    Subscription findById(SubscriptionId subscriptionId);

    void saveSnapshot(SubscriptionId subscriptionId);

    Subscription getSnapshot(SubscriptionId subscriptionId);

}

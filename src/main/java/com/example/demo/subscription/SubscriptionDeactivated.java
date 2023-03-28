package com.example.demo.subscription;

import lombok.AllArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
class SubscriptionDeactivated implements DomainEvent{
    final SubscriptionId subscriptionId;
    final Instant timestamp;
}

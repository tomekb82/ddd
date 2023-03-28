package com.example.demo.subscription;

import lombok.AllArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
class SubscriptionResumed implements DomainEvent{
    final SubscriptionId subscriptionId;
    final Instant timestamp;
}

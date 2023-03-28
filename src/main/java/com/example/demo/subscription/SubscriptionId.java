package com.example.demo.subscription;

import java.util.UUID;

public record SubscriptionId(UUID id) {

    public static SubscriptionId newOne(){
        return new SubscriptionId(UUID.randomUUID());
    }
}

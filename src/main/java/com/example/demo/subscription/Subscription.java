package com.example.demo.subscription;

import java.time.Clock;
import java.time.Instant;

import static com.example.demo.subscription.Subscription.Status.*;

class Subscription {

    enum Status {
        New, Activated, Paused, PastDue, Deactivated
    }

    private final Clock clock;
    private final SubscriptionId subscriptionId;
    private Pauses pauses = Pauses.defaultPauses();
    private Status status = New;

    Subscription(Clock clock, SubscriptionId subscriptionId){
        this.clock = clock;
        this.subscriptionId = subscriptionId;
    }

    Result activate() {
        this.status = Activated;
        return Result.success;
    }

    Result deactivate() {
        if(isActive()){
            this.status = Deactivated;
            return Result.success;
        }
        return Result.failure;
    }

    Result pause() {
        return pause(Instant.now(clock));
    }

    Result pause(Instant when) {
        if (isActive() && pauses.canPauseAt(when)){
            pauses = pauses.withNewPauseAt(when);
            status = Paused;
            return Result.success;
        }
        return Result.failure;
    }

    private boolean isActive() {
        return status == Activated;
    }

    Result resume() {
        if (isPaused()) {
            status = Activated;
            return Result.success;
        }
        return Result.failure;
    }

    private boolean isPaused() {
        return status == Paused;
    }
}

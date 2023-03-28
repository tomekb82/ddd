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
        subscriptionActivated(new SubscriptionActivated(subscriptionId, Instant.now(clock)));
        return Result.success();
    }

    void subscriptionActivated(SubscriptionActivated event){
        this.status = Activated;
    }

    Result deactivate() {
        if(isActive()){
            subscriptionDeactivated(new SubscriptionDeactivated(subscriptionId, Instant.now(clock)));
            return Result.success();
        }
        return Result.failure("error in deactivate");
    }

    void subscriptionDeactivated(SubscriptionDeactivated event){
        this.status = Deactivated;
    }

    Result pause() {
        return pause(Instant.now(clock));
    }

    Result pause(Instant when) { // komenda - niebieska karteczka
        if (isActive() && pauses.canPauseAt(when)){ // niezmienniki - zolte karteczki
            subscriptionPaused(new SubscriptionPaused(subscriptionId, Instant.now(clock), when)); // zdarzenia domenowe - pomaranczowe karteczki
            return Result.success();
        }
        return Result.failure("error in pause");
    }

    private void subscriptionPaused(SubscriptionPaused event) {
        pauses = pauses.withNewPauseAt(event.timeOfPause);
        status = Paused;
    }

    Result resume() {
        if (isPaused()) {
            subscriptionResumed(new SubscriptionResumed(subscriptionId, Instant.now(clock)));
            return Result.success();
        }
        return Result.failure("error in resume");
    }

    private void subscriptionResumed(SubscriptionResumed event) {
        status = Activated;
    }

    Result markAsPastDue() {
        subscriptionMarkPastDued(new SubscriptionMarkedPastDue(subscriptionId, Instant.now(clock)));
        return Result.success();
    }

    void subscriptionMarkPastDued(SubscriptionMarkedPastDue event) {
        status = PastDue;
    }

    Subscription applySnapshot(SnapshotEvent event) {
        return null;
    }

    private boolean isActive() {
        return status == Activated;
    }

    private boolean isPaused() {
        return status == Paused;
    }
}

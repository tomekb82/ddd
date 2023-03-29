package com.example.demo.subscription;

import io.vavr.Predicates;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.demo.subscription.Subscription.Status.*;
import static io.vavr.API.*;
import static io.vavr.collection.List.ofAll;

class Subscription {

    public static Subscription recreateFrom(List<DomainEvent> domainEvents, Subscription initialState) {
        return ofAll(domainEvents)
                .foldLeft(initialState, Subscription::apply);
    }

    private Subscription apply(DomainEvent nextEvent) {
        return Match(nextEvent).of(
                Case($(Predicates.instanceOf(SubscriptionActivated.class)), (event) -> this.handle(event, true)),
                Case($(Predicates.instanceOf(SubscriptionDeactivated.class)), (event) -> this.handle(event, true)),
                Case($(Predicates.instanceOf(SubscriptionMarkedPastDue.class)), (event) -> this.handle(event, true)),
                Case($(Predicates.instanceOf(SubscriptionResumed.class)), (event) -> this.handle(event, true)),
                Case($(Predicates.instanceOf(SubscriptionPaused.class)), (event) -> this.handle(event, true))
        );
    }

    enum Status {
        New, Activated, Paused, PastDue, Deactivated
    }

    private final Clock clock;
    private final SubscriptionId subscriptionId;
    private Pauses pauses = Pauses.defaultPauses();
    private Status status = New;
    private List<DomainEvent> pendingEvents = new ArrayList<>();

    Subscription(Clock clock, SubscriptionId subscriptionId){
        this.clock = clock;
        this.subscriptionId = subscriptionId;
    }

    Subscription(Clock clock, SubscriptionId subscriptionId, Pauses pauses,
                 Status status, List<DomainEvent> pendingEvents){
        this.clock = clock;
        this.subscriptionId = subscriptionId;
        this.pauses = pauses;
        this.status = status;
        this.pendingEvents = pendingEvents;
    }

    public SubscriptionId id() {
        return subscriptionId;
    }

    public List<DomainEvent> getPendingEvents() {
        return Collections.unmodifiableList(pendingEvents);
    }

    public void flushEvents() {
        pendingEvents.clear();
    }

    Result activate() {
        handle(new SubscriptionActivated(id(), Instant.now(clock)), false);
        return Result.success();
    }

    Subscription handle(SubscriptionActivated event, boolean isHistory){
        pendingEvents.add(event);
        this.status = Activated;
        return new Subscription(clock, id(), pauses, status, pendingEvents);
    }

    Result deactivate() {
        if(isActive()){
            handle(new SubscriptionDeactivated(id(), Instant.now(clock)), false);
            return Result.success();
        }
        return Result.failure("error in deactivate");
    }

    Subscription handle(SubscriptionDeactivated event, boolean isHistory){
        pendingEvents.add(event);
        this.status = Deactivated;
        return new Subscription(clock, id(), pauses, status, pendingEvents);
    }

    Result pause() {
        return pause(Instant.now(clock));
    }

    Result pause(Instant when) { // komenda - niebieska karteczka
        if (isActive() && pauses.canPauseAt(when)){ // niezmienniki - zolte karteczki
            handle(new SubscriptionPaused(subscriptionId, Instant.now(clock), when), false); // zdarzenia domenowe - pomaranczowe karteczki
            return Result.success();
        }
        return Result.failure("error in pause");
    }

    Subscription handle(SubscriptionPaused event, boolean isHistory) {
        if (!isHistory){
            pendingEvents.add(event);
        }
        pauses = pauses.withNewPauseAt(event.timeOfPause);
        status = Paused;
        return new Subscription(clock, id(), pauses, status, pendingEvents);
    }

    Result resume() {
        if (isPaused()) {
            handle(new SubscriptionResumed(id(), Instant.now(clock)), false);
            return Result.success();
        }
        return Result.failure("error in resume");
    }

    Subscription handle(SubscriptionResumed event, boolean isHistory) {
        pendingEvents.add(event);
        status = Activated;
        return new Subscription(clock, id(), pauses, status, pendingEvents);
    }

    Result markAsPastDue() {
        handle(new SubscriptionMarkedPastDue(id(), Instant.now(clock)), false);
        return Result.success();
    }

    Subscription handle(SubscriptionMarkedPastDue event, boolean isHistory) {
        pendingEvents.add(event);
        status = PastDue;
        return new Subscription(clock, id(), pauses, status, pendingEvents);
    }

    private boolean isActive() {
        return status == Activated;
    }

    private boolean isPaused() {
        return status == Paused;
    }
}

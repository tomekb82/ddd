package com.example.demo.subscription

import spock.lang.Specification
import spock.lang.Subject

import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

import static java.time.temporal.ChronoUnit.DAYS

class SubscriptionTest extends Specification{
    static final Instant someDay = LocalDate.of(1989, 12, 5).atStartOfDay(ZoneId.systemDefault()).toInstant()
    static final Instant elevenDaysLater = someDay.plus(11, DAYS)
    static final Instant twentyTwoDaysLater = someDay.plus(22, DAYS)
    Clock fixedClock = Clock.fixed(someDay, ZoneId.systemDefault())

    @Subject
    Subscription subscription = new Subscription(fixedClock, SubscriptionId.newOne())

    def "should activate new sub"() {
        expect:
            subscription.activate().isSuccessful()
    }

    def "should deactivate activated sub"() {
        given:
            given(new SubscriptionActivated(subscription.id(), Instant.now(fixedClock)))
        expect:
            subscription.deactivate().isSuccessful()
    }

    def "should pause activated sub"() {
        given:
            given(new SubscriptionActivated(subscription.id(), Instant.now(fixedClock)))
        expect:
            subscription.pause().isSuccessful()
    }

    def "should not pause not active sub"() {
        expect:
            subscription.pause().isFailure()
    }

    def "should not pause when all pauses used"() {
        given:
            given(new SubscriptionActivated(subscription.id(), Instant.now(fixedClock)))
        and:
            assert subscription.pause(elevenDaysLater).isSuccessful()
            assert subscription.resume().isSuccessful()
        and:
            assert subscription.pause(twentyTwoDaysLater).isSuccessful()
            assert subscription.resume().isSuccessful()
        expect:
            subscription.pause().isFailure()
    }

    def "should not pause if less than 10 days from last pause"() {
        given:
            given(new SubscriptionActivated(subscription.id(), Instant.now(fixedClock)))
        and:
            assert subscription.pause(someDay).isSuccessful()
        and:
            assert subscription.resume().isSuccessful()
        expect:
            subscription.pause(someDay).isFailure()
    }

    def "should pause if more than 10 days from last pause"() {
        given:
            given(new SubscriptionActivated(subscription.id(), Instant.now(fixedClock)))
        and:
            assert subscription.pause(someDay).isSuccessful()
            assert subscription.resume().isSuccessful()
        and:
            assert subscription.pause(elevenDaysLater).isSuccessful()
            assert subscription.resume().isSuccessful()
        expect:
            subscription.pause(twentyTwoDaysLater).isSuccessful()
    }

    def "should resume paused sub"() {
        given:
            given(new SubscriptionActivated(subscription.id(), Instant.now(fixedClock)))
        and:
            assert subscription.pause().isSuccessful()
        expect:
            subscription.resume().isSuccessful()
    }

    private void given(DomainEvent event) {
        subscription.handle(event, true)
    }
}

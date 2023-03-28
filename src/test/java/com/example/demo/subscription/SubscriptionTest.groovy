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
            subscription.activate() == Result.success
    }

    def "should deactivate activated sub"() {
        given:
            subscription.activate()
        expect:
            subscription.deactivate() == Result.success
    }

    def "should pause activated sub"() {
        given:
            subscription.activate()
        expect:
            subscription.pause() == Result.success
    }

    def "should not pause not active sub"() {
        expect:
            subscription.pause() == Result.failure
    }

    def "should not pause when all pauses used"() {
        given:
            subscription.activate()
        and:
            assert subscription.pause(elevenDaysLater) == Result.success
            assert subscription.resume() == Result.success
        and:
            assert subscription.pause(twentyTwoDaysLater) == Result.success
            assert subscription.resume() == Result.success
        expect:
            subscription.pause() == Result.failure
    }

    def "should not pause if less than 10 days from last pause"() {
        given:
            subscription.activate()
        and:
            assert subscription.pause(someDay) == Result.success
        and:
            assert subscription.resume() == Result.success
        expect:
            subscription.pause(someDay) == Result.failure
    }

    def "should pause if more than 10 days from last pause"() {
        given:
            subscription.activate()
        and:
            assert subscription.pause(someDay) == Result.success
            assert subscription.resume() == Result.success
        and:
            assert subscription.pause(elevenDaysLater) == Result.success
            assert subscription.resume() == Result.success
        expect:
            subscription.pause(twentyTwoDaysLater) == Result.failure
    }

    def "should resume paused sub"() {
        given:
            subscription.activate()
        and:
            assert subscription.pause() == Result.success
        expect:
            subscription.resume() == Result.success
    }
}

package com.example.demo.subscription

import spock.lang.Specification
import spock.lang.Subject

import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class SubscriptionRepositoryTest extends Specification{

    @Subject
    SubscriptionRepository repository = new EventSourceSubscriptionRepository() //InMemorySubscriptionRepository

    Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault())

    def "should save and load"() {
        given:
            Subscription sub = new Subscription(fixedClock, SubscriptionId.newOne())
        and:
            sub.activate()
        and:
            sub.pause()
        and:
            repository.save(sub)
        when:
            Subscription load = repository.findById(sub.id())
        then:
            load.resume().isSuccessful()
    }

    def "should restore from snapshot"() {
        given:
            Subscription sub = new Subscription(fixedClock, SubscriptionId.newOne())
        and:
            sub.activate()
        and:
            sub.pause()
        and:
            repository.save(sub)
        and:
            sub.pause()
        and:
            repository.saveSnapshot(sub.id())
        when:
            Subscription snapshot = repository.getSnapshot(sub.id())
        then:
            snapshot.resume().isSuccessful()
    }


}

package com.example.demo.purchase

import spock.lang.Specification

import java.time.Instant

class PurchaseServiceTest extends Specification {

    private static final PurchaseId PURCHASE_ID = new PurchaseId(UUID.randomUUID())

    private static final Product CODING_SUB = new Product("CODING_SUB")
    private static final Product EBOOK = new Product("EBOOK")

    PurchaseRepository purchaseRepository = new PurchaseDatabase()
    ExtraProductPolicy extraProductPolicy = new BuyOneGetSomeFreeProductPolicy()
    DomainEventPublisher justForwardDomainEventPublisher = new JustForwardDomainEventPublisher();;
    EventsStorage eventsStorage;
    DomainEventPublisher publisher = new StoreAndForwardDomainEventPublisher(justForwardDomainEventPublisher, eventsStorage)
    PurchaseService purchaseService = new PurchaseService(purchaseRepository, extraProductPolicy, publisher)

    def "can add a subscription"() {
        given:
        Purchase purchase = aPurchase()
        when:
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID)
        then:
        "{CODING_SUB=1}" == purchase.print()
    }

    def "can remove a subscription"() {
        given:
        Purchase purchase = aPurchase()
        when:
        purchaseService.removeProduct(CODING_SUB, PURCHASE_ID)
        then:
        "{}" == purchase.print()
    }

    def "can add two same subscriptions"() {
        given:
        Purchase purchase = aPurchase()
        when:
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID)
        and:
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID)
        then:
        "{CODING_SUB=2}" == purchase.print()
    }

    def "buy one subscription and get free book"() {
        given:
        Purchase purchase = aPurchase()
        and:
        extraProductPolicy.addNewExtraProduct(new ExtraProduct(CODING_SUB, EBOOK))
        when:
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID)
        then:
        "{CODING_SUB=1}{[FREE] EBOOK=1}" == purchase.print()
    }

    def "buy two subscriptions and get two free books"() {
        given:
        Purchase purchase = aPurchase()
        and:
        extraProductPolicy.addNewExtraProduct(new ExtraProduct(CODING_SUB, EBOOK))
        when:
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID)
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID)
        then:
        "{CODING_SUB=2}{[FREE] EBOOK=2}" == purchase.print()
    }

    def "buy two subscriptions and add some additional ebook"() {
        given:
        Purchase purchase = aPurchase()
        and:
        extraProductPolicy.addNewExtraProduct(new ExtraProduct(CODING_SUB, EBOOK))
        when:
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID)
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID)
        purchaseService.addProduct(EBOOK, PURCHASE_ID)
        then:
        "{EBOOK=1, CODING_SUB=2}{[FREE] EBOOK=2}" == purchase.print()
    }

    def "intentionally remove free book"() {
        given:
        Purchase purchase = aPurchase()
        and:
        extraProductPolicy.addNewExtraProduct(new ExtraProduct(CODING_SUB, EBOOK))
        when:
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID)
        purchaseService.intentionallyRemoveFreeProduct(EBOOK, PURCHASE_ID)
        then:
        "{CODING_SUB=1}" == purchase.print()
    }

    def "add back free book"() {
        given:
        Purchase purchase = aPurchase()
        and:
        extraProductPolicy.addNewExtraProduct(new ExtraProduct(CODING_SUB, EBOOK))
        when:
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID)
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID)
        purchaseService.intentionallyRemoveFreeProduct(EBOOK, PURCHASE_ID)
        purchaseService.addBackFreeProduct(EBOOK, PURCHASE_ID)
        then:
        "{CODING_SUB=2}{[FREE] EBOOK=2}" == purchase.print()
    }

    def "list subscription events"() {
        given:
        Purchase purchase = aPurchase()
        and:
        extraProductPolicy.addNewExtraProduct(new ExtraProduct(CODING_SUB, EBOOK))
        when:
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID)
        purchaseService.addProduct(CODING_SUB, PURCHASE_ID)
        purchaseService.intentionallyRemoveFreeProduct(EBOOK, PURCHASE_ID)
        purchaseService.addBackFreeProduct(EBOOK, PURCHASE_ID)
        then:
        4 == purchase.getChanges().size()
        ["ProductAdded", "ProductAdded", "IntentionallyRemovedFreeProduct", "AddBackFreeProduct"] == purchase.getChanges()
                    .stream()
                    .map({event -> event.name()})
                    .toList()
    }

    Purchase aPurchase() {
        Purchase purchase = Purchase.of(PURCHASE_ID, Instant.now())
        purchaseRepository.save(purchase)
        return purchase;
    }
}

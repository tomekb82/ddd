package com.example.demo.purchase;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PurchaseService {

   private final PurchaseRepository purchaseRepository;
   private final ExtraProductPolicy extraProductPolicy;
   private final DomainEventPublisher publisher;

   void addProduct(Product product, PurchaseId purchaseId) {
       Purchase purchase = purchaseRepository.findyBy(purchaseId);
       purchase.addProduct(product, extraProductPolicy);
       purchaseRepository.save(purchase);

       publisher.publish(purchase.getChanges());
   }

    void removeProduct(Product product, PurchaseId purchaseId) {
        Purchase purchase = purchaseRepository.findyBy(purchaseId);
        purchase.removeProduct(product, extraProductPolicy);
        purchaseRepository.save(purchase);

        publisher.publish(purchase.getChanges());
    }

    void intentionallyRemoveFreeProduct(Product freeProduct, PurchaseId purchaseId) {
        Purchase purchase = purchaseRepository.findyBy(purchaseId);
        purchase.intentionallyRemoveFreeProduct(freeProduct);
        purchaseRepository.save(purchase);

        publisher.publish(purchase.getChanges());
    }

    void addBackFreeProduct(Product freeProduct, PurchaseId purchaseId) {
        Purchase purchase = purchaseRepository.findyBy(purchaseId);
        purchase.addBackFreeProduct(freeProduct);
        purchaseRepository.save(purchase);

        publisher.publish(purchase.getChanges());
    }
}

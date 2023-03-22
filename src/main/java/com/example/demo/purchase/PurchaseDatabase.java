package com.example.demo.purchase;

import java.util.HashMap;
import java.util.Map;

class PurchaseDatabase implements PurchaseRepository{

    private final Map<PurchaseId, Purchase> purchases = new HashMap<>();

    @Override
    public Purchase findyBy(PurchaseId purchaseId) {
        return purchases.get(purchaseId);
    }

    @Override
    public void save(Purchase purchase) {
        purchases.put(purchase.purchaseId, purchase);
    }
}

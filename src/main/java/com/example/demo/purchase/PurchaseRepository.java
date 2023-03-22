package com.example.demo.purchase;

interface PurchaseRepository {

    Purchase findyBy(PurchaseId purchaseId);

    void save(Purchase  purchase);
}

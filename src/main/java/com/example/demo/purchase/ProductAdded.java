package com.example.demo.purchase;

class ProductAdded implements IDomainEvent {
    @Override
    public String name() {
        return "ProductAdded";
    }
}

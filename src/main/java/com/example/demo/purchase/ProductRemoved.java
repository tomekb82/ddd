package com.example.demo.purchase;

class ProductRemoved implements IDomainEvent {
    @Override
    public String name() {
        return "ProductRemoved";
    }
}

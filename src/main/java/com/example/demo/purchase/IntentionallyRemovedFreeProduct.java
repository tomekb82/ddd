package com.example.demo.purchase;

class IntentionallyRemovedFreeProduct implements IDomainEvent {
    @Override
    public String name() {
        return "IntentionallyRemovedFreeProduct";
    }
}

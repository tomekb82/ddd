package com.example.demo.purchase;

class AddBackFreeProduct implements IDomainEvent {
    @Override
    public String name() {
        return "AddBackFreeProduct";
    }
}

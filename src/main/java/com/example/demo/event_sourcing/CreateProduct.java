package com.example.demo.event_sourcing;

class CreateProduct extends Command {

    String name;
    int price;

    public CreateProduct(AggregateId aggregateId, String name, int price) {
        super(aggregateId);
        this.name = name;
        this.price = price;
    }
}



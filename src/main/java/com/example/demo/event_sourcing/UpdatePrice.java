package com.example.demo.event_sourcing;

class UpdatePrice extends Command {

    int price;

    public UpdatePrice(AggregateId aggregateId, int price) {
        super(aggregateId);
        this.price = price;
    }
}



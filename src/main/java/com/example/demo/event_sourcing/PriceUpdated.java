package com.example.demo.event_sourcing;

import lombok.Getter;

@Getter
public class PriceUpdated extends DomainEvent{

    private int price;

    public PriceUpdated(ProductId productId,
                          EventType eventType,
                          EventVersion eventVersion,
                          int price) {
        super(new AggregateId(productId.id()), eventType, eventVersion);
        this.price = price;
    }
}

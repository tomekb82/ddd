package com.example.demo.event_sourcing;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCreated extends DomainEvent {

    private final String name;
    private final int price;

    public ProductCreated(ProductId productId,
                          EventType eventType,
                          EventVersion eventVersion,
                          String name,
                          int price) {
        super(new AggregateId(productId.id()), eventType, eventVersion);
        this.name = name;
        this.price = price;
    }

}

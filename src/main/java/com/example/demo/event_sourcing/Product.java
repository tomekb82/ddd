package com.example.demo.event_sourcing;

import io.vavr.Predicates;
import lombok.AllArgsConstructor;

import java.util.function.Predicate;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@AllArgsConstructor
public class Product {

    ProductId id;
    String name;
    Integer prize;

    private Product(ProductId id) {
        this.id = id;
    }

    private Product(Integer prize) {
        this.prize = prize;
    }

    public static Product of(ProductId id) {
        return new Product(id);
    }

    Product apply(DomainEvent domainEvent){
        return Match(domainEvent).of(
                Case($(instanceOf(ProductCreated.class)), this::productAdded),
                Case($(instanceOf(PriceUpdated.class)), this::priceUpdated)
        );
    }

    private Product productAdded(ProductCreated event){
        id = new ProductId(event.getAggregateId().id());
        name = event.getName();
        prize = event.getPrice();
        return new Product(id, name, prize);
    }

    private Product priceUpdated(PriceUpdated event){
        this.prize = event.getPrice();
        return new Product(prize);
    }


    public DomainEvent decide(Command command) {
        return Match(command).of(
                Case($(instanceOf(CreateProduct.class)), this::create),
                Case($(instanceOf(UpdatePrice.class)), this::updatePrice)
        );
    }

    private DomainEvent create(CreateProduct command) {
        id = new ProductId(command.aggregateId.id());
        name = command.name;
        prize = command.price;
        return new ProductCreated(
                new ProductId(command.aggregateId.id()),
                new EventType("ProductCreated"),
                new EventVersion(1),
                command.name,
                command.price);
    }

    private DomainEvent updatePrice(UpdatePrice command) {
        prize = command.price;
        return new PriceUpdated(new ProductId(command.aggregateId.id()),
                new EventType("PriceUpdated"),
                new EventVersion(1),
                command.price);
    }
}

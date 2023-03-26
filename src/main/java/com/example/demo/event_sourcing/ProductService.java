package com.example.demo.event_sourcing;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    void handle(Command command) {
        Product product = repository.load(new ProductId(command.aggregateId.id()));

        DomainEvent event = product.decide(command);

        repository.append(event);
    }

    Product getSnapshot(AggregateId aggregateId) {
        return repository.load(new ProductId(aggregateId.id()));
    }
}

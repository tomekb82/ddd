package com.example.demo.event_sourcing;

import static io.vavr.collection.List.ofAll;

class ProductEventRepository implements ProductRepository{

    private final EventStore eventStore;

    public ProductEventRepository(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public Product load(ProductId productId){
        DomainEvents events = eventStore.loadStream(new AggregateId(productId.id()));
        return ofAll(events.events()).foldLeft(Product.of(productId), Product::apply);
    }

    @Override
    public void append(DomainEvent event) {
        eventStore.append(event);
    }

    @Override
    public DomainEvents getEvents(AggregateId aggregateId) {
        return eventStore.getEvents(aggregateId);
    }

}

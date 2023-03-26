package com.example.demo.event_sourcing


import spock.lang.Specification

class ProductServiceTest extends Specification {

    private static final ProductId PRODUCT_ID = new ProductId(UUID.randomUUID())
    private static final String PRODUCT_NAME = "TESTBOOK"

    EventStore eventStore = new PersistenceEventStore()
    ProductRepository productRepository = new ProductEventRepository(eventStore)
    ProductService productService = new ProductService(productRepository)

    def "can add new product"() {
        given:
        AggregateId aggregateId = new AggregateId(PRODUCT_ID.id());
        int price = 23
        Command command = new CreateProduct(aggregateId, PRODUCT_NAME, price)
        when:
        productService.handle(command)
        then:
        1 == productRepository.getEvents(aggregateId).events().size()
        productRepository.getEvents(aggregateId).events().get(0) instanceof ProductCreated
        aggregateId == ((ProductCreated) productRepository.getEvents(aggregateId).events().get(0)).aggregateId
        PRODUCT_NAME == ((ProductCreated) productRepository.getEvents(aggregateId).events().get(0)).name
        price == ((ProductCreated) productRepository.getEvents(aggregateId).events().get(0)).price
    }

    def "can update a product"() {
        given:
        AggregateId aggregateId = new AggregateId(PRODUCT_ID.id());
        int price = 23
        Command createCommand = new CreateProduct(aggregateId, PRODUCT_NAME, price)
        productService.handle(createCommand)
        when:
        price = 55
        Command updatePriceCommand = new UpdatePrice(aggregateId, price)
        productService.handle(updatePriceCommand)
        then:
        2 == productRepository.getEvents(aggregateId).events().size()
        productRepository.getEvents(aggregateId).events().get(0) instanceof ProductCreated
        productRepository.getEvents(aggregateId).events().get(1) instanceof PriceUpdated
        aggregateId == ((PriceUpdated) productRepository.getEvents(aggregateId).events().get(1)).aggregateId
        55 == ((PriceUpdated) productRepository.getEvents(aggregateId).events().get(1)).price
    }

    def "can restore a product"() {
        given:
        AggregateId aggregateId = new AggregateId(PRODUCT_ID.id());
        int price = 23
        Command createCommand = new CreateProduct(aggregateId, PRODUCT_NAME, price)
        productService.handle(createCommand)
        and:
        price = 55
        Command updatePriceCommand = new UpdatePrice(aggregateId, price)
        productService.handle(updatePriceCommand)
        and:
        price = 77
        updatePriceCommand = new UpdatePrice(aggregateId, price)
        productService.handle(updatePriceCommand)
        and:
        price = 2345
        updatePriceCommand = new UpdatePrice(aggregateId, price)
        productService.handle(updatePriceCommand)
        when:
        DomainEvents events = productRepository.getEvents(aggregateId)
        Product snapshot = productService.getSnapshot(aggregateId)
        then:
        4 == events.events().size()
        PRODUCT_ID == snapshot.id
        PRODUCT_NAME == snapshot.name
        2345 == snapshot.prize
    }

    Product aProduct() {
        Product product = Product.of(PRODUCT_ID)
        return product;
    }
}

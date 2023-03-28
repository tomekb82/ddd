package com.example.demo.purchase;

import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.ArrayList;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@EqualsAndHashCode
class Purchase extends Entity {

    final PurchaseId purchaseId;
    final ProductList products;
    final ProductList freeProducts;
    final ProductList intentionallyRemoveFreeProduct;
    final Instant created;

    private Purchase(PurchaseId purchaseId, Instant instant) {
        this.purchaseId = purchaseId;
        this.products = new ProductList(new ArrayList<Product>());
        this.freeProducts = new ProductList(new ArrayList<Product>());
        this.intentionallyRemoveFreeProduct = new ProductList(new ArrayList<Product>());
        this.created = instant;
    }

    static Purchase of(PurchaseId purchaseId, Instant instant) {
        return new Purchase(purchaseId, instant);
    }

    void addProduct(Product product, ExtraProductPolicy extraProductPolicy){
        var spec = new FreeProductAlreadyRemovedSpec();
        if (spec.satisfiedBy(product)){
            products.products().add(product);
            extraProductPolicy.getExtraProductsFor(product)
                    .forEach(extraProduct -> freeProducts.products().add(extraProduct));
            raise(new ProductAdded());
        }
    }

    void removeProduct(Product product, ExtraProductPolicy extraProductPolicy){
        products.products().remove(product);
        extraProductPolicy.getExtraProductsFor(product)
                .forEach(extraProduct -> freeProducts.products().remove(extraProduct));
        raise(new ProductRemoved());
    }

    public void intentionallyRemoveFreeProduct(Product product) {
        if(freeProducts.products().contains(product)){
            freeProducts.products().remove(product);
            intentionallyRemoveFreeProduct.products().add(product);
        }
        raise(new IntentionallyRemovedFreeProduct());
    }

    public void addBackFreeProduct(Product product) {
        if(intentionallyRemoveFreeProduct.products().contains(product)){
            freeProducts.products().add(product);
        }
        raise(new AddBackFreeProduct());
    }

    public String print() {
        var products = this.products.products()
                .stream()
                .map(product -> product.name)
                .sorted()
                .collect(groupingBy(identity(), counting()))
                .toString();
        var freeProducts = this.freeProducts.products()
                .stream()
                .map(product -> "[FREE] " + product.name)
                .sorted()
                .collect(groupingBy(identity(), counting()))
                .toString();
        return this.freeProducts.products().size() > 0
                ? products.concat(freeProducts)
                : products;
    }

}

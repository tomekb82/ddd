package com.example.demo.purchase;

import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

class BuyOneGetSomeFreeProductPolicy implements ExtraProductPolicy{

    private final Set<ExtraProduct> extraProducts = new HashSet<>();

    @Override
    public Set<Product> getExtraProductsFor(Product product) {
        return extraProducts
                .stream()
                .filter(extraProduct -> extraProduct.isFreeFor(product))
                .map(ExtraProduct::freeProduct)
                .collect(toSet());
    }

    public void addNewExtraProduct(ExtraProduct extraProduct) {
        extraProducts.add(extraProduct);
    }
}

package com.example.demo.purchase;

import java.util.function.Predicate;

class FreeProductAlreadyRemovedSpec implements Specification<Product> {

    @Override
    public boolean satisfiedBy(Product product) {
        return satisfiedBy(product, p -> p.getName().equals("test"));
    }

    private boolean satisfiedBy(Product product, Predicate<Product> predicate) {
        return predicate.test(product);
    }
}

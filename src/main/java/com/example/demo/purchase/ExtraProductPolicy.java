package com.example.demo.purchase;

import java.util.Set;

public interface ExtraProductPolicy {
    Set<Product> getExtraProductsFor(Product product);
}

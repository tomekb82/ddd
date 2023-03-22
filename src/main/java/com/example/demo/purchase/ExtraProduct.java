package com.example.demo.purchase;

record ExtraProduct(Product product, Product freeProduct) {

    boolean isFreeFor(Product product) {
        return this.product.equals(product);
    }
}

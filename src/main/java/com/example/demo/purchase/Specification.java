package com.example.demo.purchase;

interface Specification<T> {
    boolean satisfiedBy(T t);

   /* Specification<Product> and(Specification<T> specification);

    Specification<Product> or(Specification<T> specification);

    Specification<Product> not(Specification<T> specification);*/

}

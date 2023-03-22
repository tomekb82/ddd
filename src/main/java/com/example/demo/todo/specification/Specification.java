package com.example.demo.todo.specification;

public interface Specification<T> {
   /* boolean satisfiedBy(T t);*/

    boolean satisfiedBy(T t, Long id);

  /*  Specification<T> and(Specification<T> specification);

    Specification<T> or(Specification<T> specification);

    Specification<T> not(Specification<T> specification);*/

}

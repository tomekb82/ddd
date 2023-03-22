package com.example.demo.todo.specification;

public class FreeProductAlreadyRemovedSpec {}/*implements Specification<Product> {

    @Override
    public boolean satisfiedBy(Product product) {
        return false;
    }

    @Override
    public boolean satisfiedBy(Product product, Long id) {
        return product.getFreeProducts().products().stream()
                .noneMatch(p -> p.getFreeProductId().id().equals(id));
    }

    @Override
    public Specification<Product> and(Specification<Product> specification) {
        return null;
    }

    @Override
    public Specification<Product> or(Specification<Product> specification) {
        return null;
    }

    @Override
    public Specification<Product> not(Specification<Product> specification) {
        return null;
    }
}
*/
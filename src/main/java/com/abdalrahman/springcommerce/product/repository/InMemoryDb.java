package com.abdalrahman.springcommerce.product.repository;

import com.abdalrahman.springcommerce.product.Product;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class InMemoryDb  implements DbService {

    private final Map<Long,Product> productDb = new ConcurrentHashMap<>();


    @Override
    public void save(final Product product) {
        productDb.put(product.getId(),product);
    }

    @Override
    public void delete(final Long id) {
        productDb.remove(id);
    }

    @Override
    public void update(final Long id,final Product product) {
        productDb.put(id,product);
    }

    @Override
    public void clear() {
        productDb.clear();
    }

    @Override
    public Optional<Product> findById(final Long id) {
        return Optional.ofNullable(productDb.get(id));
    }

    @Override
    public Collection<Product> findAll() {
        return productDb.values();
    }
}

package com.abdalrahman.springcommerce.product.repository;

import com.abdalrahman.springcommerce.product.Product;

import java.util.Collection;
import java.util.Optional;

public interface DbService {

    void save(Product product);

    void update (Long id , Product product);

    void delete (Long id);

    void clear();

    Optional<Product> findById(Long id);

    Optional<Product> findBySku(String sku);

    Collection<Product> findAll();
}

package com.abdalrahman.springcommerce.product.repository;

import com.abdalrahman.springcommerce.product.Product;
import com.abdalrahman.springcommerce.shared.utils.enums.SortDirection;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

public interface DbService {

    void save(Product product);

    void update(Product product);

    void delete(Long id);

    void clear();

    Optional<Product> findById(Long id);

    Optional<Product> findBySku(String sku);

    Collection<Product> findAll();

    Collection<Product> findByBrand(String brand);

    Collection<Product> findByPriceRange(BigDecimal min, BigDecimal max);

    Collection<Product> findAllSortedByPrice(SortDirection direction);
}

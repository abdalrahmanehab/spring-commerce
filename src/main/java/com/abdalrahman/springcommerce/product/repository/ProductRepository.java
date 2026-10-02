package com.abdalrahman.springcommerce.product.repository;

import com.abdalrahman.springcommerce.product.Product;
import com.abdalrahman.springcommerce.shared.utils.enums.SortDirection;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface ProductRepository {

    Product save(Product product) ;

    Product update(Product product);

    void delete(Long id);

    void deleteAll();

    Optional<Product> findById(Long id);

    Optional<Product> findBySku(String sku);

    Collection<Product> findAll();

    Collection<Product> findAllByBrand(String brand);

    Collection<Product> findAllInPriceRange(BigDecimal min, BigDecimal max);

    Collection<Product> findAllSortedByPrice(SortDirection sortDirection);
}

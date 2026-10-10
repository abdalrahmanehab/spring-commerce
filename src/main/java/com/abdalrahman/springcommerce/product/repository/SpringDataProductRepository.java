package com.abdalrahman.springcommerce.product.repository;

import com.abdalrahman.springcommerce.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface SpringDataProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    Collection<Product> findByBrandIgnoreCase(String brand);

    Collection<Product> findByPriceBetween(BigDecimal min, BigDecimal max);
}
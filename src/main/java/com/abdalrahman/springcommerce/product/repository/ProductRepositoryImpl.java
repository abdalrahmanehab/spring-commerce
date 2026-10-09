package com.abdalrahman.springcommerce.product.repository;

import com.abdalrahman.springcommerce.product.Product;
import com.abdalrahman.springcommerce.shared.utils.enums.SortDirection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final DbService dbService;

    @Override
    public Product save(Product product) {
        dbService.save(product);
        return product;
    }

    @Override
    public Product update(Product product) {
        dbService.update(product);
        return product;
    }

    @Override
    public void delete(Long id) {
        dbService.delete(id);
    }

    @Override
    public void deleteAll() {
        dbService.clear();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return dbService.findById(id);
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return dbService.findBySku(sku);
    }

    @Override
    public Collection<Product> findAll() {
        return dbService.findAll().stream()
                .toList();
    }

    @Override
    public Collection<Product> findAllByBrand(String brand) {
        return dbService.findByBrand(brand);
    }

    @Override
    public Collection<Product> findAllInPriceRange(BigDecimal min, BigDecimal max) {
        return dbService.findByPriceRange(min, max);
    }

    @Override
    public Collection<Product> findAllSortedByPrice(SortDirection sortDirection) {
        return dbService.findAllSortedByPrice(sortDirection);
    }
}




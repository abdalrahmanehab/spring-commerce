package com.abdalrahman.springcommerce.product.repository;

import com.abdalrahman.springcommerce.product.Product;
import com.abdalrahman.springcommerce.shared.utils.enums.SortDirection;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository{

    private final DbService dbService;

    @Override
    public Product save(Product product) {
        dbService.save(product);
        log.debug("Product saved successfully with [id={}]",product.getId());
        return product;
    }

    @Override
    public Product update(Long id, Product product) {
        dbService.update(id,product);
        log.debug("Product updated successfully with [id={}]",product.getId());
        return product;
    }

    @Override
    public void delete(Long id) {
        dbService.delete(id);
        log.debug("Product deleted successfully");
    }

    @Override
    public void deleteAll() {
        dbService.clear();
        log.debug("All products have been deleted successfully");
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
    public Collection<Product> findByBrand(String brand) {
        return dbService.findAll().stream()
                .filter(product -> brand.equalsIgnoreCase(product.getBrand()))
                .toList();
    }

    @Override
    public Collection<Product> findAllInPriceRange(BigDecimal min, BigDecimal max) {
        return dbService.findAll().stream()
                .filter(product -> product.getPrice().compareTo(min) >= 0 && product.getPrice().compareTo(max) <=0)
                .toList();
    }

    @Override
    public Collection<Product> findAllSortedByPrice(SortDirection sortDirection) {
        return dbService.findAll().stream()
                .sorted((p1,p2)-> sortDirection == SortDirection.ASC
                ? p1.getPrice().compareTo(p2.getPrice())
                : p2.getPrice().compareTo(p1.getPrice()))
                .toList();
    }
}




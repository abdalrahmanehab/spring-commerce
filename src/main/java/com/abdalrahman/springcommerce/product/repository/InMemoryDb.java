package com.abdalrahman.springcommerce.product.repository;

import com.abdalrahman.springcommerce.product.Product;
import com.abdalrahman.springcommerce.shared.utils.enums.SortDirection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
@Profile("test")
public class InMemoryDb implements DbService {

    AtomicLong idGenerator = new AtomicLong(1);
    private final Map<Long, Product> productDb = new ConcurrentHashMap<>();


    @Override
    public void save(final Product product) {
        Long newId = idGenerator.incrementAndGet();
        product.setId(newId);
        productDb.put(newId, product);
        log.debug("Saved product: id={}, sku={}", newId, product.getSku());
    }

    @Override
    public void delete(final Long id) {
        productDb.remove(id);
    }

    @Override
    public void update(final Product product) {
        productDb.put(product.getId(), product);
        log.debug("Updated product: id={}, sku={}", product.getId(), product.getSku());
    }

    @Override
    public void clear() {
        final int count = productDb.size();
        productDb.clear();
        log.warn("Cleared ALL products from in-memory store: {} entries removed", count);
    }

    @Override
    public Optional<Product> findById(final Long id) {
        return Optional.ofNullable(productDb.get(id));
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return productDb.values().stream()
                .filter(product -> product.getSku().equals(sku))
                .findFirst();
    }

    @Override
    public Collection<Product> findAll() {
        return productDb.values();
    }

    @Override
    public Collection<Product> findByBrand(String brand) {
        return productDb.values().stream()
                .filter(p -> p.getBrand() != null && p.getBrand().equalsIgnoreCase(brand))
                .toList();
    }

    @Override
    public Collection<Product> findByPriceRange(BigDecimal min, BigDecimal max) {
        return productDb.values().stream()
                .filter(p -> p.getPrice() != null
                        && p.getPrice().compareTo(min) >= 0
                        && p.getPrice().compareTo(max) <= 0)
                .toList();
    }

    @Override
    public Collection<Product> findAllSortedByPrice(SortDirection direction) {
        return productDb.values().stream()
                .sorted((p1, p2) -> direction == SortDirection.ASC
                        ? p1.getPrice().compareTo(p2.getPrice())
                        : p2.getPrice().compareTo(p1.getPrice()))
                .toList();
    }
}

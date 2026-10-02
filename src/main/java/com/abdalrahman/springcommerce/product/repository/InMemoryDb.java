package com.abdalrahman.springcommerce.product.repository;

import com.abdalrahman.springcommerce.product.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
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
}

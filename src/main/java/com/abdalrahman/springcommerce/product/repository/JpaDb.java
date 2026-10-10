package com.abdalrahman.springcommerce.product.repository;

import com.abdalrahman.springcommerce.product.Product;
import com.abdalrahman.springcommerce.shared.utils.enums.SortDirection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Component
@Profile("local")
@RequiredArgsConstructor
public class JpaDb implements DbService {

    private final SpringDataProductRepository springDataRepo;

    @Override
    public void save(final Product product) {
        springDataRepo.save(product);
        log.debug("Saved product to PostgreSQL: sku={}", product.getSku());
    }

    @Override
    public void update(final Product product) {
        springDataRepo.save(product);
        log.debug("Updated product in PostgreSQL: id={}, sku={}", product.getId(), product.getSku());
    }

    @Override
    public void delete(final Long id) {
        springDataRepo.deleteById(id);
        log.debug("Deleted product from PostgreSQL: id={}", id);
    }

    @Override
    public void clear() {
        long count = springDataRepo.count();
        springDataRepo.deleteAll();
        log.warn("Cleared ALL products from PostgreSQL: {} entries removed", count);
    }

    @Override
    public Optional<Product> findById(final Long id) {
        return springDataRepo.findById(id);
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return springDataRepo.findBySku(sku);
    }

    @Override
    public Collection<Product> findAll() {
        return springDataRepo.findAll();
    }

    @Override
    public Collection<Product> findByBrand(String brand) {
        return springDataRepo.findByBrandIgnoreCase(brand);
    }

    @Override
    public Collection<Product> findByPriceRange(BigDecimal min, BigDecimal max) {
        return springDataRepo.findByPriceBetween(min, max);
    }

    @Override
    public Collection<Product> findAllSortedByPrice(SortDirection direction) {
        Sort sort = (direction == SortDirection.ASC)
                ? Sort.by(Sort.Direction.ASC, "price")
                : Sort.by(Sort.Direction.DESC, "price");
        return springDataRepo.findAll(sort);
    }
}
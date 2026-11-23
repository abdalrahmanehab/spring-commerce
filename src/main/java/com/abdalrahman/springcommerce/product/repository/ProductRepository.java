package com.abdalrahman.springcommerce.product.repository;

import com.abdalrahman.springcommerce.product.Product;
import com.abdalrahman.springcommerce.shared.utils.enums.SortDirection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    Collection<Product> findAllByBrand(String brand);

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :min AND :max")
    Collection<Product> findAllInPriceRange(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    default Product update(Product product) {
        return save(product);
    }

    default void delete(Long id) {
        deleteById(id);
    }

    default Collection<Product> findAllSortedByPrice(SortDirection sortDirection) {
        Sort sort = sortDirection == SortDirection.ASC
                ? Sort.by("price").ascending()
                : Sort.by("price").descending();
        return findAll(sort);
    }
}
package com.abdalrahman.springcommerce.product.service;

import com.abdalrahman.springcommerce.product.dtos.ProductRequest;
import com.abdalrahman.springcommerce.product.dtos.ProductResponse;
import com.abdalrahman.springcommerce.shared.errors.exceptions.DuplicateSkuException;
import com.abdalrahman.springcommerce.shared.errors.exceptions.ResourceNotFoundException;
import com.abdalrahman.springcommerce.shared.utils.enums.SortDirection;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    ProductResponse create(ProductRequest request) throws DuplicateSkuException;

    ProductResponse getById(Long id) throws ResourceNotFoundException;

    ProductResponse getBySku(String sku) throws ResourceNotFoundException;

    List<ProductResponse> getAll();

    List<ProductResponse> getAllByBrand(String brand);

    List<ProductResponse> getAllInPriceRange(BigDecimal min, BigDecimal max);

    List<ProductResponse> getAllSortedByPrice(SortDirection direction);

    ProductResponse update(Long id, ProductRequest request) throws ResourceNotFoundException, DuplicateSkuException;

    void delete(Long id) throws ResourceNotFoundException;

    void deleteAll();
}
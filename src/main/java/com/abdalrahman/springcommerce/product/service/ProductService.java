package com.abdalrahman.springcommerce.product.service;

import com.abdalrahman.springcommerce.product.dtos.ProductRequest;
import com.abdalrahman.springcommerce.product.dtos.ProductResponse;
import com.abdalrahman.springcommerce.shared.errors.exceptions.DuplicateSkuException;
import com.abdalrahman.springcommerce.shared.utils.enums.SortDirection;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request) throws DuplicateSkuException;

    ProductResponse getProductById(Long id);

    ProductResponse getProductBySku(String sku);

    List<ProductResponse> getAllProducts();

    List<ProductResponse> getProductsByBrand(String brand);

    List<ProductResponse> getProductsByPriceRange(BigDecimal min, BigDecimal max);

    List<ProductResponse> getAllProductsSortedByPrice(SortDirection direction);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);


}
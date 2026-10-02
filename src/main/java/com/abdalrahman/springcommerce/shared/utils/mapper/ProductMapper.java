package com.abdalrahman.springcommerce.shared.utils.mapper;

import com.abdalrahman.springcommerce.product.Product;
import com.abdalrahman.springcommerce.product.dtos.ProductRequest;
import com.abdalrahman.springcommerce.product.dtos.ProductResponse;


public class ProductMapper {
    private ProductMapper() {
        throw new AssertionError("Utility class , Can not be instantiated!");
    }

    public static ProductResponse toProductResponse(final Product product) {
        return new ProductResponse(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getBrand()
        );
    }

    public static Product toProduct(final ProductRequest productRequest) {
        return Product.builder()
                .sku(productRequest.getSku())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .brand(productRequest.getBrand())
                .build();
    }
}

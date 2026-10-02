package com.abdalrahman.springcommerce.product.dtos;

import java.math.BigDecimal;


public record ProductResponse(
        Long id,
        String sku,
        String name,
        String description,
        BigDecimal price,
        Integer quantity,
        String brand) {
}

package product.dtos;

import lombok.*;

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

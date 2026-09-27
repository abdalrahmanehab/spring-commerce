package com.abdalrahman.springcommerce.product.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "{product.sku.required}")
    private String sku;

    @NotBlank(message = "{product.name.required}")
    private String name;

    @NotBlank(message = "{product.description.required}")
    private String description;

    @NotNull(message = "{product.price.required}")
    @DecimalMin(value = "0.0", inclusive = true, message = "{product.price.negative}")
    @Digits(integer = 10, fraction = 2, message = "{product.price.format}")
    private BigDecimal price;

    @NotNull(message = "{product.quantity.required}")
    @Min(value = 0, message = "{product.quantity.negative}")
    private Integer quantity;

    @NotBlank(message = "{product.brand.required}")
    private String brand;
}
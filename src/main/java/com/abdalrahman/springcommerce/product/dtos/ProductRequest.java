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
    @NotBlank (message = "SKU is required")
    private String sku;

    @NotBlank (message = "Name is required")
    private String name;

    @NotBlank (message = "Description is required")
    private String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true , message = ("Price can not be negative"))
    @Digits(integer = 10, fraction = 2, message = "Price format must be up to 10 digits and 2 decimals")
    private BigDecimal price;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity can not be negative")
    private Integer quantity;

    @NotBlank(message = "Brand is required")
    private String brand;
}

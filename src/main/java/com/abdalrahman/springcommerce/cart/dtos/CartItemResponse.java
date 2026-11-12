package com.abdalrahman.springcommerce.cart.dtos;

import java.math.BigDecimal;

public record CartItemResponse(Long productId,
                               String productName,
                               BigDecimal unitPrice,
                               Integer quantity,
                               BigDecimal subtotal) {
}

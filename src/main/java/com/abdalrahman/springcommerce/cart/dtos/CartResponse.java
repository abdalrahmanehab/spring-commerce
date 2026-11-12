package com.abdalrahman.springcommerce.cart.dtos;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(Long id,
                           Long userId,
                           List<CartItemResponse> items,
                           BigDecimal totalPrice) {
}

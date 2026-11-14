package com.abdalrahman.springcommerce.order.dtos;

import com.abdalrahman.springcommerce.order.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(Long id,
                            Long userId,
                            OrderStatus status,
                            List<OrderItemResponse> items,
                            BigDecimal totalPrice,
                            LocalDateTime createdAt,
                            LocalDateTime updatedAt) {
}

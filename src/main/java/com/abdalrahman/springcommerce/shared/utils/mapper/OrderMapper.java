package com.abdalrahman.springcommerce.shared.utils.mapper;

import com.abdalrahman.springcommerce.order.Order;
import com.abdalrahman.springcommerce.order.OrderItem;
import com.abdalrahman.springcommerce.order.dtos.OrderItemResponse;
import com.abdalrahman.springcommerce.order.dtos.OrderResponse;
import com.abdalrahman.springcommerce.product.dtos.ProductResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class OrderMapper {

    private OrderMapper() {
        throw new AssertionError("Utility class , Can not be instantiated!");
    }

    public static OrderResponse toOrderResponse(final Order order) {

        final List<OrderItemResponse> items = order.getItems().stream()
                .map(OrderMapper::toOrderItemResponse)
                .toList();

        final BigDecimal totalPrice = items.stream()
                .map(OrderItemResponse::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                order.getStatus(),
                items,
                totalPrice,
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }

    private static OrderItemResponse toOrderItemResponse(final OrderItem orderItem) {

        return new OrderItemResponse(
                orderItem.getProductId(),
                orderItem.getProductName(),
                orderItem.getQuantity(),
                orderItem.getPriceAtPurchase(),
                orderItem.getSubtotal()
        );
    }
}

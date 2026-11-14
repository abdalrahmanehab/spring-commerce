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

    public static OrderResponse toOrderResponse(final Order order, final Map<Long, ProductResponse> products) {

        final List<OrderItemResponse> items = order.getItems().stream()
                .map(orderItem -> toOrderItemResponse(orderItem, products.get(orderItem.getProductId())))
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

    private static OrderItemResponse toOrderItemResponse(final OrderItem orderItem, ProductResponse product) {

        return new OrderItemResponse(
                orderItem.getProductId(),
                product.name(),
                orderItem.getQuantity(),
                orderItem.getPriceAtPurchase(),
                orderItem.getSubtotal()
        );
    }
}

package com.abdalrahman.springcommerce.shared.utils.mapper;

import com.abdalrahman.springcommerce.cart.Cart;
import com.abdalrahman.springcommerce.cart.CartItem;
import com.abdalrahman.springcommerce.cart.dtos.CartItemResponse;
import com.abdalrahman.springcommerce.cart.dtos.CartResponse;
import com.abdalrahman.springcommerce.product.dtos.ProductResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class CartMapper {

    private CartMapper() {
        throw new AssertionError("Utility class , Can not be instantiated!");
    }

    public static CartResponse toCartResponse(final Cart cart, final Map<Long, ProductResponse> products) {
        final List<CartItemResponse> items = cart.getItems().stream()
                .map(item -> toCartItemResponse(item, products.get(item.getProductId())))
                .toList();

        final BigDecimal totalPrice = items.stream()
                .map(CartItemResponse::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(
                cart.getId(),
                cart.getUserId(),
                items,
                totalPrice
        );
    }

    public static CartItemResponse toCartItemResponse(final CartItem item, final ProductResponse product) {

        final BigDecimal subtotal = product.price()
                .multiply(BigDecimal.valueOf(item.getQuantity()));

        return new CartItemResponse(
                item.getProductId(),
                product.name(),
                product.price(),
                item.getQuantity(),
                subtotal
        );
    }

    public static CartResponse emptyCart(final Long userId) {
        return new CartResponse(
                null,
                userId,
                List.of(),
                BigDecimal.ZERO
        );
    }
}

package com.abdalrahman.springcommerce.cart.controller;

import com.abdalrahman.springcommerce.cart.dtos.AddToCartRequest;
import com.abdalrahman.springcommerce.cart.dtos.CartResponse;
import com.abdalrahman.springcommerce.cart.dtos.UpdateCartItemRequest;
import com.abdalrahman.springcommerce.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/carts/{userId}")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CartResponse getCart(@PathVariable Long userId) {
        return cartService.getCart(userId);
    }

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.OK)
    public CartResponse addItem(@PathVariable Long userId, @Valid @RequestBody AddToCartRequest request) {
        return cartService.addItem(userId, request);
    }

    @PutMapping("/items/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public CartResponse updateItemQuantity(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateCartItemRequest request) {
        return cartService.updateItemQuantity(userId, productId, request);
    }

    @DeleteMapping("/items/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public CartResponse removeItem(@PathVariable Long userId, @PathVariable Long productId) {
        return cartService.removeItem(userId, productId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
    }

}

package com.abdalrahman.springcommerce.cart.controller;

import com.abdalrahman.springcommerce.cart.dtos.AddToCartRequest;
import com.abdalrahman.springcommerce.cart.dtos.CartResponse;
import com.abdalrahman.springcommerce.cart.dtos.UpdateCartItemRequest;
import com.abdalrahman.springcommerce.cart.service.CartService;
import com.abdalrahman.springcommerce.security.CurrentUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CurrentUser currentUser;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CartResponse getCart() {
        return cartService.getCart(currentUser.id());
    }

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.OK)
    public CartResponse addItem(@Valid @RequestBody AddToCartRequest request) {
        return cartService.addItem(currentUser.id(), request);
    }

    @PutMapping("/items/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public CartResponse updateItemQuantity(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateCartItemRequest request) {
        return cartService.updateItemQuantity(currentUser.id(), productId, request);
    }

    @DeleteMapping("/items/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public CartResponse removeItem(@PathVariable Long productId) {
        return cartService.removeItem(currentUser.id(), productId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart() {
        cartService.clearCart(currentUser.id());
    }

}

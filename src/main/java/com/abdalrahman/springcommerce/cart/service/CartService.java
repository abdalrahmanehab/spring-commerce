package com.abdalrahman.springcommerce.cart.service;

import com.abdalrahman.springcommerce.cart.dtos.AddToCartRequest;
import com.abdalrahman.springcommerce.cart.dtos.CartResponse;
import com.abdalrahman.springcommerce.cart.dtos.UpdateCartItemRequest;
import com.abdalrahman.springcommerce.shared.errors.exceptions.InsufficientStockException;
import com.abdalrahman.springcommerce.shared.errors.exceptions.ResourceNotFoundException;

public interface CartService {

    CartResponse getCart(Long userId) throws ResourceNotFoundException;

    CartResponse addItem(Long userId, AddToCartRequest request)
            throws ResourceNotFoundException, InsufficientStockException;

    CartResponse updateItemQuantity(Long userId, Long productId, UpdateCartItemRequest request)
            throws ResourceNotFoundException, InsufficientStockException;

    CartResponse removeItem(Long userId, Long productId) throws ResourceNotFoundException;

    void clearCart(Long userId) throws ResourceNotFoundException;
}
package com.abdalrahman.springcommerce.order.service;

import com.abdalrahman.springcommerce.order.OrderStatus;
import com.abdalrahman.springcommerce.order.dtos.OrderResponse;
import com.abdalrahman.springcommerce.shared.errors.exceptions.EmptyCartException;
import com.abdalrahman.springcommerce.shared.errors.exceptions.InsufficientStockException;
import com.abdalrahman.springcommerce.shared.errors.exceptions.InvalidOrderStateException;
import com.abdalrahman.springcommerce.shared.errors.exceptions.ResourceNotFoundException;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(Long userId)
            throws ResourceNotFoundException, EmptyCartException, InsufficientStockException;

    OrderResponse getById(Long orderId) throws ResourceNotFoundException;

    List<OrderResponse> getByUserId(Long userId) throws ResourceNotFoundException;

    OrderResponse updateStatus(Long orderId, OrderStatus newStatus)
            throws ResourceNotFoundException, InvalidOrderStateException;
}
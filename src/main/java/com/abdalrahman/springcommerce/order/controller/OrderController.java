package com.abdalrahman.springcommerce.order.controller;

import com.abdalrahman.springcommerce.order.Order;
import com.abdalrahman.springcommerce.order.dtos.OrderResponse;
import com.abdalrahman.springcommerce.order.dtos.UpdateOrderRequest;
import com.abdalrahman.springcommerce.order.service.OrderService;
import com.abdalrahman.springcommerce.security.CurrentUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;
    private final CurrentUser currentUser;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse placeOrder() {
        return orderService.placeOrder(currentUser.id());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getMyOrders() {
        return orderService.getByUserId(currentUser.id());
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getById(@PathVariable @Positive final Long orderId) {
        OrderResponse order = orderService.getById(orderId);
        currentUser.checkOwnerAndAdmin(order.userId());
        return order;
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getByUserId(@PathVariable @Positive final Long userId) {
        return orderService.getByUserId(userId);
    }

    @PatchMapping("/{orderId}/status")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse updateOrderStatus(
            @Valid @RequestBody final UpdateOrderRequest request,
            @PathVariable @Positive final Long orderId) {
        return orderService.updateStatus(orderId, request.getStatus());
    }
}
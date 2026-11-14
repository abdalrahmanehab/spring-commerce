package com.abdalrahman.springcommerce.order.service;

import com.abdalrahman.springcommerce.cart.dtos.CartItemResponse;
import com.abdalrahman.springcommerce.cart.dtos.CartResponse;
import com.abdalrahman.springcommerce.cart.service.CartService;
import com.abdalrahman.springcommerce.order.Order;
import com.abdalrahman.springcommerce.order.OrderItem;
import com.abdalrahman.springcommerce.order.OrderStatus;
import com.abdalrahman.springcommerce.order.dtos.OrderResponse;
import com.abdalrahman.springcommerce.order.repository.OrderRepository;
import com.abdalrahman.springcommerce.product.dtos.ProductResponse;
import com.abdalrahman.springcommerce.product.service.ProductService;
import com.abdalrahman.springcommerce.shared.errors.exceptions.EmptyCartException;
import com.abdalrahman.springcommerce.shared.errors.exceptions.InsufficientStockException;
import com.abdalrahman.springcommerce.shared.errors.exceptions.InvalidOrderStateException;
import com.abdalrahman.springcommerce.shared.errors.exceptions.ResourceNotFoundException;
import com.abdalrahman.springcommerce.shared.utils.mapper.OrderMapper;
import com.abdalrahman.springcommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;


    @Override
    @Transactional
    public OrderResponse placeOrder(Long userId)
            throws ResourceNotFoundException, EmptyCartException, InsufficientStockException {
        userService.getById(userId);

        CartResponse cartResponse = cartService.getCart(userId);

        if (cartResponse.items().isEmpty()) {
            log.warn("Checkout rejected, cart is empty [userId={}]", userId);
            throw new EmptyCartException("Cart it Empty!");
        }

        log.info("Placing order [userId={}, items={}]", userId, cartResponse.items().size());
        Order order = new Order(userId, OrderStatus.PENDING);

        for (CartItemResponse item : cartResponse.items()) {
            ProductResponse product = productService.decreaseStock(item.productId(), item.quantity());
            order.addItem(new OrderItem(product.id(), product.name(), item.quantity(), product.price()));
            log.debug("Stock reserved [productId={}, quantity={}]", product.id(), item.quantity());
        }

        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(userId);

        log.info("Order placed [orderId={}, userId={}, items={}]",
                savedOrder.getId(), userId, savedOrder.getItems().size());
        return OrderMapper.toOrderResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getById(Long orderId) throws ResourceNotFoundException {
        return orderRepository.findById(orderId)
                .map(OrderMapper::toOrderResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getByUserId(Long userId) throws ResourceNotFoundException {
        userService.getById(userId);

        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(OrderMapper::toOrderResponse)
                .toList();
    }

    @Override
    @Transactional
    public OrderResponse updateStatus(final Long orderId, final OrderStatus newStatus)
            throws ResourceNotFoundException, InvalidOrderStateException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        order.changeStatus(newStatus);
        if (newStatus == OrderStatus.CANCELLED) {
            order.getItems().forEach(item ->
                    productService.increaseStock(item.getProductId(), item.getQuantity())
            );
        }

        Order savedOrder = orderRepository.save(order);
        log.info("Order status updated [id={}, status={}]", savedOrder.getId(), savedOrder.getStatus());

        return OrderMapper.toOrderResponse(savedOrder);
    }
}

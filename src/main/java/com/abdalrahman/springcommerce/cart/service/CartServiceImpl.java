package com.abdalrahman.springcommerce.cart.service;

import com.abdalrahman.springcommerce.cart.Cart;
import com.abdalrahman.springcommerce.cart.CartItem;
import com.abdalrahman.springcommerce.cart.dtos.AddToCartRequest;
import com.abdalrahman.springcommerce.cart.dtos.CartResponse;
import com.abdalrahman.springcommerce.cart.dtos.UpdateCartItemRequest;
import com.abdalrahman.springcommerce.cart.repository.CartRepository;
import com.abdalrahman.springcommerce.product.dtos.ProductResponse;
import com.abdalrahman.springcommerce.product.service.ProductService;
import com.abdalrahman.springcommerce.shared.errors.exceptions.InsufficientStockException;
import com.abdalrahman.springcommerce.shared.errors.exceptions.ResourceNotFoundException;
import com.abdalrahman.springcommerce.shared.utils.mapper.CartMapper;
import com.abdalrahman.springcommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final UserService userService;

    private Cart findOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId).
                orElseGet(() -> cartRepository.save(new Cart(userId)));
    }

    private CartResponse buildResponse(Cart cart) {
        Map<Long, ProductResponse> products = cart.getItems().stream()
                .map(CartItem::getProductId)
                .distinct()
                .collect(Collectors.toMap(
                        Function.identity(),
                        productService::getById
                ));

        return CartMapper.toCartResponse(cart, products);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCart(Long userId) throws ResourceNotFoundException {
        userService.getById(userId);

        return cartRepository.findByUserId(userId)
                .map(this::buildResponse)
                .orElseGet(() -> CartMapper.emptyCart(userId));
    }

    @Override
    @Transactional
    public CartResponse addItem(Long userId, AddToCartRequest request) throws ResourceNotFoundException, InsufficientStockException {
        userService.getById(userId);

        Cart cart = findOrCreateCart(userId);
        ProductResponse product = productService.getById(request.getProductId());

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(request.getProductId()))
                .findFirst();

        int newQuantity = existingItem.map(CartItem::getQuantity).orElse(0) + request.getQuantity();

        if (product.quantity() < newQuantity) {
            log.warn("Insufficient stock [productId={}, available={}, requested={}]",
                    request.getProductId(), product.quantity(), newQuantity);
            throw new InsufficientStockException("Insufficient stock for product with ID: " + request.getProductId());
        }

        existingItem.ifPresentOrElse(
                item -> {
                    item.setQuantity(newQuantity);
                    cart.touch();
                },
                () -> cart.addItem(new CartItem(request.getProductId(), newQuantity))
        );

        log.info("Item added to cart [userId={}, productId={}, quantity={}]", userId, request.getProductId(), newQuantity);

        return buildResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse updateItemQuantity(Long userId, Long productId, UpdateCartItemRequest request) throws ResourceNotFoundException, InsufficientStockException {
        userService.getById(userId);
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user with ID: " + userId));


        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID: " + productId + " not found in cart"));


        ProductResponse product = productService.getById(productId);

        if (request.getQuantity() > product.quantity()) {
            log.warn("Insufficient stock to update [productId={}, available={}, requested={}]",
                    productId, product.quantity(), request.getQuantity());
            throw new InsufficientStockException("Insufficient stock for product with ID: " + productId);
        }

        existingItem.setQuantity(request.getQuantity());
        cart.touch();
        log.info("Cart item updated [userId={}, productId={}, quantity={}]", userId, productId, request.getQuantity());

        return buildResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse removeItem(Long userId, Long productId) throws ResourceNotFoundException {
        userService.getById(userId);

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user with ID: " + userId));

        CartItem itemToRemove = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID: " + productId + " not found in cart"));

        cart.removeItem(itemToRemove);
        log.info("Item removed from cart [userId={}, productId={}]", userId, productId);

        return buildResponse(cart);
    }

    @Override
    @Transactional
    public void clearCart(Long userId) throws ResourceNotFoundException {
        userService.getById(userId);

        cartRepository.findByUserId(userId).ifPresent(cart -> {
            cart.clear();
            log.info("Cart cleared [userId={}]", userId);
        });
    }
}

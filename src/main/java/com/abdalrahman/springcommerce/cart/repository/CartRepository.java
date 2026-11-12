package com.abdalrahman.springcommerce.cart.repository;

import com.abdalrahman.springcommerce.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUserId(Long id);
}
package com.abdalrahman.springcommerce.order.repository;

import com.abdalrahman.springcommerce.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Long, Order> {
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
}

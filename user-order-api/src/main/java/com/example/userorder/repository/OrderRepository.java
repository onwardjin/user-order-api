package com.example.userorder.repository;

import com.example.userorder.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    Optional<Order> findByUser_IdAndId(Long userId, Long orderId);
}
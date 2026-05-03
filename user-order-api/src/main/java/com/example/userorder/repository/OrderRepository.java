package com.example.userorder.repository;

import com.example.userorder.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser_Id(Long userId);

    Optional<Order> findByUser_IdAndId(Long userId, Long orderId);
}
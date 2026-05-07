package com.example.userorder.repository;

import com.example.userorder.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    Optional<Order> findByUser_IdAndId(Long userId, Long orderId);

    @Modifying
    @Query("DELETE FROM Order o WHERE o.user.id=:userId AND o.id=:orderId")
    int deleteByUser_IdAndId(
            @Param("userId") Long userId,
            @Param("orderId") Long orderId
    );
}
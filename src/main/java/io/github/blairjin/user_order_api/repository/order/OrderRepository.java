package io.github.blairjin.user_order_api.repository.order;

import io.github.blairjin.user_order_api.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    Optional<Order> findByUserIdAndId(Long userId, Long id);
}
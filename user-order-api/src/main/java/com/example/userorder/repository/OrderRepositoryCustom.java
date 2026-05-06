package com.example.userorder.repository;

import com.example.userorder.dto.order.OrderSearchCondition;
import com.example.userorder.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface OrderRepositoryCustom {
    Slice<Order> searchOrders(
            Long userId,
            OrderSearchCondition condition,
            Pageable pageable
    );
}

package com.example.userorder.repository;

import com.example.userorder.dto.order.OrderSearchCondition;
import com.example.userorder.entity.Order;
import com.example.userorder.entity.OrderStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static com.example.userorder.entity.QOrder.order;

public class OrderRepositoryImpl implements OrderRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Slice<Order> searchOrders(
            Long userId,
            OrderSearchCondition condition,
            Pageable pageable
    ) {
        List<Order> orders = queryFactory
                .selectFrom(order)
                .where(
                        productNameContain(condition.productName()),
                        orderStatus(condition.orderStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = orders.size() > pageable.getPageSize();

        if (hasNext) {
            orders.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(orders, pageable, hasNext);
    }

    private BooleanExpression userIdEq(Long userId) {
        return order.user.id.eq(userId);
    }

    private BooleanExpression productNameContain(String productName) {
        if (productName == null || productName.isBlank()) {
            return null;
        }
        return order.product.name.containsIgnoreCase(productName);
    }

    private BooleanExpression orderStatus(OrderStatus orderStatus) {
        if (orderStatus == null) {
            return null;
        }

        return order.orderStatus.eq(orderStatus);
    }
}

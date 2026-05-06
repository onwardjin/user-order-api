package com.example.userorder.repository;

import com.example.userorder.dto.order.OrderSearchCondition;
import com.example.userorder.entity.Order;
import com.querydsl.core.BooleanBuilder;
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
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(order.user.id.eq(userId));

        if (condition != null && condition.productName() != null && !condition.productName().isBlank()) {
            builder.and(order.product.name.containsIgnoreCase(condition.productName()));

        }

        List<Order> orders = queryFactory
                .selectFrom(order)
                .join(order.product).fetchJoin()
                .join(order.user).fetchJoin()
                .where(builder)
                .orderBy(order.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = orders.size() > pageable.getPageSize();

        if (hasNext) {
            orders.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(orders, pageable, hasNext);
    }
}

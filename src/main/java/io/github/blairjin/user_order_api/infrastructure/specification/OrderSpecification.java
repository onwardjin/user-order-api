package io.github.blairjin.user_order_api.infrastructure.specification;

import io.github.blairjin.user_order_api.domain.order.Order;
import io.github.blairjin.user_order_api.domain.order.OrderStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderSpecification {

    public static Specification<Order> userIdEq(Long userId) {
        return (root, query, cb) ->
                cb.equal(root.get("userId"), userId);
    }

    public static Specification<Order> statusEq(OrderStatus status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("orderStatus"), status);
    }

    public static Specification<Order> totalPriceGoe(Long minPrice) {
        return (root, query, cb) ->
                minPrice == null ? null : cb.greaterThanOrEqualTo(root.get("totalPrice"), minPrice);
    }

    public static Specification<Order> totalPriceLoe(Long maxPrice) {
        return (root, query, cb) ->
                maxPrice == null ? null : cb.lessThanOrEqualTo(root.get("totalPrice"), maxPrice);
    }

    public static Specification<Order> createdAtGoe(LocalDate startDate){
        return ((root, query, cb) ->
                startDate==null ? null : cb.greaterThanOrEqualTo(root.get("createdAt"), startDate.atStartOfDay()));
    }

    public static Specification<Order> createdAtLoe(LocalDate endDate){
        return ((root, query, cb) ->
                endDate==null ? null : cb.lessThan(root.get("createdAt"), endDate.plusDays(1).atStartOfDay()));
    }
}
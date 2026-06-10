package io.github.blairjin.user_order_api.infrastructure.specification;

import io.github.blairjin.user_order_api.domain.order.Order;
import io.github.blairjin.user_order_api.domain.order.OrderStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
}
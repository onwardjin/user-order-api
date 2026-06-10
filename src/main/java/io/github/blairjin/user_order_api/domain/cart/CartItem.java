package io.github.blairjin.user_order_api.domain.cart;

import io.github.blairjin.user_order_api.domain.order.vo.OrderQuantity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@Table(name = "cart_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long cartId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private int orderQuantity;

    private CartItem(Long cartId, Long productId, OrderQuantity orderQuantity){
        this.cartId = Objects.requireNonNull(cartId);
        this.productId = Objects.requireNonNull(productId);
        this.orderQuantity = Objects.requireNonNull(orderQuantity).value();
    }

    public static CartItem create(Long cartId, Long productId, OrderQuantity orderQuantity) {
        return new CartItem(cartId, productId, orderQuantity);
    }

    public void update(OrderQuantity orderQuantity){
        this.orderQuantity = orderQuantity.value();
    }

    public void increase(OrderQuantity orderQuantity){
        int result = this.orderQuantity + orderQuantity.value();
        this.orderQuantity = OrderQuantity.of(result).value();
    }
}
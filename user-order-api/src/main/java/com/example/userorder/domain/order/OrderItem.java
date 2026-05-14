package com.example.userorder.domain.order;

import com.example.userorder.domain.common.BaseTimeEntity;
import com.example.userorder.domain.common.vo.Money;
import com.example.userorder.domain.order.vo.OrderQuantity;
import com.example.userorder.domain.product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "order_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Embedded
    private OrderQuantity orderQuantity;

    @Embedded
    private Money unitPrice;

    private OrderItem(Order order, Product product, OrderQuantity orderQuantity) {
        this.order = order;
        this.product = product;
        this.orderQuantity = orderQuantity;
        this.unitPrice = product.getUnitPrice();
    }

    static OrderItem createOrderItem(Order order, Product product, OrderQuantity orderQuantity) {
        return new OrderItem(order, product, orderQuantity);
    }

    public Money calculateTotalPrice() {
        return unitPrice.multiply(orderQuantity.value());
    }
}
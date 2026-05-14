package com.example.userorder.domain.order;

import com.example.userorder.domain.common.BaseTimeEntity;
import com.example.userorder.domain.common.vo.Money;
import com.example.userorder.domain.order.vo.OrderQuantity;
import com.example.userorder.domain.product.Product;
import com.example.userorder.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Embedded
    private Money totalPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private Order(User user) {
        this.user = user;
        this.totalPrice = Money.zero();
        this.orderStatus = OrderStatus.PAYMENT_PENDING;
    }

    public static Order createOrder(User user) {
        return new Order(user);
    }

    public void addOrderItem(Product product, OrderQuantity orderQuantity) {
        product.decrease(orderQuantity);

        OrderItem orderItem = OrderItem.createOrderItem(this, product, orderQuantity);
        orderItems.add(orderItem);
        
        this.totalPrice = this.totalPrice.add(orderItem.calculateTotalPrice());
    }
}
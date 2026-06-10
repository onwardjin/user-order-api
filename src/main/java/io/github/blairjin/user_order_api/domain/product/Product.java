package io.github.blairjin.user_order_api.domain.product;

import io.github.blairjin.user_order_api.application.product.command.ProductCreateCommand;
import io.github.blairjin.user_order_api.application.product.command.ProductUpdateCommand;
import io.github.blairjin.user_order_api.domain.common.BaseTimeEntity;
import io.github.blairjin.user_order_api.domain.common.vo.Money;
import io.github.blairjin.user_order_api.domain.order.vo.OrderQuantity;
import io.github.blairjin.user_order_api.domain.product.vo.ProductName;
import io.github.blairjin.user_order_api.domain.product.vo.StockQuantity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long userId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private int stockQuantity;

    @Column(nullable = false)
    private long unitPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    private Product(Long userId, ProductName productName, StockQuantity stockQuantity, Money unitPrice, ProductStatus productStatus){
        this.userId = Objects.requireNonNull(userId);
        this.productName = Objects.requireNonNull(productName).value();
        this.stockQuantity = Objects.requireNonNull(stockQuantity).value();
        this.unitPrice = Objects.requireNonNull(unitPrice).value();
        this.productStatus = Objects.requireNonNull(productStatus);
    }

    public static Product create(Long userId, ProductCreateCommand command){
        return new Product(
                userId,
                command.productName(),
                command.stockQuantity(),
                command.unitPrice(),
                ProductStatus.ACTIVE
        );
    }

    public void update(ProductUpdateCommand command){
        Objects.requireNonNull(command);

        if(command.productName()!=null){
            this.productName = command.productName().value();
        }

        if(command.stockQuantity()!=null){
            this.stockQuantity = command.stockQuantity().value();
        }

        if(command.unitPrice()!=null){
            this.unitPrice = command.unitPrice().value();
        }

        if(command.productStatus()!=null){
            this.productStatus = command.productStatus();
        }
    }

    public void increase(int orderQuantity){
        int result = stockQuantity + orderQuantity;
        this.stockQuantity = StockQuantity.of(result).value();
    }

    public void decrease(OrderQuantity orderQuantity){
        Objects.requireNonNull(orderQuantity);

        int result = stockQuantity - orderQuantity.value();
        this.stockQuantity = StockQuantity.of(result).value();
    }

    public void decrease(int orderQuantity){
        decrease(OrderQuantity.of(orderQuantity));
    }
}
package com.example.userorder.domain.product;

import com.example.userorder.domain.common.BaseTimeEntity;
import com.example.userorder.domain.common.vo.Money;
import com.example.userorder.domain.order.vo.OrderQuantity;
import com.example.userorder.domain.product.vo.ProductName;
import com.example.userorder.domain.product.vo.RestockQuantity;
import com.example.userorder.domain.product.vo.StockQuantity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ProductName name;

    @Embedded
    private Money unitPrice;

    @Embedded
    private StockQuantity stockQuantity;

    private Product(ProductName name, Money unitPrice, StockQuantity stockQuantity) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.stockQuantity = stockQuantity;
    }

    public static Product createProduct(ProductName name, Money unitPrice, StockQuantity stockQuantity) {
        return new Product(name, unitPrice, stockQuantity);
    }

    public void updateName(ProductName name) {
        this.name = name;
    }

    public void updateUnitPrice(Money unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void updateStockQuantity(StockQuantity stockQuantity) {
        this.stockQuantity = stockQuantity;
    }


    public void increase(RestockQuantity restockQuantity) {
        this.stockQuantity = stockQuantity.increaseStock(restockQuantity);
    }

    public void decrease(OrderQuantity orderQuantity) {
        this.stockQuantity = stockQuantity.decreaseStock(orderQuantity);
    }
}
package com.example.userorder.domain.product.vo;

import com.example.userorder.common.StringValidator;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductName {
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 50;

    private String productName;

    private ProductName(String productName) {
        StringValidator.validate(productName, MIN_LENGTH, MAX_LENGTH);
        this.productName = productName;
    }

    public static ProductName of(String productName) {
        return new ProductName(productName);
    }

    public String value() {
        return productName;
    }
}
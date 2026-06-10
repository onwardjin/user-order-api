package io.github.blairjin.user_order_api.application.product.command;

import io.github.blairjin.user_order_api.domain.common.vo.Money;
import io.github.blairjin.user_order_api.domain.product.vo.ProductName;
import io.github.blairjin.user_order_api.domain.product.vo.StockQuantity;

public record ProductCreateCommand(
        ProductName productName,
        StockQuantity stockQuantity,
        Money unitPrice
) {
}
package io.github.blairjin.user_order_api.application.order.command;

import io.github.blairjin.user_order_api.domain.common.vo.Money;
import io.github.blairjin.user_order_api.domain.order.vo.OrderQuantity;
import io.github.blairjin.user_order_api.domain.product.vo.ProductName;

public record OrderItemCommand(
        Long productId,
        ProductName productName,
        OrderQuantity orderQuantity,
        Money unitPrice
) {
}
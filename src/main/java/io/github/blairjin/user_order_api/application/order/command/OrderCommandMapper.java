package io.github.blairjin.user_order_api.application.order.command;

import io.github.blairjin.user_order_api.domain.cart.CartItem;
import io.github.blairjin.user_order_api.domain.common.vo.Money;
import io.github.blairjin.user_order_api.domain.order.vo.OrderQuantity;
import io.github.blairjin.user_order_api.domain.product.Product;
import io.github.blairjin.user_order_api.domain.product.vo.ProductName;

public final class OrderCommandMapper {
    public static OrderItemCommand toCommand(Product product, CartItem cartItem){
        return new OrderItemCommand(
                product.getId(),
                ProductName.of(product.getProductName()),
                OrderQuantity.of(cartItem.getOrderQuantity()),
                Money.of(product.getUnitPrice())
        );
    }

    public static OrderItemCommand toCommand(Product product, OrderQuantity orderQuantity){
        return new OrderItemCommand(
                product.getId(),
                ProductName.of(product.getProductName()),
                orderQuantity,
                Money.of(product.getUnitPrice())
        );
    }
}
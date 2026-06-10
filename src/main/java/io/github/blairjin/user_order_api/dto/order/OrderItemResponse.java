package io.github.blairjin.user_order_api.dto.order;

import io.github.blairjin.user_order_api.domain.order.OrderItem;
import io.github.blairjin.user_order_api.domain.product.Product;

public record OrderItemResponse(
        Long productId,
        String productName,
        int orderQuantity,
        long unitPrice,
        long totalPrice
) {
    public static OrderItemResponse from(OrderItem item){
        return new OrderItemResponse(
                item.getProductId(),
                item.getProductName(),
                item.getOrderQuantity(),
                item.getUnitPrice(),
                item.getTotalPrice()
        );
    }
}
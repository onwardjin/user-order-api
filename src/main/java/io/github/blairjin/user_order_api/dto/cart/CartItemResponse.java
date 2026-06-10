package io.github.blairjin.user_order_api.dto.cart;

import io.github.blairjin.user_order_api.domain.cart.Cart;
import io.github.blairjin.user_order_api.domain.cart.CartItem;
import io.github.blairjin.user_order_api.domain.product.Product;

public record CartItemResponse(
        Long productId,
        Long cartItemId,
        String productName,
        long unitPrice,
        int quantity
) {
    public static CartItemResponse from(Product product, CartItem cartItem){
        return new CartItemResponse(
                product.getId(),
                cartItem.getId(),
                product.getProductName(),
                product.getUnitPrice(),
                cartItem.getOrderQuantity()
        );
    }
}
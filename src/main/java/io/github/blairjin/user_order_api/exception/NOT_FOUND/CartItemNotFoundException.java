package io.github.blairjin.user_order_api.exception.NOT_FOUND;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException() {
        super("CART_ITEM_NOT_FOUND");
    }
    public CartItemNotFoundException(String message) {
        super(message);
    }
}
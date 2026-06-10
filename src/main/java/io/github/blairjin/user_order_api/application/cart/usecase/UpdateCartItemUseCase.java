package io.github.blairjin.user_order_api.application.cart.usecase;

import io.github.blairjin.user_order_api.application.cart.service.CartCommandService;
import io.github.blairjin.user_order_api.application.product.reader.ProductReader;
import io.github.blairjin.user_order_api.domain.order.vo.OrderQuantity;
import io.github.blairjin.user_order_api.domain.product.Product;
import io.github.blairjin.user_order_api.dto.cart.CartItemUpdateRequest;
import io.github.blairjin.user_order_api.exception.BAD_REQUEST.InsufficientStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateCartItemUseCase {
    private final CartCommandService cartCommandService;
    private final ProductReader productReader;

    public void execute(Long userId, Long cartItemId, CartItemUpdateRequest request){
        OrderQuantity quantity = OrderQuantity.of(request.quantity());
        cartCommandService.updateCartItem(userId, cartItemId, quantity);
    }
}
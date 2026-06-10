package io.github.blairjin.user_order_api.application.cart.usecase;

import io.github.blairjin.user_order_api.application.cart.service.CartQueryService;
import io.github.blairjin.user_order_api.dto.cart.CartItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetCartItemsUseCase {
    private final CartQueryService cartQueryService;

    public List<CartItemResponse> execute(Long userId) {
        return cartQueryService.get(userId);
    }
}
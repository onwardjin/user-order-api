package io.github.blairjin.user_order_api.application.cart.usecase;

import io.github.blairjin.user_order_api.application.cart.service.CartCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCartItemUseCase {
    private final CartCommandService cartCommandService;

    public void execute(Long userId, Long cartItemId){
        cartCommandService.deleteItem(userId, cartItemId);
    }
}
package io.github.blairjin.user_order_api.application.product.usecase;

import io.github.blairjin.user_order_api.application.product.service.ProductCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteProductUseCase {
    private final ProductCommandService productCommandService;

    public void execute(Long userId, Long productId){
        productCommandService.delete(userId, productId);
    }
}
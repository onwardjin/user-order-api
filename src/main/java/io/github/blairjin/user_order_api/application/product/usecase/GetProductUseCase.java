package io.github.blairjin.user_order_api.application.product.usecase;

import io.github.blairjin.user_order_api.application.product.service.ProductQueryService;
import io.github.blairjin.user_order_api.dto.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetProductUseCase {
    private final ProductQueryService productQueryService;

    public ProductResponse execute(Long productId){
        return productQueryService.get(productId);
    }
}
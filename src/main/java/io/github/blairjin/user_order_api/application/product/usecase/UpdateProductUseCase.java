package io.github.blairjin.user_order_api.application.product.usecase;

import io.github.blairjin.user_order_api.application.product.command.ProductCommandMapper;
import io.github.blairjin.user_order_api.application.product.command.ProductUpdateCommand;
import io.github.blairjin.user_order_api.application.product.service.ProductCommandService;
import io.github.blairjin.user_order_api.dto.product.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateProductUseCase {
    private final ProductCommandService productCommandService;

    public void execute(Long userId, Long productId, ProductUpdateRequest request){
        ProductUpdateCommand command = ProductCommandMapper.toCommand(request);
        productCommandService.update(userId, productId, command);
    }
}
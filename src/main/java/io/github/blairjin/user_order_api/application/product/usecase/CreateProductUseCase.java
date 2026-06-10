package io.github.blairjin.user_order_api.application.product.usecase;

import io.github.blairjin.user_order_api.application.product.command.ProductCommandMapper;
import io.github.blairjin.user_order_api.application.product.service.ProductCommandService;
import io.github.blairjin.user_order_api.application.product.command.ProductCreateCommand;
import io.github.blairjin.user_order_api.dto.product.ProductCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateProductUseCase {
    private final ProductCommandService productCommandService;

    public void execute(Long userId, ProductCreateRequest request){
        ProductCreateCommand command = ProductCommandMapper.toCommand(request);
        productCommandService.create(userId, command);
    }
}
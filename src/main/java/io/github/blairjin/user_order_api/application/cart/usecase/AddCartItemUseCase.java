package io.github.blairjin.user_order_api.application.cart.usecase;

import io.github.blairjin.user_order_api.application.cart.command.CartItemCreateCommand;
import io.github.blairjin.user_order_api.application.cart.service.CartCommandService;
import io.github.blairjin.user_order_api.application.product.reader.ProductReader;
import io.github.blairjin.user_order_api.domain.order.vo.OrderQuantity;
import io.github.blairjin.user_order_api.dto.cart.CartItemAddRequest;
import io.github.blairjin.user_order_api.exception.BAD_REQUEST.InsufficientStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddCartItemUseCase {
    private final CartCommandService cartCommandService;
    private final ProductReader productReader;

    public void execute(Long userId, CartItemAddRequest request){
        Integer stockQuantity = productReader.getStockById(request.productId());

        if(request.quantity() > stockQuantity){
            throw new InsufficientStockException();
        }

        CartItemCreateCommand command =
                new CartItemCreateCommand(
                        request.productId(),
                        OrderQuantity.of(request.quantity())
                );

        cartCommandService.addCartItem(userId, command);
    }
}
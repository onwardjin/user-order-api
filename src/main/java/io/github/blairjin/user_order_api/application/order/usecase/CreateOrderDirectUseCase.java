package io.github.blairjin.user_order_api.application.order.usecase;

import io.github.blairjin.user_order_api.application.order.service.OrderCommandService;
import io.github.blairjin.user_order_api.domain.order.vo.OrderQuantity;
import io.github.blairjin.user_order_api.dto.order.OrderCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateOrderDirectUseCase {
    private final OrderCommandService orderCommandService;

    public void execute(Long userId, OrderCreateRequest request){
        OrderQuantity orderQuantity = OrderQuantity.of(request.quantity());

        orderCommandService.createDirect(
                userId,
                request.productId(),
                orderQuantity
        );
    }
}
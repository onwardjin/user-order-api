package io.github.blairjin.user_order_api.application.order.usecase;

import io.github.blairjin.user_order_api.application.order.service.OrderCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CancelOrderUseCase {
    private final OrderCommandService orderCommandService;

    public void execute(Long userId, Long orderId){
        orderCommandService.cancel(userId, orderId);
    }
}
package io.github.blairjin.user_order_api.application.order.usecase;

import io.github.blairjin.user_order_api.application.order.reader.OrderReader;
import io.github.blairjin.user_order_api.application.order.service.OrderQueryService;
import io.github.blairjin.user_order_api.domain.order.Order;
import io.github.blairjin.user_order_api.dto.order.OrderItemResponse;
import io.github.blairjin.user_order_api.dto.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetOrderUseCase {
    private final OrderReader orderReader;
    private final OrderQueryService orderQueryService;

    public List<OrderItemResponse> execute(Long userId, Long orderId){
        return orderQueryService.get(userId, orderId);
    }
}
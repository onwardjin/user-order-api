package io.github.blairjin.user_order_api.application.order.usecase;

import io.github.blairjin.user_order_api.application.order.service.OrderQueryService;
import io.github.blairjin.user_order_api.dto.order.OrderResponse;
import io.github.blairjin.user_order_api.dto.order.SearchOrderCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchOrdersUseCase {
    private final OrderQueryService orderQueryService;

    public Slice<OrderResponse> execute(Long userId, SearchOrderCondition condition, Pageable pageable){
        return orderQueryService.search(userId, condition, pageable);
    }
}
package io.github.blairjin.user_order_api.controller;

import io.github.blairjin.user_order_api.application.order.usecase.*;
import io.github.blairjin.user_order_api.dto.order.OrderCreateRequest;
import io.github.blairjin.user_order_api.dto.order.OrderItemResponse;
import io.github.blairjin.user_order_api.dto.order.OrderResponse;
import io.github.blairjin.user_order_api.dto.order.SearchOrderCondition;
import io.github.blairjin.user_order_api.infrastructure.jwt.CustomUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final CreateOrderFromCartUseCase createOrderFromCartUseCase;
    private final CreateOrderDirectUseCase createOrderDirectUseCase;
    private final SearchOrdersUseCase searchOrdersUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;

    @PostMapping("/cart")
    @ResponseStatus(HttpStatus.CREATED)
    public void createFromCart(@AuthenticationPrincipal CustomUserPrincipal principal){
        createOrderFromCartUseCase.execute(principal.userId());
    }

    @PostMapping("/direct")
    @ResponseStatus(HttpStatus.CREATED)
    public void createDirect(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @Valid @RequestBody OrderCreateRequest request
    ){
        createOrderDirectUseCase.execute(principal.userId(), request);
    }

    @GetMapping
    public Slice<OrderResponse> search(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @Valid @RequestBody SearchOrderCondition condition,
            Pageable pageable
    ){
        return searchOrdersUseCase.execute(principal.userId(), condition, pageable);
    }

    @GetMapping("/{orderId}")
    public List<OrderItemResponse> get(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable Long orderId
    ){
        return getOrderUseCase.execute(principal.userId(), orderId);
    }

    @PatchMapping("/{orderId}/cancel")
    public void cancel(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable Long orderId
    ){
        cancelOrderUseCase.execute(principal.userId(), orderId);
    }
}
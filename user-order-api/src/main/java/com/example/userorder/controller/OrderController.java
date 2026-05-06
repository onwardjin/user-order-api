package com.example.userorder.controller;

import com.example.userorder.dto.order.OrderCreateRequestDto;
import com.example.userorder.dto.order.OrderResponseDto;
import com.example.userorder.dto.order.OrderSearchCondition;
import com.example.userorder.security.CustomUserPrincipal;
import com.example.userorder.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto createOrder(
            @Valid @RequestBody OrderCreateRequestDto request,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        return orderService.createOrder(principal.getUserId(), request);
    }

    @GetMapping
    public Slice<OrderResponseDto> searchOrders(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            OrderSearchCondition condition,
            Pageable pageable
    ) {
        return orderService.searchOrders(principal.getUserId(), condition, pageable);
    }

    @GetMapping("/{orderId}")
    public OrderResponseDto findOrder(
            @PathVariable Long orderId,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        return orderService.findOrder(principal.getUserId(), orderId);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(
            @PathVariable Long orderId,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        orderService.deleteOrder(principal.getUserId(), orderId);
    }
}
package com.example.userorder.controller;

import com.example.userorder.dto.order.OrderCreateRequestDto;
import com.example.userorder.dto.order.OrderResponseDto;
import com.example.userorder.dto.order.OrderSearchCondition;
import com.example.userorder.dto.order.OrderStatusUpdateRequestDto;
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
    public OrderResponseDto createOrder(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @Valid @RequestBody OrderCreateRequestDto request
    ) {
        return orderService.createOrder(principal.getUserId(), request);
    }

    @GetMapping
    public Slice<OrderResponseDto> searchOrders(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @RequestBody OrderSearchCondition condition,
            Pageable pageable
    ) {
        return orderService.searchOrders(principal.getUserId(), condition, pageable);
    }

    @GetMapping("/{orderId}")
    public OrderResponseDto getOrder(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable Long orderId
    ) {
        return orderService.getOrder(principal.getUserId(), orderId);
    }

    @PatchMapping("/{orderId}")
    public OrderResponseDto updateOrderStatus(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable Long orderId,
            @Valid @RequestBody OrderStatusUpdateRequestDto request
    ) {
        return orderService.updateOrderStatus(principal.getUserId(), orderId, request);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable Long orderId
    ) {
        orderService.deleteOrder(principal.getUserId(), orderId);
    }
}
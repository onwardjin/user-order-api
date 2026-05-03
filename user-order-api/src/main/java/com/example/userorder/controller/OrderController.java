package com.example.userorder.controller;

import com.example.userorder.dto.OrderCreateRequestDto;
import com.example.userorder.dto.OrderResponseDto;
import com.example.userorder.dto.OrderUpdateRequestDto;
import com.example.userorder.security.CustomUserPrincipal;
import com.example.userorder.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<OrderResponseDto> findAllOrders(
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        return orderService.findAllOrders(principal.getUserId());
    }

    @GetMapping("/{id}")
    public OrderResponseDto findOrder(
            @PathVariable("id") Long orderId,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        return orderService.findOrder(principal.getUserId(), orderId);
    }

    @PatchMapping("/{id}")
    public OrderResponseDto updateOrder(
            @PathVariable("id") Long orderId,
            @Valid @RequestBody OrderUpdateRequestDto request,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        return orderService.updateOrder(principal.getUserId(), orderId, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(
            @PathVariable("id") Long orderId,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        orderService.deleteOrder(principal.getUserId(), orderId);
    }
}
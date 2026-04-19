package com.example.userorder.controller;

import com.example.userorder.dto.OrderRequestDto;
import com.example.userorder.dto.OrderResponseDto;
import com.example.userorder.entity.User;
import com.example.userorder.security.CustomUserPrincipal;
import com.example.userorder.service.OrderService;
import com.example.userorder.service.UserService;
import jakarta.validation.Valid;
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
    public OrderResponseDto create(
            @Valid @RequestBody OrderRequestDto request,
            @AuthenticationPrincipal CustomUserPrincipal user
    ){
        return orderService.createOrder(request, user.getId());
    }

    @GetMapping
    public List<OrderResponseDto> getOrders(@AuthenticationPrincipal CustomUserPrincipal user){
        return orderService.getOrdersByUserId(user.getId());
    }

    @GetMapping("/{id}")
    public OrderResponseDto getOrder(@PathVariable Long id, @AuthenticationPrincipal CustomUserPrincipal user){
        return orderService.getOrderById(id, user.getId());
    }

    @PutMapping("/{id}")
    public OrderResponseDto update(
            @PathVariable Long id,
            @RequestBody OrderRequestDto request,
            @AuthenticationPrincipal CustomUserPrincipal user){
        return orderService.updateOrder(id, request, user.getId());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @AuthenticationPrincipal CustomUserPrincipal user
    ){
        orderService.deleteOrder(id, user.getId());
    }
}
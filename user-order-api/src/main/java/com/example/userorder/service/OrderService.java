package com.example.userorder.service;

import com.example.userorder.dto.OrderRequestDto;
import com.example.userorder.dto.OrderResponseDto;
import com.example.userorder.entity.Order;
import com.example.userorder.entity.User;
import com.example.userorder.exception.OrderNotFoundException;
import com.example.userorder.exception.UserNotFoundException;
import com.example.userorder.repository.OrderRepository;
import com.example.userorder.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto request, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Order order = new Order(
                request.getItem(),
                user
        );

        Order savedOrder = orderRepository.save(order);
        return new OrderResponseDto(savedOrder);
    }

    public List<OrderResponseDto> getOrdersByUserId(Long userId){
        return orderRepository.findAllWithUser(userId).stream()
                .map(OrderResponseDto::new)
                .toList();
    }

    public OrderResponseDto getOrderById(Long orderId, Long userId){
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(OrderNotFoundException::new);

        return new OrderResponseDto(order);
    }

    @Transactional
    public OrderResponseDto updateOrder(Long orderId, OrderRequestDto request, Long userId){
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(OrderNotFoundException::new);
        order.setItem(request.getItem());
        return new OrderResponseDto(order);
    }

    @Transactional
    public void deleteOrder(Long orderId, Long userId){
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(OrderNotFoundException::new);
        orderRepository.delete(order);
    }
}
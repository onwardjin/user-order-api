package com.example.userorder.service;

import com.example.userorder.dto.OrderCreateRequestDto;
import com.example.userorder.dto.OrderResponseDto;
import com.example.userorder.dto.OrderUpdateRequestDto;
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
    public OrderResponseDto createOrder(Long userId, OrderCreateRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Order order = Order.createOrder(
                request.item(),
                request.quantity(),
                request.unitPrice(),
                user
        );

        orderRepository.save(order);
        return OrderResponseDto.from(order);
    }

    public List<OrderResponseDto> findAllOrders(Long userId) {
        return orderRepository.findAllByUser_Id(userId).stream()
                .map(OrderResponseDto::from)
                .toList();
    }

    public OrderResponseDto findOrder(Long userId, Long orderId) {
        Order order = getOrderByUserIdAndOrderId(userId, orderId);
        return OrderResponseDto.from(order);
    }

    @Transactional
    public OrderResponseDto updateOrder(Long userId, Long orderId, OrderUpdateRequestDto request) {
        Order order = getOrderByUserIdAndOrderId(userId, orderId);

        order.updateOrder(request.quantity(), request.unitPrice());

        return OrderResponseDto.from(order);
    }

    @Transactional
    public void deleteOrder(Long userId, Long orderId) {
        Order order = getOrderByUserIdAndOrderId(userId, orderId);
        orderRepository.delete(order);
    }


    private Order getOrderByUserIdAndOrderId(Long userId, Long orderId) {
        return orderRepository.findByUser_IdAndId(userId, orderId)
                .orElseThrow(OrderNotFoundException::new);
    }
}
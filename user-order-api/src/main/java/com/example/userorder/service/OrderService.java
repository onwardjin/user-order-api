package com.example.userorder.service;

import com.example.userorder.dto.order.OrderCreateRequestDto;
import com.example.userorder.dto.order.OrderResponseDto;
import com.example.userorder.dto.order.OrderSearchCondition;
import com.example.userorder.entity.Order;
import com.example.userorder.entity.Product;
import com.example.userorder.entity.User;
import com.example.userorder.exception.OrderNotFoundException;
import com.example.userorder.exception.ProductNotFoundException;
import com.example.userorder.exception.UserNotFoundException;
import com.example.userorder.repository.OrderRepository;
import com.example.userorder.repository.ProductRepository;
import com.example.userorder.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderResponseDto createOrder(Long userId, OrderCreateRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Product product = productRepository.findById(request.productId())
                .orElseThrow(ProductNotFoundException::new);

        Order order = Order.createOrder(product, user, request.quantity());
        Order savedOrder = orderRepository.save(order);

        return OrderResponseDto.from(savedOrder);
    }

    public Slice<OrderResponseDto> searchOrders(
            Long userId,
            OrderSearchCondition condition,
            Pageable pageable
    ) {
        return orderRepository.searchOrders(userId, condition, pageable)
                .map(OrderResponseDto::from);
    }

    public OrderResponseDto findOrder(Long userId, Long orderId) {
        Order order = getOrderByUserIdAndOrderId(userId, orderId);
        return OrderResponseDto.from(order);
    }

    public OrderResponseDto cancelOrder(Long userId, Long orderId) {
        Order order = getOrderByUserIdAndOrderId(userId, orderId);
        order.cancel();
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
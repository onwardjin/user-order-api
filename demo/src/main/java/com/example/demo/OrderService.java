package com.example.demo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    // CRUD - C
    public OrderResponseDto create(OrderRequestDto request){
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(()->new RuntimeException("User not found"));

        Order order = new Order(request.getItem(), user);
        Order savedOrder = orderRepository.save(order);

        return new OrderResponseDto(savedOrder);
    }

    // Read ALL
    public List<OrderResponseDto> findAll(){
        return orderRepository.findAll().stream()
                .map(OrderResponseDto::new)
                .toList();
    }

    // READ ONE
    public OrderResponseDto findById(Long orderId){
        Order result = orderRepository.findById(orderId)
                .orElseThrow(()->new RuntimeException("Order not found"));

        return new OrderResponseDto(result);
    }

    // UPDATE
    @Transactional
    public OrderResponseDto update(Long id, OrderRequestDto request){
        Order order = orderRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("Order not found"));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow( () -> new RuntimeException("User not found"));

        order.setItem(request.getItem());
        order.setUser(user);

        return new OrderResponseDto(order);
    }

    // DELETE
    public void delete(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
    }
}
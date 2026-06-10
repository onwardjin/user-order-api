package io.github.blairjin.user_order_api.application.order.reader;

import io.github.blairjin.user_order_api.domain.order.Order;
import io.github.blairjin.user_order_api.domain.order.OrderItem;
import io.github.blairjin.user_order_api.exception.NOT_FOUND.OrderNotFoundException;
import io.github.blairjin.user_order_api.repository.order.OrderItemRepository;
import io.github.blairjin.user_order_api.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderReader {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public Order getOrderByUserIdAndId(Long userId, Long orderId){
        return orderRepository.findByUserIdAndId(userId, orderId)
                .orElseThrow(OrderNotFoundException::new);
    }

    public List<OrderItem> getItemsByOrderId(Long orderId){
        return orderItemRepository.findAllByOrderId(orderId);
    }
}
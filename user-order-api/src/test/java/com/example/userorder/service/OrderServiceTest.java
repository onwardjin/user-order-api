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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    private static final Long USER_ID = 1L;
    private static final Long ORDER_ID = 1L;
    private static final String ITEM = "testItem";
    private static final int QUANTITY = 5;
    private static final int UNIT_PRICE = 100;

    @Test
    void createOrder_success() {
        User savedUser = createSavedUser();
        OrderCreateRequestDto request = new OrderCreateRequestDto(ITEM, QUANTITY, UNIT_PRICE);

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);

        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.of(savedUser));
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> {
                    Order order = invocation.getArgument(0);
                    ReflectionTestUtils.setField(order, "id", ORDER_ID);
                    return order;
                });

        OrderResponseDto result = orderService.createOrder(USER_ID, request);

        verify(userRepository).findById(USER_ID);
        verify(orderRepository).save(captor.capture());
        Order capturedOrder = captor.getValue();

        assertEquals(ORDER_ID, result.id());
        assertEquals(ITEM, result.item());
        assertEquals(QUANTITY, result.quantity());
        assertEquals(UNIT_PRICE, result.unitPrice());

        assertEquals(ORDER_ID, capturedOrder.getId());
        assertEquals(ITEM, capturedOrder.getItem());
        assertEquals(QUANTITY, capturedOrder.getQuantity());
        assertEquals(UNIT_PRICE, capturedOrder.getUnitPrice());
        assertEquals(savedUser, capturedOrder.getUser());
    }

    @Test
    void createOrder_userNotFound_throwsUserNotFoundException() {
        OrderCreateRequestDto request = new OrderCreateRequestDto(ITEM, QUANTITY, UNIT_PRICE);

        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> orderService.createOrder(USER_ID, request));

        verify(userRepository).findById(USER_ID);
        verify(orderRepository, never()).save(any());
    }

    @Test
    void findAllOrders_success() {
        User savedUser = createSavedUser();
        Order savedOrder01 = Order.createOrder(ITEM + "01", QUANTITY, UNIT_PRICE, savedUser);
        Order savedOrder02 = Order.createOrder(ITEM + "02", QUANTITY * 10, UNIT_PRICE * 10, savedUser);

        when(orderRepository.findAllByUser_Id(USER_ID))
                .thenReturn(List.of(savedOrder01, savedOrder02));

        List<OrderResponseDto> result = orderService.findAllOrders(USER_ID);

        verify(orderRepository).findAllByUser_Id(USER_ID);
        assertEquals(2, result.size());

        assertEquals(ITEM + "01", result.getFirst().item());
        assertEquals(QUANTITY, result.getFirst().quantity());
        assertEquals(UNIT_PRICE, result.getFirst().unitPrice());

        assertEquals(ITEM + "02", result.getLast().item());
        assertEquals(QUANTITY * 10, result.getLast().quantity());
        assertEquals(UNIT_PRICE * 10, result.getLast().unitPrice());
    }

    @Test
    void findAllOrders_emptyList_returnsEmptyList() {
        when(orderRepository.findAllByUser_Id(USER_ID))
                .thenReturn(List.of());

        List<OrderResponseDto> result = orderService.findAllOrders(USER_ID);

        verify(orderRepository).findAllByUser_Id(USER_ID);
        assertEquals(0, result.size());
    }

    @Test
    void findOrder_success() {
        User savedUser = createSavedUser();
        Order savedOrder = createSavedOrder(savedUser);

        when(orderRepository.findByUser_IdAndId(USER_ID, ORDER_ID))
                .thenReturn(Optional.of(savedOrder));

        OrderResponseDto result = orderService.findOrder(USER_ID, ORDER_ID);

        verify(orderRepository).findByUser_IdAndId(USER_ID, ORDER_ID);
        assertEquals(ORDER_ID, result.id());
        assertEquals(ITEM, result.item());
        assertEquals(QUANTITY, result.quantity());
        assertEquals(UNIT_PRICE, result.unitPrice());
    }

    @Test
    void findOrder_orderNotFound_throwsOrderNotFoundException() {
        when(orderRepository.findByUser_IdAndId(USER_ID, ORDER_ID))
                .thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class,
                () -> orderService.findOrder(USER_ID, ORDER_ID));

        verify(orderRepository).findByUser_IdAndId(USER_ID, ORDER_ID);
    }

    @Test
    void updateOrder_success() {
        User savedUser = createSavedUser();
        Order savedOrder = createSavedOrder(savedUser);

        OrderUpdateRequestDto request =
                new OrderUpdateRequestDto(QUANTITY * 10, UNIT_PRICE * 10);

        when(orderRepository.findByUser_IdAndId(USER_ID, ORDER_ID))
                .thenReturn(Optional.of(savedOrder));

        OrderResponseDto result = orderService.updateOrder(USER_ID, ORDER_ID, request);

        verify(orderRepository).findByUser_IdAndId(USER_ID, ORDER_ID);
        assertEquals(QUANTITY * 10, result.quantity());
        assertEquals(UNIT_PRICE * 10, result.unitPrice());

        assertEquals(QUANTITY * 10, savedOrder.getQuantity());
        assertEquals(UNIT_PRICE * 10, savedOrder.getUnitPrice());
    }

    @Test
    void updateOrder_orderNotFound_throwsOrderNotFoundException() {
        OrderUpdateRequestDto request =
                new OrderUpdateRequestDto(QUANTITY * 10, UNIT_PRICE * 10);

        when(orderRepository.findByUser_IdAndId(USER_ID, ORDER_ID))
                .thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class,
                () -> orderService.updateOrder(USER_ID, ORDER_ID, request));

        verify(orderRepository).findByUser_IdAndId(USER_ID, ORDER_ID);
    }

    @Test
    void deleteOrder_success() {
        User savedUser = createSavedUser();
        Order savedOrder = createSavedOrder(savedUser);

        when(orderRepository.findByUser_IdAndId(USER_ID, ORDER_ID))
                .thenReturn(Optional.of(savedOrder));

        orderService.deleteOrder(USER_ID, ORDER_ID);

        verify(orderRepository).findByUser_IdAndId(USER_ID, ORDER_ID);
        verify(orderRepository).delete(savedOrder);
    }

    @Test
    void deleteOrder_orderNotFound_throwsOrderNotFoundException() {
        when(orderRepository.findByUser_IdAndId(USER_ID, ORDER_ID))
                .thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class,
                () -> orderService.deleteOrder(USER_ID, ORDER_ID));

        verify(orderRepository).findByUser_IdAndId(USER_ID, ORDER_ID);
        verify(orderRepository, never()).delete(any());
    }


    // NOT TEST
    //
    private User createSavedUser() {
        User user = User.createUser(
                "testUserName",
                20,
                "validLoginId",
                "encodedPassword"
        );
        ReflectionTestUtils.setField(user, "id", USER_ID);
        return user;
    }

    private Order createSavedOrder(User user) {
        Order order = Order.createOrder(
                ITEM,
                QUANTITY,
                UNIT_PRICE,
                user
        );
        ReflectionTestUtils.setField(order, "id", ORDER_ID);
        return order;
    }
}
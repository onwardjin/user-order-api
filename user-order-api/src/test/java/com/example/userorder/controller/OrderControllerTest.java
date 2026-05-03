package com.example.userorder.controller;

import com.example.userorder.dto.OrderCreateRequestDto;
import com.example.userorder.dto.OrderResponseDto;
import com.example.userorder.dto.OrderUpdateRequestDto;
import com.example.userorder.exception.OrderNotFoundException;
import com.example.userorder.exception.UserNotFoundException;
import com.example.userorder.service.OrderService;
import com.example.userorder.support.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private static final Long ORDER_ID = 1L;
    private static final Long USER_ID = 1L;
    private static final String ITEM = "testItem";
    private static final int QUANTITY = 5;
    private static final int UNIT_PRICE = 100;

    @Test
    @WithMockCustomUser
    void createOrder_success() throws Exception {
        OrderCreateRequestDto request =
                new OrderCreateRequestDto(ITEM, QUANTITY, UNIT_PRICE);
        OrderResponseDto response =
                new OrderResponseDto(ORDER_ID, ITEM, QUANTITY, UNIT_PRICE, QUANTITY * UNIT_PRICE);

        when(orderService.createOrder(eq(USER_ID), any(OrderCreateRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(post("/orders")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(ORDER_ID))
                .andExpect(jsonPath("$.item").value(ITEM))
                .andExpect(jsonPath("$.quantity").value(QUANTITY))
                .andExpect(jsonPath("$.unitPrice").value(UNIT_PRICE))
                .andExpect(jsonPath("$.totalPrice").value(QUANTITY * UNIT_PRICE));
        verify(orderService).createOrder(eq(USER_ID), any(OrderCreateRequestDto.class));
    }

    @Test
    @WithMockCustomUser
    void createOrder_userNotFound_throwsUserNotFoundException() throws Exception {
        OrderCreateRequestDto request =
                new OrderCreateRequestDto(ITEM, QUANTITY, UNIT_PRICE);

        when(orderService.createOrder(eq(USER_ID), any(OrderCreateRequestDto.class)))
                .thenThrow(new UserNotFoundException());

        mockMvc.perform(post("/orders")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
        verify(orderService).createOrder(eq(USER_ID), any(OrderCreateRequestDto.class));
    }

    @Test
    @WithMockCustomUser
    void findAllOrders_success() throws Exception {
        OrderResponseDto savedOrder01 = new OrderResponseDto(ORDER_ID, ITEM + "01", QUANTITY, UNIT_PRICE, QUANTITY * UNIT_PRICE);
        OrderResponseDto savedOrder02 =
                new OrderResponseDto(ORDER_ID + 1, ITEM + "02", QUANTITY * 10, UNIT_PRICE * 10, QUANTITY * 10 * UNIT_PRICE * 10);

        when(orderService.findAllOrders(eq(USER_ID)))
                .thenReturn(List.of(savedOrder01, savedOrder02));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(ORDER_ID))
                .andExpect(jsonPath("$[0].item").value(ITEM + "01"))
                .andExpect(jsonPath("$[0].quantity").value(QUANTITY))
                .andExpect(jsonPath("$[0].unitPrice").value(UNIT_PRICE))
                .andExpect(jsonPath("$[0].totalPrice").value(QUANTITY * UNIT_PRICE))
                .andExpect(jsonPath("$[1].item").value(ITEM + "02"))
                .andExpect(jsonPath("$[1].id").value(ORDER_ID + 1))
                .andExpect(jsonPath("$[1].quantity").value(QUANTITY * 10))
                .andExpect(jsonPath("$[1].unitPrice").value(UNIT_PRICE * 10))
                .andExpect(jsonPath("$[1].totalPrice").value(QUANTITY * 10 * UNIT_PRICE * 10));
        verify(orderService).findAllOrders(eq(USER_ID));
    }

    @Test
    @WithMockCustomUser
    void findAllOrders_emptyList_returnsOkAndEmptyList() throws Exception {
        when(orderService.findAllOrders(eq(USER_ID)))
                .thenReturn(List.of());

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));

        verify(orderService).findAllOrders(eq(USER_ID));
    }

    @Test
    @WithMockCustomUser
    void findOrder_success() throws Exception {
        OrderResponseDto response =
                new OrderResponseDto(ORDER_ID, ITEM, QUANTITY, UNIT_PRICE, QUANTITY * UNIT_PRICE);

        when(orderService.findOrder(eq(USER_ID), eq(ORDER_ID)))
                .thenReturn(response);

        mockMvc.perform(get("/orders/{id}", ORDER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ORDER_ID))
                .andExpect(jsonPath("$.item").value(ITEM))
                .andExpect(jsonPath("$.quantity").value(QUANTITY))
                .andExpect(jsonPath("$.unitPrice").value(UNIT_PRICE));
        verify(orderService).findOrder(eq(USER_ID), eq(ORDER_ID));
    }

    @Test
    @WithMockCustomUser
    void findOrder_orderNotFound_returnsNotFound() throws Exception {
        when(orderService.findOrder(eq(USER_ID), eq(ORDER_ID)))
                .thenThrow(new OrderNotFoundException());

        mockMvc.perform(get("/orders/{id}", ORDER_ID))
                .andExpect(status().isNotFound());
        verify(orderService).findOrder(eq(USER_ID), eq(ORDER_ID));
    }

    @Test
    @WithMockCustomUser
    void updateOrder_success() throws Exception {
        OrderUpdateRequestDto request =
                new OrderUpdateRequestDto(QUANTITY * 10, UNIT_PRICE * 10);
        OrderResponseDto response =
                new OrderResponseDto(ORDER_ID, ITEM, QUANTITY * 10, UNIT_PRICE * 10, QUANTITY * 10 * UNIT_PRICE * 10);

        when(orderService.updateOrder(eq(USER_ID), eq(ORDER_ID), any(OrderUpdateRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(patch("/orders/{id}", ORDER_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ORDER_ID))
                .andExpect(jsonPath("$.item").value(ITEM))
                .andExpect(jsonPath("$.quantity").value(QUANTITY * 10))
                .andExpect(jsonPath("$.unitPrice").value(UNIT_PRICE * 10));
        verify(orderService).updateOrder(eq(USER_ID), eq(ORDER_ID), any(OrderUpdateRequestDto.class));
    }

    @Test
    @WithMockCustomUser
    void updateOrder_invalidRequest_returnsBadRequest() throws Exception {
        OrderUpdateRequestDto request =
                new OrderUpdateRequestDto(QUANTITY * (-1), UNIT_PRICE * (-1));

        mockMvc.perform(patch("/orders/{id}", ORDER_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
        verify(orderService, never()).updateOrder(anyLong(), anyLong(), any(OrderUpdateRequestDto.class));
    }

    @Test
    @WithMockCustomUser
    void updateOrder_orderNotFound_returnNotFound() throws Exception {
        OrderUpdateRequestDto request =
                new OrderUpdateRequestDto(QUANTITY * 10, UNIT_PRICE * 10);

        when(orderService.updateOrder(eq(USER_ID), eq(ORDER_ID), any(OrderUpdateRequestDto.class)))
                .thenThrow(new OrderNotFoundException());

        mockMvc.perform(patch("/orders/{id}", ORDER_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
        verify(orderService).updateOrder(eq(USER_ID), eq(ORDER_ID), any(OrderUpdateRequestDto.class));
    }

    @Test
    @WithMockCustomUser
    void deleteOrder_success() throws Exception {
        mockMvc.perform(delete("/orders/{id}", ORDER_ID)
                        .with(csrf()))
                .andExpect(status().isNoContent());
        verify(orderService).deleteOrder(eq(USER_ID), eq(ORDER_ID));
    }

    @Test
    @WithMockCustomUser
    void deleteOrder_orderNotFound_returnsNotFound() throws Exception {
        doThrow(new OrderNotFoundException())
                .when(orderService)
                .deleteOrder(eq(USER_ID), eq(ORDER_ID));

        mockMvc.perform(delete("/orders/{id}", ORDER_ID)
                        .with(csrf()))
                .andExpect(status().isNotFound());
        verify(orderService).deleteOrder(eq(USER_ID), eq(ORDER_ID));
    }
}
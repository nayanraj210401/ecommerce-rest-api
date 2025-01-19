package com.example.orderservice.controllers;

import com.example.common.dto.orders.CreateOrderRequest;
import com.example.common.dto.orders.OrderDTO;
import com.example.common.enums.OrderStatus;
import com.example.orderservice.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllOrders() {
        OrderDTO orderDTO = new OrderDTO();
        when(orderService.getAllOrders()).thenReturn(Collections.singletonList(orderDTO));

        ResponseEntity<List<OrderDTO>> response = orderController.getAllOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    public void testGetOrderById() {
        Long orderId = 1L;
        OrderDTO orderDTO = new OrderDTO();
        when(orderService.getOrderById(orderId)).thenReturn(orderDTO);

        ResponseEntity<OrderDTO> response = orderController.getOrderById(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderDTO, response.getBody());
        verify(orderService, times(1)).getOrderById(orderId);
    }

    @Test
    public void testCreateOrder() {
        CreateOrderRequest request = new CreateOrderRequest();
        OrderDTO orderDTO = new OrderDTO();
        when(orderService.createOrder(request)).thenReturn(orderDTO);

        ResponseEntity<OrderDTO> response = orderController.createOrder(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(orderDTO, response.getBody());
        verify(orderService, times(1)).createOrder(request);
    }

    @Test
    public void testUpdateOrderStatus() {
        Long orderId = 1L;
        OrderStatus status = OrderStatus.SHIPPED;
        OrderDTO orderDTO = new OrderDTO();
        when(orderService.updateOrderStatus(orderId, status)).thenReturn(orderDTO);

        ResponseEntity<OrderDTO> response = orderController.createOrder(orderId, status);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderDTO, response.getBody());
        verify(orderService, times(1)).updateOrderStatus(orderId, status);
    }
}

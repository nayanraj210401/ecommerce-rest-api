package com.example.orderservice.services;

import com.example.common.dto.orders.CreateOrderRequest;
import com.example.common.dto.orders.OrderDTO;
import com.example.common.dto.orders.OrderItemRequest;
import com.example.common.enums.OrderStatus;
import com.example.common.models.Product;
import com.example.common.models.orders.Order;
import com.example.common.models.orders.OrderItem;
import com.example.orderservice.repo.OrderRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepo orderRepo;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllOrders() {
        Order order = new Order();
        when(orderRepo.findAll()).thenReturn(Collections.singletonList(order));

        List<OrderDTO> orders = orderService.getAllOrders();

        assertEquals(1, orders.size());
        verify(orderRepo, times(1)).findAll();
    }

    @Test
    public void testGetOrderById() {
        Long orderId = 1L;
        Order order = new Order();
        when(orderRepo.findById(orderId)).thenReturn(Optional.of(order));

        OrderDTO orderDTO = orderService.getOrderById(orderId);

        assertNotNull(orderDTO);
        verify(orderRepo, times(1)).findById(orderId);
    }

    @Test
    public void testCreateOrder() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setUserId(1L);
        List<OrderItemRequest> orderItemRequests = new ArrayList<>();
        OrderItemRequest orderItemRequest = new OrderItemRequest();
        orderItemRequest.setProductId(1L);
        orderItemRequest.setQuantity(1);
        orderItemRequests.add(orderItemRequest);
        request.setItems(orderItemRequests);

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setStockQuantity(10);
        product.setPrice(BigDecimal.valueOf(100.00)); // Set a non-null price for the product

        when(restTemplate.getForObject(anyString(), eq(Product.class))).thenReturn(product);
        when(orderRepo.save(any(Order.class))).thenReturn(new Order());

        OrderDTO orderDTO = orderService.createOrder(request);

        assertNotNull(orderDTO);
        verify(orderRepo, times(1)).save(any(Order.class));
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Product.class));
    }

    @Test
    public void testUpdateOrderStatus() {
        Long orderId = 1L;
        OrderStatus status = OrderStatus.SHIPPED;
        Order order = new Order();
        when(orderRepo.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepo.save(any(Order.class))).thenReturn(order);

        OrderDTO orderDTO = orderService.updateOrderStatus(orderId, status);

        assertNotNull(orderDTO);
        assertEquals(status, orderDTO.getStatus());
        verify(orderRepo, times(1)).findById(orderId);
        verify(orderRepo, times(1)).save(any(Order.class));
    }
}

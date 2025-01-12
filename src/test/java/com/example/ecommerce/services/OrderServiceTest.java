package com.example.ecommerce.services;

import com.example.ecommerce.dto.orders.CreateOrderRequest;
import com.example.ecommerce.dto.orders.OrderDTO;
import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.models.Order;
import com.example.ecommerce.models.OrderItem;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.repositories.OrderRepo;
import com.example.ecommerce.repositories.ProductRepo;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepo orderRepo;

    @Mock
    private ProductRepo productRepo;

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
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(new Product());
        orderItem.setQuantity(1);
        request.setItems(Collections.singletonList(orderItem));

        Product product = new Product();
        product.setStockQuantity(10);
        when(productRepo.findById(any(Long.class))).thenReturn(Optional.of(product));
        when(orderRepo.save(any(Order.class))).thenReturn(new Order());

        OrderDTO orderDTO = orderService.createOrder(request);

        assertNotNull(orderDTO);
        verify(orderRepo, times(1)).save(any(Order.class));
        verify(productRepo, times(1)).save(any(Product.class));
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

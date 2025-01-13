package com.example.ecommerce.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.ecommerce.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    private Order order;

    @BeforeEach
    public void setUp() {
        order = new Order();
        order.setTotalAmount(BigDecimal.valueOf(100.00));
        order.setUserId(1L);
    }

    @Test
    public void testOnCreate() {
        order.onCreate();
        assertNotNull(order.getCreatedAt());
        assertNotNull(order.getUpdatedAt());
        assertEquals(OrderStatus.PENDING, order.getStatus());
    }

    @Test
    public void testOnUpdate() {
        order.onCreate();
        LocalDateTime createdAt = order.getCreatedAt();
        order.onUpdate();
        assertNotNull(order.getUpdatedAt());
        assertEquals(createdAt, order.getCreatedAt());
        assertTrue(order.getUpdatedAt().isAfter(order.getCreatedAt()));
    }
}

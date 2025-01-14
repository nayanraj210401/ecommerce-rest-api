package com.example.common.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class OrderItemTest {

    private OrderItem orderItem;

    @BeforeEach
    public void setUp() {
        orderItem = new OrderItem();
        orderItem.setQuantity(2);
        orderItem.setUnitPrice(BigDecimal.valueOf(50.00));
    }

    @Test
    public void testOnCreate() {
        orderItem.onCreate();
        assertNotNull(orderItem.getCreatedAt());
        assertNotNull(orderItem.getUpdatedAt());
        assertEquals(BigDecimal.valueOf(100.00), orderItem.getSubtotal());
    }

    @Test
    public void testOnUpdate() {
        orderItem.onCreate();
        LocalDateTime createdAt = orderItem.getCreatedAt();
        orderItem.setQuantity(3);
        orderItem.onUpdate();
        assertNotNull(orderItem.getUpdatedAt());
        assertEquals(createdAt, orderItem.getCreatedAt());
        assertTrue(orderItem.getUpdatedAt().isAfter(orderItem.getCreatedAt()));
        assertEquals(BigDecimal.valueOf(150.00), orderItem.getSubtotal());
    }
}

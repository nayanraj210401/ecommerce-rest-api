package com.example.ecommerce.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product();
        product.setName("Test Product");
        product.setDescription("This is a test product");
        product.setPrice(BigDecimal.valueOf(100.00));
        product.setStockQuantity(10);
    }

    @Test
    public void testOnCreate() {
        product.onCreate();
        assertNotNull(product.getCreatedAt());
        assertNotNull(product.getUpdatedAt());
    }

    @Test
    public void testOnUpdate() {
        product.onCreate();
        LocalDateTime createdAt = product.getCreatedAt();
        product.onUpdate();
        assertNotNull(product.getUpdatedAt());
        assertEquals(createdAt, product.getCreatedAt());
        assertTrue(product.getUpdatedAt().isAfter(product.getCreatedAt()));
    }
}

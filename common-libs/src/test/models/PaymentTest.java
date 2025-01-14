package com.example.common.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.common.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    private Payment payment;

    @BeforeEach
    public void setUp() {
        payment = new Payment();
        payment.setTransactionId("12345");
        payment.setAmount(BigDecimal.valueOf(100.00));
        payment.setOrderId(1L);
        payment.setPaymentMethod(PaymentMethod.CREDIT_CARD);
    }

    @Test
    public void testOnCreate() {
        payment.onCreate();
        assertNotNull(payment.getCreatedAt());
        assertNotNull(payment.getPaymentDate());
    }

    @Test
    public void testOnUpdate() {
        payment.onCreate();
        LocalDateTime createdAt = payment.getCreatedAt();
        payment.onUpdate();
        assertNotNull(payment.getUpdatedAt());
        assertEquals(createdAt, payment.getCreatedAt());
        assertTrue(payment.getUpdatedAt().isAfter(payment.getCreatedAt()));
    }
}

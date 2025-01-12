package com.example.ecommerce.dto.payment;

import com.example.ecommerce.enums.PaymentMethod;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private Long orderId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private String paymentDetails; // Encrypted payment information
}
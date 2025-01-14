package com.example.ecommerce.services;

import com.example.common.dto.payment.*;

import java.util.List;

public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest paymentRequest);
    PaymentResponse getPaymentByTransactionId(String transactionId);
    List<PaymentResponse> getPaymentsByOrderId(Long orderId);
    PaymentResponse refundPayment(String transactionId);
    String generatePaymentReceipt(String transactionId);
}

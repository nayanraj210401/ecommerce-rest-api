package com.example.ecommerce.services;

import com.example.ecommerce.dto.payment.PaymentRequest;
import com.example.ecommerce.dto.payment.PaymentResponse;

import java.util.List;

public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest paymentRequest);
    PaymentResponse getPaymentByTransactionId(String transactionId);
    List<PaymentResponse> getPaymentsByOrderId(Long orderId);
    PaymentResponse refundPayment(String transactionId);
    String generatePaymentReceipt(String transactionId);
}

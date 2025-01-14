package com.example.ecommerce.services;

import com.example.ecommerce.dto.payment.PaymentRequest;
import com.example.ecommerce.dto.payment.PaymentResponse;
import com.example.ecommerce.enums.PaymentMethod;
import com.example.ecommerce.enums.PaymentStatus;
import com.example.ecommerce.models.Payment;
import com.example.ecommerce.repositories.PaymentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    @Mock
    private PaymentRepo paymentRepo;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessPayment() {
        PaymentRequest request = new PaymentRequest();
        request.setOrderId(1L);
        request.setAmount(BigDecimal.valueOf(100.00));
        request.setPaymentMethod(PaymentMethod.CREDIT_CARD);

        Payment payment = new Payment();
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setAmount(request.getAmount());
        payment.setOrderId(request.getOrderId());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus(PaymentStatus.COMPLETED);

        when(paymentRepo.save(any(Payment.class))).thenReturn(payment);

        PaymentResponse response = paymentService.processPayment(request);

        assertEquals(payment.getTransactionId(), response.getTransactionId());
        assertEquals(payment.getStatus(), response.getStatus());
        assertEquals(payment.getAmount(), response.getAmount());
    }

    @Test
    void testGetPaymentByTransactionId() {
        String transactionId = UUID.randomUUID().toString();

        Payment payment = new Payment();
        payment.setTransactionId(transactionId);
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setAmount(BigDecimal.valueOf(100.00));

        when(paymentRepo.findByTransactionId(transactionId)).thenReturn(payment);

        PaymentResponse response = paymentService.getPaymentByTransactionId(transactionId);

        assertEquals(payment.getTransactionId(), response.getTransactionId());
        assertEquals(payment.getStatus(), response.getStatus());
        assertEquals(payment.getAmount(), response.getAmount());
    }

    @Test
    void testGetPaymentsByOrderId() {
        Long orderId = 1L;

        Payment payment1 = new Payment();
        payment1.setTransactionId(UUID.randomUUID().toString());
        payment1.setStatus(PaymentStatus.COMPLETED);
        payment1.setAmount(BigDecimal.valueOf(100.00));
        payment1.setOrderId(orderId);

        Payment payment2 = new Payment();
        payment2.setTransactionId(UUID.randomUUID().toString());
        payment2.setStatus(PaymentStatus.COMPLETED);
        payment2.setAmount(BigDecimal.valueOf(200.00));
        payment2.setOrderId(orderId);

        List<Payment> payments = Arrays.asList(payment1, payment2);

        when(paymentRepo.findByOrderId(orderId)).thenReturn(payments);

        List<PaymentResponse> responses = paymentService.getPaymentsByOrderId(orderId);

        assertEquals(2, responses.size());
        assertEquals(payment1.getTransactionId(), responses.get(0).getTransactionId());
        assertEquals(payment2.getTransactionId(), responses.get(1).getTransactionId());
    }

    @Test
    void testRefundPayment() {
        String transactionId = UUID.randomUUID().toString();

        Payment payment = new Payment();
        payment.setTransactionId(transactionId);
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setAmount(BigDecimal.valueOf(100.00));

        when(paymentRepo.findByTransactionId(transactionId)).thenReturn(payment);
        when(paymentRepo.save(any(Payment.class))).thenReturn(payment);

        PaymentResponse response = paymentService.refundPayment(transactionId);

        assertEquals(PaymentStatus.REFUNDED, response.getStatus());
    }

    @Test
    void testGeneratePaymentReceipt() {
        String transactionId = UUID.randomUUID().toString();

        String receiptUrl = paymentService.generatePaymentReceipt(transactionId);

        assertEquals("receipt-" + transactionId + ".pdf", receiptUrl);
    }
}

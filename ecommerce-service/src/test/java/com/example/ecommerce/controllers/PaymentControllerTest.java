package com.example.ecommerce.controllers;

import com.example.common.dto.payment.PaymentRequest;
import com.example.common.dto.payment.PaymentResponse;
import com.example.ecommerce.services.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessPayment() {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(1L);
        paymentRequest.setAmount(BigDecimal.valueOf(100.00));

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setTransactionId("12345");
        paymentResponse.setAmount(BigDecimal.valueOf(100.00));

        when(paymentService.processPayment(any(PaymentRequest.class))).thenReturn(paymentResponse);

        ResponseEntity<PaymentResponse> responseEntity = paymentController.processPayment(paymentRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(paymentResponse, responseEntity.getBody());
    }

    @Test
    void testGetPayment() {
        String transactionId = "12345";
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setTransactionId(transactionId);

        when(paymentService.getPaymentByTransactionId(transactionId)).thenReturn(paymentResponse);

        ResponseEntity<PaymentResponse> responseEntity = paymentController.getPayment(transactionId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(paymentResponse, responseEntity.getBody());
    }

    @Test
    void testGetPaymentsByOrder() {
        Long orderId = 1L;
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setTransactionId("12345");

        when(paymentService.getPaymentsByOrderId(orderId)).thenReturn(Collections.singletonList(paymentResponse));

        ResponseEntity<List<PaymentResponse>> responseEntity = paymentController.getPaymentsByOrder(orderId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(paymentResponse, responseEntity.getBody().get(0));
    }

    @Test
    void testRefundPayment() {
        String transactionId = "12345";
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setTransactionId(transactionId);

        when(paymentService.refundPayment(transactionId)).thenReturn(paymentResponse);

        ResponseEntity<PaymentResponse> responseEntity = paymentController.refundPayment(transactionId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(paymentResponse, responseEntity.getBody());
    }

    @Test
    void testGetPaymentReceipt() {
        String transactionId = "12345";
        String receiptUrl = "receipt-12345.pdf";

        when(paymentService.generatePaymentReceipt(transactionId)).thenReturn(receiptUrl);

        ResponseEntity<String> responseEntity = paymentController.getPaymentReceipt(transactionId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(receiptUrl, responseEntity.getBody());
    }
}

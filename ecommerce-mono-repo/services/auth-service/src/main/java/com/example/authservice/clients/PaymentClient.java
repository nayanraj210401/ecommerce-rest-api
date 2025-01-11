package com.example.authservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "payment-service")
public interface PaymentClient {

    @GetMapping("/payments/{transactionId}")
    PaymentResponse getPaymentByTransactionId(@PathVariable String transactionId);

    @PostMapping("/payments/{transactionId}/refund")
    PaymentResponse refundPayment(@PathVariable String transactionId);
}

package com.example.ecommerce.services;

import com.example.common.dto.payment.PaymentRequest;
import com.example.common.dto.payment.PaymentResponse;
import com.example.common.enums.PaymentStatus;
import com.example.common.models.Payment;
import com.example.ecommerce.repositories.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepo paymentRepo;

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        payment.setTransactionId(generateTransactionId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setOrderId(paymentRequest.getOrderId());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setStatus(PaymentStatus.PROCESSING);

        // Here you would integrate with actual payment gateway
        // For now, we'll simulate a successful payment
        payment.setStatus(PaymentStatus.COMPLETED);

        Payment savedPayment = paymentRepo.save(payment);
        return convertToPaymentResponse(savedPayment);
    }

    @Override
    public PaymentResponse getPaymentByTransactionId(String transactionId) {
        Payment payment = paymentRepo.findByTransactionId(transactionId);
        return convertToPaymentResponse(payment);
    }

    @Override
    public List<PaymentResponse> getPaymentsByOrderId(Long orderId) {
        return paymentRepo.findByOrderId(orderId)
                .stream()
                .map(this::convertToPaymentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponse refundPayment(String transactionId) {
        Payment payment = paymentRepo.findByTransactionId(transactionId);
        payment.setStatus(PaymentStatus.REFUNDED);
        Payment refundedPayment = paymentRepo.save(payment);
        return convertToPaymentResponse(refundedPayment);
    }

    @Override
    public String generatePaymentReceipt(String transactionId) {
        return "receipt-" + transactionId + ".pdf";
    }

    private String generateTransactionId() {
        return UUID.randomUUID().toString();
    }

    private PaymentResponse convertToPaymentResponse(Payment payment) {
        PaymentResponse response = new PaymentResponse();
        response.setTransactionId(payment.getTransactionId());
        response.setStatus(payment.getStatus());
        response.setAmount(payment.getAmount());
        response.setPaymentDate(payment.getPaymentDate());
        response.setReceiptUrl("/receipts/" + payment.getTransactionId());
        return response;
    }
}

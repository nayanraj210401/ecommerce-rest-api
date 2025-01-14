package com.example.ecommerce.repositories;

import com.example.common.enums.PaymentStatus;
import com.example.common.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
    List<Payment> findByOrderId(Long orderId);
    Payment findByTransactionId(String transactionId);
    List<Payment> findByStatus(PaymentStatus status);
}

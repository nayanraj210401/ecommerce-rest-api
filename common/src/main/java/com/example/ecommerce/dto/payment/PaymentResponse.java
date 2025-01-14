package com.example.ecommerce.dto.payment;

import com.example.ecommerce.enums.PaymentStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponse {
    private String transactionId;
    private PaymentStatus status;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String receiptUrl;
}

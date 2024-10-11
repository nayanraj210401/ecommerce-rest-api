package com.example.ecommerce.models;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="PAYMENT")
public class Payment extends BaseModel {
   
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String paymentMethod;
    private Double amount;
    private LocalDateTime paymentDate;
    private String status;

}

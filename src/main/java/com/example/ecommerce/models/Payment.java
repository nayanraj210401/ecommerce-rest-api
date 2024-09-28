package com.example.ecommerce.models;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Payment extends BaseModel {
   
    @OneToOne
    @JoinColumn(name = "order_id")
    private CustomerOrder customerOrder;

    private String paymentMethod;
    private Double amount;
    private LocalDateTime paymentDate;
    private String status;

}

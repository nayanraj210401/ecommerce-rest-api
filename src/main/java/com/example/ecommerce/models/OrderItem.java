package com.example.ecommerce.models;

import jakarta.persistence.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private CustomerOrder customerOrder;

    @ManyToOne
    private Product product;

    private int quantity;
    private double unitPrice;
}

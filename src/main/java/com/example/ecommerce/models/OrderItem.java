package com.example.ecommerce.models;

import jakarta.persistence.*;

@Entity
public class OrderItem extends BaseModel {

    @ManyToOne
    private CustomerOrder customerOrder;

    @ManyToOne
    private Product product;

    private int quantity;
    private double unitPrice;
}

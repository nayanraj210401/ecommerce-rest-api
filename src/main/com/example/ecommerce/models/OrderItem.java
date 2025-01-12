package com.example.ecommerce.models;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem extends  BaseModel{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;

    @PrePersist
    protected void onCreate() {
        if(quantity != null && unitPrice != null) {
            subtotal = unitPrice.multiply(new BigDecimal(quantity));
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        if(quantity != null && unitPrice != null) {
            subtotal = unitPrice.multiply(new BigDecimal(quantity));
        }

        updatedAt = LocalDateTime.now();
    }

}

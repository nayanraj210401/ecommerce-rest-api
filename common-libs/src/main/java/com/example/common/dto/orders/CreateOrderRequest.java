package com.example.common.dto.orders;

import org.hibernate.validator.constraints.*;
import java.util.List;

public class CreateOrderRequest {

    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemRequest> items;

    // Implement when auth is implemented
    private Long userId;

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

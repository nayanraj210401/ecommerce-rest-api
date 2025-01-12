package com.example.orderservice.dto.orders;

import lombok.Data;
import org.hibernate.beanvalidation.tck.tests.constraints.constraintcomposition.NotEmpty;

import java.util.List;

@Data
public class CreateOrderRequest {

    @NotEmpty(message = "Order must contain alteast one item")
    private List<OrderItemRequest> items;

    private Long userId;
}

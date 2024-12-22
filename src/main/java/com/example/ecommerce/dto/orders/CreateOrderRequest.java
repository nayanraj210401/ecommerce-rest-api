package com.example.ecommerce.dto.orders;

import lombok.Data;
import org.hibernate.beanvalidation.tck.tests.constraints.constraintcomposition.NotEmpty;

import java.util.List;

@Data
public class CreateOrderRequest {

    @NotEmpty(message = "Order must contain alteast one item")
    private List<OrderItemRequest> items;

//    Implement when auth is implemented
//    private Long userId;
}

package com.example.ecommerce.dto.orders;

import com.example.ecommerce.enums.OrderStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private Long userId;
    private List<OrderItemDTO> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.example.orderservice.services;


import com.example.common.dto.orders.CreateOrderRequest;
import com.example.common.dto.orders.OrderDTO;
import com.example.common.dto.orders.OrderItemDTO;
import com.example.common.enums.OrderStatus;
import com.example.common.models.Order;
import com.example.common.models.OrderItem;
import com.example.common.models.Product;
import com.example.orderservice.repo.order.OrderRepo;
import com.example.orderservice.repo.product.ProductRepo;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private OrderRepo orderRepo;
    private ProductRepo productRepo;

    @Autowired
    public OrderService(@Qualifier("productEntityManagerFactory") EntityManagerFactory productEntityManagerFactory,
                       ProductRepo productRepo, OrderRepo orderRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    public List<OrderDTO> getAllOrders(){
        return orderRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long id){
        Order order = orderRepo.findById(id).orElseThrow( () -> new EntityNotFoundException("Order not found"));
        return convertToDTO(order);
    }

    @Transactional
    public OrderDTO createOrder(CreateOrderRequest request){
        Order order = new Order();
       order.setUserId(request.getUserId());
        List<OrderItem> orderItems = request.getItems().stream()
                .map(itemRequest -> {
                    Product product = productRepo.findById(itemRequest.getProductId())
                            .orElseThrow(() -> new EntityNotFoundException("Product not found"));

                    if (product.getStockQuantity() < itemRequest.getQuantity()) {
                        throw new IllegalStateException("Insufficient stock for product: " + product.getName());
                    }

                    // Update product stock
                    product.setStockQuantity(product.getStockQuantity() - itemRequest.getQuantity());
                    productRepo.save(product);

                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(product);
                    orderItem.setQuantity(itemRequest.getQuantity());
                    orderItem.setOrder(order);
                    orderItem.setUnitPrice(product.getPrice());
                    return orderItem;
                }).collect(Collectors.toList());

        order.setOrderItems(orderItems);

        // Compute total
        BigDecimal totalAmount = orderItems.stream()
                .map(orderItem -> orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(totalAmount);
        Order savedOrder =  orderRepo.save(order);
        return convertToDTO(savedOrder);
    }

    @Transactional
    public OrderDTO updateOrderStatus(Long id, OrderStatus newStatus) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
        order.setStatus(newStatus);

        Order updatedOrder = orderRepo.save(order);
        return convertToDTO(updatedOrder);
    }


    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
       dto.setUserId(order.getUserId());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());

        List<OrderItemDTO> itemDTOs = order.getOrderItems().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        dto.setItems(itemDTOs);

        return dto;
    }


    private OrderItemDTO convertToDTO(OrderItem orderItem) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(orderItem.getId());
        dto.setProductId(orderItem.getProduct().getId());
        dto.setProductName(orderItem.getProduct().getName());
        dto.setQuantity(orderItem.getQuantity());
        dto.setUnitPrice(orderItem.getUnitPrice());
        dto.setSubtotal(orderItem.getSubtotal());
        return dto;
    }
}

package com.example.ecommerce.services;


import com.example.ecommerce.dto.orders.CreateOrderRequest;
import com.example.ecommerce.dto.orders.OrderDTO;
import com.example.ecommerce.dto.orders.OrderItemDTO;
import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.models.Order;
import com.example.ecommerce.models.OrderItem;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.repositories.OrderRepo;
import com.example.ecommerce.repositories.ProductRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

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
//        implemented later
//        order.setUserId(request.getUserId());
//        System.out.println("ORDER_CREATED NOW GET ORDER-ITEMS");
//        System.out.println("Request items"+request.getItems());
        List<OrderItem> orderItems = request.getItems().stream()
                .map(itemRequest -> {
                    Product product = productRepo.findById(itemRequest.getProductId())
                            .orElseThrow(() -> new EntityNotFoundException("Product not found"));

                    if (product.getStockQuantity() < itemRequest.getQuantity()) {
                        throw new IllegalStateException("Insufficient stock for product: " + product.getName());
                    }

//                    System.out.println("PRODUCT FETCHED: " + product.getName());

                    // Update product stock
                    product.setStockQuantity(product.getStockQuantity() - itemRequest.getQuantity());
                    productRepo.save(product);

                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(product);
                    orderItem.setQuantity(itemRequest.getQuantity());
                    orderItem.setOrder(order);
                    orderItem.setUnitPrice(product.getPrice());
//                    System.out.println("ORDER_ITEMS FETCHED: " + orderItem);
                    return orderItem;
                }).collect(Collectors.toList());

        order.setOrderItems(orderItems);

//        System.out.println("ORDER_ITEMS:" + orderItems);

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
//        dto.setUserId(order.getUserId(getUserId));
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

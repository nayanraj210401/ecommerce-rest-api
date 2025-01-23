package com.example.orderservice.services;

import com.example.common.dto.orders.CreateOrderRequest;
import com.example.common.dto.orders.OrderDTO;
import com.example.common.dto.orders.OrderItemDTO;
import com.example.common.enums.OrderStatus;
import com.example.common.models.Product;
import com.example.common.models.orders.Order;
import com.example.common.models.orders.OrderItem;
import com.example.orderservice.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private String productServiceUrl = "http://localhost:8083/api/products/";

    public List<OrderDTO> getAllOrders() {
        return orderRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return convertToDTO(order);
    }

    @Transactional
    public OrderDTO createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setUserId(request.getUserId());
        List<OrderItem> orderItems = request.getItems().stream()
                .map(itemRequest -> {
                    Product product = getProductFromApi(itemRequest.getProductId());

                    if (product == null) {
                        throw new EntityNotFoundException("Product with ID " + itemRequest.getProductId() + " not found");
                    }

                    if (product.getStockQuantity() < itemRequest.getQuantity()) {
                        throw new IllegalStateException("Insufficient stock for product: " + product.getName());
                    }

                    // Update product stock
                    product.setStockQuantity(product.getStockQuantity() - itemRequest.getQuantity());
                    updateProductStockInApi(product);

                    OrderItem orderItem = new OrderItem();
                    orderItem.setProductId(product.getId());
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
        Order savedOrder = orderRepo.save(order);
        return convertToDTO(savedOrder);
    }

    @Transactional
    public OrderDTO updateOrderStatus(Long id, OrderStatus newStatus) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
        order.setStatus(newStatus);

        if (newStatus == OrderStatus.SUCCESS) {
            sendOrderStatusToKafka(order);
        }

        Order updatedOrder = orderRepo.save(order);
        return convertToDTO(updatedOrder);
    }

    private void sendOrderStatusToKafka(Order order) {
        String message = "Order ID: " + order.getId() + " Status: " + order.getStatus();
        kafkaTemplate.send("order-status-topic", message);
    }

    private Product getProductFromApi(Long productId) {
        String url = productServiceUrl + productId;
        return restTemplate.getForObject(url, Product.class);
    }

    private void updateProductStockInApi(Product product) {
        String url = productServiceUrl + product.getId();
        restTemplate.put(url, product);
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
        dto.setProductId(orderItem.getProductId());

        Product product = getProductFromApi(orderItem.getProductId());
        dto.setProductName(product.getName());

        dto.setQuantity(orderItem.getQuantity());
        dto.setUnitPrice(orderItem.getUnitPrice());
        dto.setSubtotal(orderItem.getSubtotal());
        return dto;
    }
}

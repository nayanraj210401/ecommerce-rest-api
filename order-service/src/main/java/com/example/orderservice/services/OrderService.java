package com.example.orderservice.services;

import com.example.common.dto.orders.CreateOrderRequest;
import com.example.common.dto.orders.OrderDTO;
import com.example.common.dto.orders.OrderItemDTO;
import com.example.common.enums.OrderStatus;
import com.example.common.models.Product;
import com.example.common.models.orders.Order;
import com.example.common.models.orders.OrderItem;
import com.example.orderservice.repo.OrderRepo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.beans.factory.annotation.Autowired;
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
    private CacheService<Long, OrderDTO> cacheService;

    @Autowired
    private CacheService<String, Object> globalCacheService;

    @Autowired
    private RestTemplate restTemplate;
    private String productServiceUrl = "http://localhost:8083/api/products/";

    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();


    // @Autowired
    // public OrderService(OrderRepo orderRepo, RestTemplate restTemplate) {
    //     this.orderRepo = orderRepo;
    //     this.restTemplate = restTemplate;
    // }

    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> cachedOrders = (List<OrderDTO>) globalCacheService.get("allOrders");
        if (cachedOrders != null) {
            System.out.println("Cache hit for all orders");
            return cachedOrders;
        }
        
        List<OrderDTO> orders = orderRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        // Update global cache
        globalCacheService.put("allOrders", orders);

        return orders;
    }

    public OrderDTO getOrderById(Long id) {
         // Check in Redis cache
         OrderDTO cachedOrder = cacheService.get(id);
         if (cachedOrder != null) {
            System.out.println("Cache hit for order: " + id);
             return cachedOrder;
         }

        Order order = orderRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));

        OrderDTO orderDTO = convertToDTO(order);

        // Update Redis cache
        cacheService.put(id, orderDTO);

        return orderDTO;
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
        
        OrderDTO orderDTO = convertToDTO(savedOrder);

        // Add the created order to Redis
        cacheService.put(savedOrder.getId(), orderDTO);

        return orderDTO;
    }

    @Transactional
    public OrderDTO updateOrderStatus(Long id, OrderStatus newStatus) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
        order.setStatus(newStatus);

        Order updatedOrder = orderRepo.save(order);

      

        if(updatedOrder.getStatus() == OrderStatus.CANCELLED) {
            // Refund the stock
            updatedOrder.getOrderItems().forEach(orderItem -> {
                Product product = getProductFromApi(orderItem.getProductId());
                product.setStockQuantity(product.getStockQuantity() + orderItem.getQuantity());
                updateProductStockInApi(product);
            });
        }


        OrderDTO orderDTO = convertToDTO(updatedOrder);

        if(updatedOrder.getStatus() == OrderStatus.DELIVERED){
            // Clear the order from Redis
            cacheService.evict(id);
        }else{
            // Update the order in Redis
            cacheService.put(id, orderDTO);
        }

        return orderDTO;
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

    public String getOrderJson(OrderDTO order) {
        return gson.toJson(order);
    }

    public OrderDTO getOrderFromJson(String json) {
        return gson.fromJson(json, OrderDTO.class);
    }
}

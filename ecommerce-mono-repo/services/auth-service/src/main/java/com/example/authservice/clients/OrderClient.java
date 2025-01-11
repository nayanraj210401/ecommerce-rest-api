package com.example.authservice.clients;

import com.example.shared.models.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service")
public interface OrderClient {

    @GetMapping("/orders/{id}")
    Order getOrderById(@PathVariable Long id);
}

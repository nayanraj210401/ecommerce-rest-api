package com.example.orderservice.clients;

import com.example.shared.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service")
public interface AuthClient {

    @GetMapping("/auth/users/{id}")
    User getUserById(@PathVariable("id") Long id);
}

package com.example.ecommerce.dto.users;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private UserDTO user;
}
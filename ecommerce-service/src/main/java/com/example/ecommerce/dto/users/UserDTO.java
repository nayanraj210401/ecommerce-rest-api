package com.example.ecommerce.dto.users;

import lombok.Data;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Set<String> roles;
}
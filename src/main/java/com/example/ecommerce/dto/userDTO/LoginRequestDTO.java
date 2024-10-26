package com.example.ecommerce.dto.userDTO;

import lombok.Data;

@Data
public class LoginRequestDTO {
  private String email;
  private String password;
}

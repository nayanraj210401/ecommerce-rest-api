package com.example.ecommerce.dto.userDTO;

import lombok.Data;

@Data
public class RegisterRequestDTO {
  private String firstName;
  private String lastName;
  private String email;
  private String password;
}

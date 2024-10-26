package com.example.ecommerce.dto.userDTO;

import com.example.ecommerce.dto.RequestStatus;

import lombok.Data;

@Data
public class RegisterResponseDTO {
  private String email;
  private RequestStatus status;
}

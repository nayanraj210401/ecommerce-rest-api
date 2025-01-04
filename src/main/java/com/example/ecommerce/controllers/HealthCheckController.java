package com.example.ecommerce.controllers;

import com.example.ecommerce.dto.HealthDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @GetMapping
  public ResponseEntity<HealthDTO> healthCheck() {
        HealthDTO healthDTO = new HealthDTO();
        healthDTO.setDb(true);
        return ResponseEntity.ok(healthDTO);
  }
}

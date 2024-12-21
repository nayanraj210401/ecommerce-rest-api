package com.example.ecommerce.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    @GetMapping
  public String healthCheck() {
      return "OK...";
  }

}

package com.example.ecommerce.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/healthcheck")
public class HealthCheckController {
  
  @GetMapping("/")
  public String healthCheck(@RequestParam String param) {
     System.out.println("Health check endpoint hit with param: " + param);
      return "OK...";
  }

}

package com.example.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.RequestStatus;
import com.example.ecommerce.dto.userDTO.LoginRequestDTO;
import com.example.ecommerce.dto.userDTO.LoginResponseDTO;
import com.example.ecommerce.dto.userDTO.RegisterRequestDTO;
import com.example.ecommerce.dto.userDTO.RegisterResponseDTO;
import com.example.ecommerce.models.User;
import com.example.ecommerce.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/users")
public class UserController {
    
  @Autowired
  private UserService userService;


  @GetMapping("/test")
  public ResponseEntity<String> getMethodName() {
    return ResponseEntity.ok("Hello World");
  }

  @PostMapping("/register") 
  public RegisterResponseDTO register(@RequestBody RegisterRequestDTO user) {
    
    RegisterResponseDTO response = new RegisterResponseDTO();

    try{
      User newUser = userService.register(user);

      if(newUser == null){
        response.setEmail(null);
        response.setStatus(RequestStatus.FAILED);
        return response;
      }

      response.setEmail(newUser.getEmail());
      response.setStatus(RequestStatus.SUCCESS);
    }catch(Exception e){
      response.setEmail(null);
     response.setStatus(RequestStatus.FAILED); 
    }
    return response;
  }

  @PostMapping("/login")
  public LoginResponseDTO postMethodName(@RequestBody LoginRequestDTO entity) {
    LoginResponseDTO response = new LoginResponseDTO();
    
    try{
      User user = userService.login(entity.getEmail(), entity.getPassword());

      if(user == null){
        response.setEmail(null);
        response.setStatus(RequestStatus.FAILED);
        return response;
      }

      response.setEmail(user.getEmail());
      response.setStatus(RequestStatus.SUCCESS);
    }catch(Exception e){
      response.setStatus(RequestStatus.FAILED); 
    }    
    return response;
  }
  
}

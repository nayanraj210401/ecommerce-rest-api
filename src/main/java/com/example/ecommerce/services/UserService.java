package com.example.ecommerce.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.userDTO.RegisterRequestDTO;
import com.example.ecommerce.models.User;
import com.example.ecommerce.repositories.UserRepository;

@Service
public class UserService {
  
  Map<String, User> loggedInUsers = new HashMap<>();

  @Autowired
  private UserRepository userRepository;

  public User register(RegisterRequestDTO user){
    User newUser = new User();
    newUser.setEmail(user.getEmail());
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String encryptedPassword = encoder.encode(user.getPassword());
    newUser.setPassword(encryptedPassword);
    newUser.setFirstName(user.getFirstName());
    newUser.setLastName(user.getLastName());
    return userRepository.save(newUser);
  }
  
  public User login(String email, String password){
    Optional<User> user = userRepository.findByEmail(email);

    if(!user.isPresent()){
      return null;
    }
    
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    if(encoder.matches(password, user.get().getPassword())){
      loggedInUsers.put(email, user.get());
      return user.get();
    }

    return null;
  }
}

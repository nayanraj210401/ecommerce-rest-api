package com.example.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  
  public Optional<User> findByEmail(String email);
} 

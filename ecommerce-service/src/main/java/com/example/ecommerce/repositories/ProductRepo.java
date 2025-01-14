package com.example.ecommerce.repositories;

import com.example.common.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
}

package com.example.orderservice.repo;

import com.example.orderservice.models.CachedOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<CachedOrder, Long> {
    List<CachedOrder> findByUserId(Long userId);
}

package com.example.orderservice.models;

import com.example.common.models.Order;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
public class CachedOrder extends Order {
}

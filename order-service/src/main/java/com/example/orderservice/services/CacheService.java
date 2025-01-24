package com.example.orderservice.services;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class CacheService<K, V> {

    private final ConcurrentHashMap<K, CacheEntry<V>> cache = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final long DEFAULT_TTL = 60; // Default TTL in seconds

    // Get value from cache
    public V get(K key) {
        CacheEntry<V> entry = cache.get(key);
        if (entry != null && !entry.isExpired()) {
            return entry.getValue();
        }
        cache.remove(key);
        return null;
    }

    // Add or update value in cache with optional TTL
    public void put(K key, V value) {
        put(key, value, DEFAULT_TTL, TimeUnit.SECONDS);
    }

    public void put(K key, V value, long ttl, TimeUnit unit) {
        CacheEntry<V> entry = new CacheEntry<>(value, System.currentTimeMillis() + unit.toMillis(ttl));
        cache.put(key, entry);
        scheduler.schedule(() -> cache.remove(key), ttl, unit);
    }

    // Remove value from cache
    public void evict(K key) {
        cache.remove(key);
    }

    // Clear the entire cache
    public void clear() {
        cache.clear();
    }

    private static class CacheEntry<V> {
        private final V value;
        private final long expiryTime;

        CacheEntry(V value, long expiryTime) {
            this.value = value;
            this.expiryTime = expiryTime;
        }

        public V getValue() {
            return value;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }
}
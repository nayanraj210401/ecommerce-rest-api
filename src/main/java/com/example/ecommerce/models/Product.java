package com.example.ecommerce.models;

import jakarta.persistence.*;

import java.util.List;


@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private double price;
    private String imageUrl;

    // relations
    @ManyToMany(mappedBy = "products")
    private List<Cart> carts;
}

package com.example.ecommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;


@Data
@Entity
public class Product extends BaseModel{
    private String name;
    private String description;
    private double price;
    private String imageUrl;

    // relations
    @ManyToMany(mappedBy = "products")
    private List<Cart> carts;
}

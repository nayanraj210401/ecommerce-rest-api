package com.example.ecommerce.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Table(name="PRODUCTS")
@Data
public class Product extends BaseModel {

    private String name;
    private String description;
    private double price;
    private String imageUrl;

    @ManyToMany(mappedBy = "products")
    private List<Cart> carts;
}

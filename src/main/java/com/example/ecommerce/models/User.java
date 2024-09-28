package com.example.ecommerce.models;

import jakarta.persistence.*;

import java.util.List;


@Entity
public class User extends BaseModel{

    private String email;
    private String password;
    private String firstName;
    private String lastName;

    // relations
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CustomerOrder> customerOrders;
}

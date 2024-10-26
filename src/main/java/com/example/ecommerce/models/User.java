package com.example.ecommerce.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Data
@Table("users")
public class User extends BaseModel{

    private String email;
    private String password;
    private String firstName;
    private String lastName;

    // relations
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;
}

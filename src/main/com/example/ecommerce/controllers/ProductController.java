package com.example.ecommerce.controllers;


import com.example.ecommerce.dto.products.CreateProductRequest;
import com.example.ecommerce.dto.products.ProductDTO;
import com.example.ecommerce.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody CreateProductRequest createProduct){
        return ResponseEntity.ok(productService.createProduct(createProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,
                                                    @Valid @RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
    }

    @DeleteMapping
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}

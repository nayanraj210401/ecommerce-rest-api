package com.example.productservice.services;

import com.example.productservice.dto.products.CreateProductRequest;
import com.example.productservice.dto.products.ProductDTO;
import com.example.shared.models.Product;
import com.example.productservice.repositories.ProductRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<ProductDTO> getAllProducts(){
        return productRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id){
        Product product = productRepo.findById(id).orElseThrow( () -> new EntityNotFoundException("Product not found"));
        return convertToDTO(product);
    }

    @Transactional
    public ProductDTO createProduct(CreateProductRequest productDTO){
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setStockQuantity(productDTO.getStockQuantity());
        // repo gives us ID
        return convertToDTO(productRepo.save(product));
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO){
        Product product = productRepo.findById(id).orElseThrow( () -> new EntityNotFoundException("Product not found"));

        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setStockQuantity(productDTO.getStockQuantity());
        return convertToDTO(productRepo.save(product));
    }

    @Transactional
    public void deleteProduct(Long id){
        if(!productRepo.existsById(id)){
            throw new EntityNotFoundException("Product not found");
        }

        productRepo.deleteById(id);
    }


    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        return dto;
    }
}

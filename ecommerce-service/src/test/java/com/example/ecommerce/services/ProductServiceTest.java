package com.example.ecommerce.services;

import com.example.ecommerce.dto.products.CreateProductRequest;
import com.example.ecommerce.dto.products.ProductDTO;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.repositories.ProductRepo;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepo productRepo;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setPrice(BigDecimal.valueOf(10.0));

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setPrice(BigDecimal.valueOf(20.0));

        List<Product> products = Arrays.asList(product1, product2);

        when(productRepo.findAll()).thenReturn(products);

        List<ProductDTO> productDTOs = productService.getAllProducts();

        assertEquals(2, productDTOs.size());
        assertEquals("Product 1", productDTOs.get(0).getName());
        assertEquals("Product 2", productDTOs.get(1).getName());
    }

    @Test
    public void testGetProductById() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(BigDecimal.valueOf(10.0));

        when(productRepo.findById(1L)).thenReturn(Optional.of(product));

        ProductDTO productDTO = productService.getProductById(1L);

        assertNotNull(productDTO);
        assertEquals("Product 1", productDTO.getName());
    }

    @Test
    public void testCreateProduct() {
        CreateProductRequest createProductRequest = new CreateProductRequest();
        createProductRequest.setName("Product 1");
        createProductRequest.setPrice(BigDecimal.valueOf(10.0));
        createProductRequest.setDescription("Description 1");
        createProductRequest.setStockQuantity(100);

        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(BigDecimal.valueOf(10.0));
        product.setDescription("Description 1");
        product.setStockQuantity(100);

        when(productRepo.save(any(Product.class))).thenReturn(product);

        ProductDTO productDTO = productService.createProduct(createProductRequest);

        assertNotNull(productDTO);
        assertEquals("Product 1", productDTO.getName());
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(BigDecimal.valueOf(10.0));
        product.setDescription("Description 1");
        product.setStockQuantity(100);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Updated Product 1");
        productDTO.setPrice(BigDecimal.valueOf(15.0));
        productDTO.setDescription("Updated Description 1");
        productDTO.setStockQuantity(150);

        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        when(productRepo.save(any(Product.class))).thenReturn(product);

        ProductDTO updatedProductDTO = productService.updateProduct(1L, productDTO);

        assertNotNull(updatedProductDTO);
        assertEquals("Updated Product 1", updatedProductDTO.getName());
    }

    @Test
    public void testDeleteProduct() {
        when(productRepo.existsById(1L)).thenReturn(true);
        doNothing().when(productRepo).deleteById(1L);

        assertDoesNotThrow(() -> productService.deleteProduct(1L));

        verify(productRepo, times(1)).deleteById(1L);
    }
}

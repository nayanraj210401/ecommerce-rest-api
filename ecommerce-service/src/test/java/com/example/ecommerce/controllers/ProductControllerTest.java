package com.example.ecommerce.controllers;

import com.example.ecommerce.dto.products.CreateProductRequest;
import com.example.ecommerce.dto.products.ProductDTO;
import com.example.ecommerce.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        ProductDTO product1 = new ProductDTO();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setPrice(BigDecimal.valueOf(10.0));

        ProductDTO product2 = new ProductDTO();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setPrice(BigDecimal.valueOf(20.0));

        List<ProductDTO> products = Arrays.asList(product1, product2);

        when(productService.getAllProducts()).thenReturn(products);

        ResponseEntity<List<ProductDTO>> response = productController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }

    @Test
    public void testGetProductById() {
        ProductDTO product = new ProductDTO();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(BigDecimal.valueOf(10.0));

        when(productService.getProductById(1L)).thenReturn(product);

        ResponseEntity<ProductDTO> response = productController.getProductById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testCreateProduct() {
        CreateProductRequest createProductRequest = new CreateProductRequest();
        createProductRequest.setName("Product 1");
        createProductRequest.setPrice(BigDecimal.valueOf(10.0));
        createProductRequest.setDescription("Description 1");
        createProductRequest.setStockQuantity(100);

        ProductDTO product = new ProductDTO();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(BigDecimal.valueOf(10.0));
        product.setDescription("Description 1");
        product.setStockQuantity(100);

        when(productService.createProduct(createProductRequest)).thenReturn(product);

        ResponseEntity<ProductDTO> response = productController.createProduct(createProductRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testUpdateProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Product 1");
        productDTO.setPrice(BigDecimal.valueOf(10.0));
        productDTO.setDescription("Description 1");
        productDTO.setStockQuantity(100);

        when(productService.updateProduct(1L, productDTO)).thenReturn(productDTO);

        ResponseEntity<ProductDTO> response = productController.updateProduct(1L, productDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDTO, response.getBody());
    }

    @Test
    public void testDeleteProduct() {
        doNothing().when(productService).deleteProduct(1L);

        ResponseEntity<ProductDTO> response = productController.deleteProduct(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).deleteProduct(1L);
    }
}

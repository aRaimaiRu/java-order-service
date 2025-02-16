package com.eshop.service.order_product_service.service;

import com.eshop.service.order_product_service.dto.CreateProductRequest;
import com.eshop.service.order_product_service.exception.ProductNotFoundException;
import com.eshop.service.order_product_service.model.Product;
import com.eshop.service.order_product_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        assertTrue(productService.getAllProducts().isEmpty());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById() {
        Product product = new Product();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        assertEquals(product, productService.getProductById(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductByIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    void testCreateProduct() {
        CreateProductRequest req = CreateProductRequest.builder()
                .name("Product1")
                .price(BigDecimal.TEN)
                .description("Description1")
                .stock(10)
                .build();

        Product product = new Product();
        product.setName("Product1");
        product.setPrice(BigDecimal.TEN);
        product.setDescription("Description1");
        product.setStock(10);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.createProduct(req);
        assertNotNull(createdProduct);
        assertEquals("Product1", createdProduct.getName());
        assertEquals(BigDecimal.TEN, createdProduct.getPrice());
        assertEquals("Description1", createdProduct.getDescription());
        assertEquals(10, createdProduct.getStock());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct() {
        CreateProductRequest req = CreateProductRequest.builder()
                .name("UpdatedProduct")
                .price(BigDecimal.valueOf(20))
                .description("UpdatedDescription")
                .stock(20)
                .build();

        Product product = new Product();
        product.setName("Product1");
        product.setPrice(BigDecimal.TEN);
        product.setDescription("Description1");
        product.setStock(10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product updatedProduct = productService.updateProduct(1L, req);
        assertNotNull(updatedProduct);
        assertEquals("UpdatedProduct", updatedProduct.getName());
        assertEquals(BigDecimal.valueOf(20), updatedProduct.getPrice());
        assertEquals("UpdatedDescription", updatedProduct.getDescription());
        assertEquals(20, updatedProduct.getStock());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        productService.deleteProduct(1L);
        verify(productRepository, times(1)).delete(product);
    }
}
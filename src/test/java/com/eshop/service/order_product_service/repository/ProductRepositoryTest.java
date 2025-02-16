package com.eshop.service.order_product_service.repository;

import com.eshop.service.order_product_service.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testSaveProduct() {
        Product product = new Product();
        product.setName("Product1");
        product.setPrice(BigDecimal.TEN);
        product.setDescription("Description1");
        product.setStock(10);
        product = productRepository.save(product);

        assertNotNull(product.getProductId());
        assertEquals("Product1", product.getName());
        assertEquals(BigDecimal.TEN, product.getPrice());
        assertEquals("Description1", product.getDescription());
        assertEquals(10, product.getStock());
    }

    @Test
    void testFindProductById() {
        Product product = new Product();
        product.setName("Product1");
        product.setPrice(BigDecimal.TEN);
        product.setDescription("Description1");
        product.setStock(10);
        product = productRepository.save(product);

        Optional<Product> foundProduct = productRepository.findById(product.getProductId());
        assertTrue(foundProduct.isPresent());
        assertEquals(product.getProductId(), foundProduct.get().getProductId());
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setName("Product1");
        product.setPrice(BigDecimal.TEN);
        product.setDescription("Description1");
        product.setStock(10);
        product = productRepository.save(product);

        productRepository.deleteById(product.getProductId());
        Optional<Product> deletedProduct = productRepository.findById(product.getProductId());
        assertFalse(deletedProduct.isPresent());
    }
}
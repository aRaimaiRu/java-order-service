package com.eshop.service.order_product_service.service;

import com.eshop.service.order_product_service.dto.CreateProductRequest;
import com.eshop.service.order_product_service.exception.ProductNotFoundException;
import com.eshop.service.order_product_service.model.Product;
import com.eshop.service.order_product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    public Product createProduct(CreateProductRequest req) {
        Product product = Product.builder()
                .name(req.getName())
                .price(req.getPrice())
                .description(req.getDescription())
                .stock(req.getStock())
                .build();
        return productRepository.save(product);
    }


    public Product updateProduct(Long id, CreateProductRequest req) {
        Product product = this.getProductById(id);
        product.setName(req.getName());
        product.setPrice(req.getPrice());
        product.setDescription(req.getDescription());
        product.setStock(req.getStock());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = this.getProductById(id);
        productRepository.delete(product);
    }
}
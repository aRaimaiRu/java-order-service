package com.eshop.service.order_product_service.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super("Product not found");
    }


    public ProductNotFoundException(String message) {
        super(message);
    }
}
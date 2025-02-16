package com.eshop.service.order_product_service.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException() {
        super("Not enough stock");
    }
}

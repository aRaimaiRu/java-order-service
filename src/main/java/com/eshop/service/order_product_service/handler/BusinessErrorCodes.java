package com.eshop.service.order_product_service.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum BusinessErrorCodes {
    ORDER_NOT_FOUND(400, NOT_FOUND, "Order not found"),
    PRODUCT_NOT_FOUND(401, NOT_FOUND, "Product not found");

    private final int code;
    private final String description;
    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code, HttpStatus status, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = status;
    }
}
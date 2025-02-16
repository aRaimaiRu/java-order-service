package com.eshop.service.order_product_service.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum BusinessErrorCodes {
    ORDER_NOT_FOUND(NOT_FOUND.value(), NOT_FOUND, "Order not found"),
    PRODUCT_NOT_FOUND(NOT_FOUND.value(), NOT_FOUND, "Product not found"),
    NOT_ENOUGH_STOCK(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "Not enough stock"),
    ORDER_PROCESSED(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN, "Order is already processed");

    private final int code;
    private final String description;
    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code, HttpStatus status, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = status;
    }
}
package com.eshop.service.order_product_service.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
public class CreateOrderRequest {
@NotNull(message = "Products quantity map cannot be null")
    @NotEmpty(message = "Products quantity is required")
    private Map< @Digits(integer = 10, fraction = 0, message = "Stock must be a number") Long, @NotNull @Min(value = 1, message = "Quantity must be at least 1") Integer> productsQuantity;
}

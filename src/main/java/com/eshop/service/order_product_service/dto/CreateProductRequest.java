package com.eshop.service.order_product_service.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;


import java.math.BigDecimal;

@Data
public class CreateProductRequest {
    @NotNull(message = "Product name cannot be null")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "description name cannot be null")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "price name cannot be null")
    @Column(nullable = false)
    private BigDecimal price;

    @NotNull(message = "stock name cannot be null")
    @Column(nullable = false)
    @Min(value = 1, message = "Stock must be at least 1")
    @Digits(integer = 10, fraction = 0, message = "Stock must be a number")
    private Integer stock;

}

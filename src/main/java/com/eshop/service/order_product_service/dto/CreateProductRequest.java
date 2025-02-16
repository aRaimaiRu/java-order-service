package com.eshop.service.order_product_service.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateProductRequest {
    @NotBlank(message = "description cannot be empty")
    private String name;

    @NotBlank(message = "description cannot be empty")
    private String description;

    @NotNull(message = "stock name cannot be null")
    @Min(value = 1, message = "Stock must be at least 1")
    @Digits(integer = 10, fraction = 2, message = "Stock must be a number")
    private BigDecimal price;

    @NotNull(message = "stock name cannot be null")
    @Min(value = 1, message = "Stock must be at least 1")
    @Digits(integer = 10, fraction = 0, message = "Stock must be a number")
    private Integer stock;

}

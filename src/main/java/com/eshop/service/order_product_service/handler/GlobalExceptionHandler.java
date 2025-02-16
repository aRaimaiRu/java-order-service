package com.eshop.service.order_product_service.handler;

import com.eshop.service.order_product_service.exception.OrderNotFoundException;
import com.eshop.service.order_product_service.exception.ProductNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import static com.eshop.service.order_product_service.handler.BusinessErrorCodes.ORDER_NOT_FOUND;
import static com.eshop.service.order_product_service.handler.BusinessErrorCodes.PRODUCT_NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleOrderNotFoundException(OrderNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ExceptionResponse.builder()
                        .errorCode(ORDER_NOT_FOUND.getCode())
                        .description(ORDER_NOT_FOUND.getDescription())
                        .error(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ExceptionResponse.builder()
                        .errorCode(PRODUCT_NOT_FOUND.getCode())
                        .description(PRODUCT_NOT_FOUND.getDescription())
                        .error(ex.getMessage())
                        .build()
        );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ExceptionResponse.builder()
                        .errorCode(HttpStatus.BAD_REQUEST.value())
                        .description(String.join(", ", errors))
                        .error(String.join(", ", errors))
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
    }
}
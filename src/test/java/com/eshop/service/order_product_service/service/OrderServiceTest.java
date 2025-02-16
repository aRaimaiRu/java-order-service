package com.eshop.service.order_product_service.service;

import com.eshop.service.order_product_service.dto.CreateOrderRequest;
import com.eshop.service.order_product_service.exception.NotEnoughStockException;
import com.eshop.service.order_product_service.exception.OrderNotFoundException;
import com.eshop.service.order_product_service.exception.OrderProcessedException;
import com.eshop.service.order_product_service.model.Order;
import com.eshop.service.order_product_service.model.OrderStatus;
import com.eshop.service.order_product_service.model.Product;
import com.eshop.service.order_product_service.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());
        assertTrue(orderService.getAllOrders().isEmpty());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetOrderById() {
        Order order = new Order();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        assertEquals(order, orderService.getOrderById(1L));
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testGetOrderByIdNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    void testCreateOrder() {
        CreateOrderRequest req = new CreateOrderRequest();
        req.setProductsQuantity(Collections.singletonMap(1L, 1));
        Product product = new Product();
        product.setProductId(1L);
        product.setPrice(BigDecimal.TEN);
        product.setStock(10);

        when(productService.getProductById(1L)).thenReturn(product);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order order = orderService.createOrder(req);
        assertNotNull(order);
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(BigDecimal.TEN, order.getTotalAmount());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testCreateOrderNotEnoughStock() {
        CreateOrderRequest req = new CreateOrderRequest();
        req.setProductsQuantity(Collections.singletonMap(1L, 11));
        Product product = new Product();
        product.setProductId(1L);
        product.setStock(10);

        when(productService.getProductById(1L)).thenReturn(product);

        assertThrows(NotEnoughStockException.class, () -> orderService.createOrder(req));
    }

    @Test
    void testUpdateOrder() {
        CreateOrderRequest req = new CreateOrderRequest();
        req.setProductsQuantity(Collections.singletonMap(1L, 1));
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        Product product = new Product();
        product.setProductId(1L);
        product.setPrice(BigDecimal.TEN);
        product.setStock(10);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(productService.getProductById(1L)).thenReturn(product);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order updatedOrder = orderService.updateOrder(1L, req);
        assertNotNull(updatedOrder);
        assertEquals(OrderStatus.PENDING, updatedOrder.getStatus());
        assertEquals(BigDecimal.TEN, updatedOrder.getTotalAmount());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testUpdateOrderProcessed() {
        CreateOrderRequest req = new CreateOrderRequest();
        Order order = new Order();
        order.setStatus(OrderStatus.PROCESSING);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertThrows(OrderProcessedException.class, () -> orderService.updateOrder(1L, req));
    }

    @Test
    void testDeleteOrder() {
        doNothing().when(orderRepository).deleteById(1L);
        orderService.deleteOrder(1L);
        verify(orderRepository, times(1)).deleteById(1L);
    }
}
package com.eshop.service.order_product_service.repository;

import com.eshop.service.order_product_service.model.Order;
import com.eshop.service.order_product_service.model.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void testSaveOrder() {
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(BigDecimal.TEN);
        order = orderRepository.save(order);

        assertNotNull(order.getOrderId());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(BigDecimal.TEN, order.getTotalAmount());
    }

    @Test
    void testFindOrderById() {
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(BigDecimal.TEN);
        order = orderRepository.save(order);

        Optional<Order> foundOrder = orderRepository.findById(order.getOrderId());
        assertTrue(foundOrder.isPresent());
        assertEquals(order.getOrderId(), foundOrder.get().getOrderId());
    }

    @Test
    void testDeleteOrder() {
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(BigDecimal.TEN);
        order = orderRepository.save(order);

        orderRepository.deleteById(order.getOrderId());
        Optional<Order> deletedOrder = orderRepository.findById(order.getOrderId());
        assertFalse(deletedOrder.isPresent());
    }
}
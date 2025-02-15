package com.eshop.service.order_product_service.service;

import com.eshop.service.order_product_service.exception.OrderNotFoundException;
import com.eshop.service.order_product_service.model.Order;
import com.eshop.service.order_product_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order orderDetails) {
        Optional<Order> orderOptional = getOrderById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setProducts(orderDetails.getProducts());
            order.setProductsQuantity(orderDetails.getProductsQuantity());
            return orderRepository.save(order);
        } else {
            throw new OrderNotFoundException();
        }
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}

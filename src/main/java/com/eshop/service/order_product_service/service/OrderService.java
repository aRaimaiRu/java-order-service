package com.eshop.service.order_product_service.service;

import com.eshop.service.order_product_service.dto.CreateOrderRequest;
import com.eshop.service.order_product_service.exception.OrderNotFoundException;
import com.eshop.service.order_product_service.exception.ProductNotFoundException;
import com.eshop.service.order_product_service.model.Order;
import com.eshop.service.order_product_service.model.OrderStatus;
import com.eshop.service.order_product_service.model.Product;
import com.eshop.service.order_product_service.repository.OrderRepository;
import com.eshop.service.order_product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final ProductRepository ProductRepository;

    public Product getProductById(Long id) throws ProductNotFoundException {
        return ProductRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    private Order setOrderFromRequest(Order order, CreateOrderRequest req) {
        order.setProductsQuantity(req.getProductsQuantity());
        Set<Product> products = new HashSet<>();
        BigDecimal total = new BigDecimal(0);
        req.getProductsQuantity().forEach((productId, quantity) -> {
            Product product = getProductById(productId);
            total.add(product.getPrice().multiply(new BigDecimal(quantity)));
            products.add(product);
        });
        order.setProducts(products);
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.PENDING);
        return order;
    }


    public Order createOrder(CreateOrderRequest req) {
        Order order = setOrderFromRequest(new Order(), req);
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, CreateOrderRequest req) {
        Order order = getOrderById(id);
        Order updatedOrder = setOrderFromRequest(order, req);
        return orderRepository.save(updatedOrder);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}

package com.eshop.service.order_product_service.service;

import com.eshop.service.order_product_service.dto.CreateOrderRequest;
import com.eshop.service.order_product_service.exception.OrderNotFoundException;
import com.eshop.service.order_product_service.model.Order;
import com.eshop.service.order_product_service.model.OrderStatus;
import com.eshop.service.order_product_service.model.Product;
import com.eshop.service.order_product_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final ProductService productService;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    private Order setOrderFromRequest(Order order, CreateOrderRequest req) {
        final BigDecimal[] total = {BigDecimal.ZERO};
        req.getProductsQuantity().forEach((productId, quantity) -> {
            Product product = productService.getProductById(productId);

            total[0] = total[0].add(product.getPrice().multiply(new BigDecimal(quantity)));
        });
        order.setTotalAmount(total[0]);
        order.setStatus(OrderStatus.PENDING);
        order.setProductsQuantity(req.getProductsQuantity());
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
        productService.getProductById(id);
        orderRepository.deleteById(id);
    }
}

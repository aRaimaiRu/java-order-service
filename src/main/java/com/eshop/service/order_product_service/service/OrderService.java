package com.eshop.service.order_product_service.service;

import com.eshop.service.order_product_service.dto.CreateOrderRequest;
import com.eshop.service.order_product_service.dto.CreateProductRequest;
import com.eshop.service.order_product_service.exception.NotEnoughStockException;
import com.eshop.service.order_product_service.exception.OrderNotFoundException;
import com.eshop.service.order_product_service.exception.OrderProcessedException;
import com.eshop.service.order_product_service.model.Order;
import com.eshop.service.order_product_service.model.OrderStatus;
import com.eshop.service.order_product_service.model.Product;
import com.eshop.service.order_product_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // lock product order by id to prevent deadlock
        req.getProductsQuantity().keySet().stream().sorted().forEach(productId -> {
            Product product = productService.getProductById(productId);
            int stock = product.getStock() - req.getProductsQuantity().get(productId);
            if (stock < 0) {
                throw new NotEnoughStockException();
            }
            product.setStock(stock);
            CreateProductRequest createProductRequest = CreateProductRequest.builder()
                    .name(product.getName())
                    .price(product.getPrice())
                    .description(product.getDescription())
                    .stock(stock)
                    .build();
            productService.updateProduct(productId, createProductRequest);
            total[0] = total[0].add(product.getPrice().multiply(new BigDecimal(req.getProductsQuantity().get(productId))));
        });
        order.setTotalAmount(total[0]);
        order.setStatus(OrderStatus.PENDING);
        order.setProductsQuantity(req.getProductsQuantity());
        return order;
    }


    @Transactional
    public Order createOrder(CreateOrderRequest req) {
        Order order = setOrderFromRequest(new Order(), req);
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrder(Long id, CreateOrderRequest req) {
        Order order = getOrderById(id);
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new OrderProcessedException("Order is already Processed");
        }
        Order updatedOrder = setOrderFromRequest(order, req);
        return orderRepository.save(updatedOrder);
    }

    public void deleteOrder(Long id) {
        productService.getProductById(id);
        orderRepository.deleteById(id);
    }
}

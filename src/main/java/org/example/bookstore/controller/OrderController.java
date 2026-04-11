package org.example.bookstore.controller;

import org.example.bookstore.entity.Order;
import org.example.bookstore.repository.OrderRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public Iterable<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}

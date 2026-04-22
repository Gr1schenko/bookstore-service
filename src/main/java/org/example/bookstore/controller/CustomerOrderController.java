package org.example.bookstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.dto.request.CreateCustomerOrderDto;
import org.example.bookstore.dto.response.CustomerOrderDto;
import org.example.bookstore.service.CustomerOrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class CustomerOrderController {
    private final CustomerOrderService orderService;

    @GetMapping()
    public List<CustomerOrderDto> getAllCustomerOrders() {
        log.info("getAllCustomerOrders() - start");
        List<CustomerOrderDto> result = orderService.getAllCustomerOrders();
        log.info("getAllCustomerOrders() - end, count: {}", result.size());
        return result;
    }

    @PostMapping
    public CustomerOrderDto createCustomerOrder(@RequestBody CreateCustomerOrderDto request) {
        log.info("createCustomerOrder() - start, customerId: {}, itemsCount: {}",
                request.getCustomerId(),
                request.getOrderItems() != null ? request.getOrderItems().size() : 0);
        CustomerOrderDto result = orderService.createCustomerOrder(request);
        log.info("createCustomerOrder() - end, orderId: {}, totalAmount: {}",
                result.getId(),
                result.getTotalAmount());
        return result;
    }
}

package org.example.bookstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.dto.response.CustomerDto;
import org.example.bookstore.dto.response.CustomerWithOrders;
import org.example.bookstore.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping()
    public List<CustomerDto> getAllCustomers() {
        log.info("getAllCustomers() - start");
        List<CustomerDto> result = customerService.getAllCustomers();
        log.info("getAllCustomers() - end, count: {}", result.size());
        return result;
    }

    @GetMapping("/with-orders")
    public List<CustomerWithOrders> getAllCustomersWithOrders() {
        log.info("getAllCustomersWithOrders() - start");
        List<CustomerWithOrders> result = customerService.getAllCustomersWithOrders();
        log.info("getAllCustomersWithOrders() - end, count: {}", result.size());
        return result;
    }
}

package org.example.bookstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.api.CustomerApi;
import org.example.bookstore.dto.response.CustomerDto;
import org.example.bookstore.dto.response.CustomerWithOrders;
import org.example.bookstore.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerController implements CustomerApi {
    private final CustomerService customerService;

    @Override
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        log.info("getAllCustomers() - start");
        List<CustomerDto> result = customerService.getAllCustomers();
        log.info("getAllCustomers() - end, count: {}", result.size());
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<CustomerWithOrders>> getAllCustomersWithOrders() {
        log.info("getAllCustomersWithOrders() - start");
        List<CustomerWithOrders> result = customerService.getAllCustomersWithOrders();
        log.info("getAllCustomersWithOrders() - end, count: {}", result.size());
        return ResponseEntity.ok(result);
    }
}

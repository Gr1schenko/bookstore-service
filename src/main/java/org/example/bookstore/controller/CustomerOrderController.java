package org.example.bookstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.api.CustomerOrderApi;
import org.example.bookstore.dto.request.CreateCustomerOrderDto;
import org.example.bookstore.dto.response.CustomerOrderDto;
import org.example.bookstore.service.CustomerOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerOrderController implements CustomerOrderApi {
    private final CustomerOrderService orderService;

    @Override
    public ResponseEntity<List<CustomerOrderDto>> getAllCustomerOrders() {
        log.info("getAllCustomerOrders() - start");
        List<CustomerOrderDto> result = orderService.getAllCustomerOrders();
        log.info("getAllCustomerOrders() - end, count: {}", result.size());
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<CustomerOrderDto> createCustomerOrder(CreateCustomerOrderDto request) {
        log.info("createCustomerOrder() - start, customerId: {}, itemsCount: {}",
                request.getCustomerId(),
                request.getOrderItems() != null ? request.getOrderItems().size() : 0);
        CustomerOrderDto result = orderService.createCustomerOrder(request);
        log.info("createCustomerOrder() - end, orderId: {}, totalAmount: {}",
                result.getId(),
                result.getTotalAmount());
        return ResponseEntity.ok(result);
    }
}

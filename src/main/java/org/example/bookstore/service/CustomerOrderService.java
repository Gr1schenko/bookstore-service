package org.example.bookstore.service;

import org.example.bookstore.dto.request.CreateCustomerOrderDto;
import org.example.bookstore.dto.response.CustomerOrderDto;

import java.util.List;

public interface CustomerOrderService {
    List<CustomerOrderDto> getAllCustomerOrders();
    CustomerOrderDto createCustomerOrder(CreateCustomerOrderDto request);
}

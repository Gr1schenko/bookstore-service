package org.example.bookstore.service;

import org.example.bookstore.dto.response.CustomerDto;
import org.example.bookstore.dto.response.CustomerWithOrders;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> getAllCustomers();
    List<CustomerWithOrders> getAllCustomersWithOrders();
}

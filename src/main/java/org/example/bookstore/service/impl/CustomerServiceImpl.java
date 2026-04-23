package org.example.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.dto.response.CustomerDto;
import org.example.bookstore.dto.mapper.CustomerMapper;
import org.example.bookstore.dto.mapper.CustomerOrderSummaryMapper;
import org.example.bookstore.dto.response.CustomerWithOrders;
import org.example.bookstore.entity.Customer;
import org.example.bookstore.repository.CustomerRepository;
import org.example.bookstore.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerOrderSummaryMapper customerOrderSummaryMapper;

    @Override
    public List<CustomerDto> getAllCustomers() {
        log.debug("getAllCustomers() - start");
        List<CustomerDto> result = customerRepository.findAll()
                .stream()
                .map(customerMapper::toDto)
                .toList();
        log.debug("getAllCustomers() - end, count {}", result.size());
        return result;
    }

    @Override
    public List<CustomerWithOrders> getAllCustomersWithOrders() {
        log.debug("getAllCustomersWithOrders() - start");
        List<Customer> customers = customerRepository.findAllWithOrders();

        List<CustomerWithOrders> result = customers.stream().map(c -> {
            CustomerWithOrders response = new CustomerWithOrders();
            response.setCustomer(customerMapper.toDto(c));
            response.setCustomerOrders(
                    customerOrderSummaryMapper.toDtoList(c.getCustomerOrders())
            );
            return response;
        }).toList();
        log.debug("getAllCustomersWithOrders() - end, count {}", result.size());
        return result;
    }
}

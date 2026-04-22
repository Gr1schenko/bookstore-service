package org.example.bookstore;

import org.example.bookstore.dto.response.CustomerDto;
import org.example.bookstore.dto.mapper.CustomerMapper;
import org.example.bookstore.dto.mapper.CustomerOrderSummaryMapper;
import org.example.bookstore.dto.response.CustomerWithOrders;
import org.example.bookstore.entity.Customer;
import org.example.bookstore.repository.CustomerRepository;
import org.example.bookstore.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private CustomerOrderSummaryMapper orderSummaryMapping;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void getAllCustomers_shouldReturnDtoList() {
        Customer customer = new Customer();
        customer.setId(1L);

        CustomerDto dto = new CustomerDto();
        dto.setId(1L);

        when(customerRepository.findAll()).thenReturn(List.of(customer));
        when(customerMapper.toDto(customer)).thenReturn(dto);

        List<CustomerDto> result = customerService.getAllCustomers();

        assertEquals(1, result.size());
        verify(customerRepository).findAll();
    }

    @Test
    void getAllCustomersWithOrders_shouldReturnResponse() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCustomerOrders(new LinkedHashSet<>());

        CustomerDto dto = new CustomerDto();
        dto.setId(1L);

        when(customerRepository.findAllWithOrders()).thenReturn(List.of(customer));
        when(customerMapper.toDto(customer)).thenReturn(dto);
        when(orderSummaryMapping.toDtoList(any())).thenReturn(List.of());

        List<CustomerWithOrders> result = customerService.getAllCustomersWithOrders();

        assertEquals(1, result.size());
        assertEquals(1L, result.getFirst().getCustomer().getId());
    }
}

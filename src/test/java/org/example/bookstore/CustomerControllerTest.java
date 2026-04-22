package org.example.bookstore;

import org.example.bookstore.controller.CustomerController;
import org.example.bookstore.dto.response.CustomerDto;
import org.example.bookstore.dto.response.CustomerWithOrders;
import org.example.bookstore.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Test
    void getAllCustomers_shouldReturnList() {
        CustomerDto dto = new CustomerDto();
        dto.setId(1L);
        dto.setFirstName("John");

        when(customerService.getAllCustomers()).thenReturn(List.of(dto));

        List<CustomerDto> result = customerController.getAllCustomers();

        assertEquals(1, result.size());
        assertEquals("John", result.getFirst().getFirstName());

        verify(customerService).getAllCustomers();
    }

    @Test
    void getAllCustomersWithOrders_shouldReturnList() {
        CustomerWithOrders response = new CustomerWithOrders();

        when(customerService.getAllCustomersWithOrders()).thenReturn(List.of(response));

        List<CustomerWithOrders> result = customerController.getAllCustomersWithOrders();

        assertEquals(1, result.size());

        verify(customerService).getAllCustomersWithOrders();
    }
}

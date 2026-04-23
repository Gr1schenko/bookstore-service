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
import org.springframework.http.ResponseEntity;

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

        ResponseEntity<List<CustomerDto>> response = customerController.getAllCustomers();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        assertEquals("John", response.getBody().getFirst().getFirstName());

        verify(customerService).getAllCustomers();
    }

    @Test
    void getAllCustomersWithOrders_shouldReturnList() {
        CustomerWithOrders responseDto = new CustomerWithOrders();

        when(customerService.getAllCustomersWithOrders()).thenReturn(List.of(responseDto));

        ResponseEntity<List<CustomerWithOrders>> response = customerController.getAllCustomersWithOrders();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());

        verify(customerService).getAllCustomersWithOrders();
    }
}

package org.example.bookstore;

import org.example.bookstore.controller.CustomerOrderController;
import org.example.bookstore.dto.request.CreateCustomerOrderDto;
import org.example.bookstore.dto.request.CreateOrderItemsDto;
import org.example.bookstore.dto.response.CustomerOrderDto;
import org.example.bookstore.service.CustomerOrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerOrderControllerTest {
    @Mock
    private CustomerOrderService orderService;

    @InjectMocks
    private CustomerOrderController controller;

    @Test
    void getAllCustomerOrders_shouldReturnList() {
        CustomerOrderDto dto = new CustomerOrderDto();
        dto.setId(1L);

        when(orderService.getAllCustomerOrders()).thenReturn(List.of(dto));

        List<CustomerOrderDto> result = controller.getAllCustomerOrders();

        assertEquals(1, result.size());

        verify(orderService).getAllCustomerOrders();
    }

    @Test
    void createCustomerOrder_shouldReturnCreatedOrder() {
        CreateCustomerOrderDto request = new CreateCustomerOrderDto();
        request.setCustomerId(1L);
        CreateOrderItemsDto item = new CreateOrderItemsDto();
        item.setBookId(1L);
        item.setQuantity(2);
        request.setOrderItems(List.of(item));

        CustomerOrderDto expectedResponse = new CustomerOrderDto();
        expectedResponse.setId(1L);
        expectedResponse.setTotalAmount(BigDecimal.valueOf(200));

        when(orderService.createCustomerOrder(any(CreateCustomerOrderDto.class))).thenReturn(expectedResponse);

        CustomerOrderDto result = controller.createCustomerOrder(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(BigDecimal.valueOf(200), result.getTotalAmount());

        verify(orderService, times(1)).createCustomerOrder(any(CreateCustomerOrderDto.class));
    }
}

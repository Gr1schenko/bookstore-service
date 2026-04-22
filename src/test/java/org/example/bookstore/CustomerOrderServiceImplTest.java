package org.example.bookstore;

import org.example.bookstore.dto.request.CreateCustomerOrderDto;
import org.example.bookstore.dto.request.CreateOrderItemsDto;
import org.example.bookstore.dto.response.CustomerOrderDto;
import org.example.bookstore.dto.mapper.CustomerOrderMapper;
import org.example.bookstore.entity.Book;
import org.example.bookstore.entity.Customer;
import org.example.bookstore.entity.CustomerOrder;
import org.example.bookstore.repository.BookRepository;
import org.example.bookstore.repository.CustomerOrderRepository;
import org.example.bookstore.repository.CustomerRepository;
import org.example.bookstore.service.DiscountService;
import org.example.bookstore.service.impl.CustomerOrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerOrderServiceImplTest {
    @Mock
    private CustomerOrderRepository customerOrderRepository;

    @Mock
    private CustomerOrderMapper customerOrderMapper;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private DiscountService discountService;

    @InjectMocks
    private CustomerOrderServiceImpl customerOrderService;

    @Test
    void getAllCustomerOrders_shouldReturnDto() {
        CustomerOrder order = new CustomerOrder();
        order.setId(1L);

        CustomerOrderDto dto = new CustomerOrderDto();
        dto.setId(1L);

        when(customerOrderRepository.findAllWithOrderItems()).thenReturn(List.of(order));
        when(customerOrderMapper.toDto(order)).thenReturn(dto);

        List<CustomerOrderDto> result = customerOrderService.getAllCustomerOrders();

        assertEquals(1, result.size());
        verify(customerOrderRepository).findAllWithOrderItems();
    }

    @Test
    void createCustomerOrder_noDiscounts_shouldSucceed() {
        CreateOrderItemsDto itemDto = new CreateOrderItemsDto();
        itemDto.setBookId(1L);
        itemDto.setQuantity(1);

        CreateCustomerOrderDto request = new CreateCustomerOrderDto();
        request.setCustomerId(1L);
        request.setOrderItems(List.of(itemDto));

        Customer customer = new Customer();
        customer.setId(1L);

        Book book = new Book();
        book.setId(1L);
        book.setPrice(BigDecimal.valueOf(100));
        book.setStock(10);

        CustomerOrder savedOrder = new CustomerOrder();
        savedOrder.setId(1L);

        CustomerOrderDto resultDto = new CustomerOrderDto();
        resultDto.setId(1L);
        resultDto.setTotalAmount(BigDecimal.valueOf(100));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(discountService.applyTimeBasedDiscount(BigDecimal.valueOf(100))).thenReturn(BigDecimal.valueOf(100));
        when(discountService.applyWholesaleDiscount(BigDecimal.valueOf(100), 1)).thenReturn(BigDecimal.valueOf(100));
        when(customerOrderRepository.save(any(CustomerOrder.class))).thenReturn(savedOrder);
        when(customerOrderMapper.toDto(savedOrder)).thenReturn(resultDto);

        CustomerOrderDto result = customerOrderService.createCustomerOrder(request);

        assertNotNull(result);
        assertEquals(0, BigDecimal.valueOf(100).compareTo(result.getTotalAmount()));
        verify(discountService).applyTimeBasedDiscount(BigDecimal.valueOf(100));
        verify(discountService).applyWholesaleDiscount(BigDecimal.valueOf(100), 1);
    }

    @Test
    void createCustomerOrder_withTimeDiscountOnly_shouldSucceed() {
        CreateOrderItemsDto itemDto = new CreateOrderItemsDto();
        itemDto.setBookId(1L);
        itemDto.setQuantity(1);

        CreateCustomerOrderDto request = new CreateCustomerOrderDto();
        request.setCustomerId(1L);
        request.setOrderItems(List.of(itemDto));

        Customer customer = new Customer();
        customer.setId(1L);

        Book book = new Book();
        book.setId(1L);
        book.setPrice(BigDecimal.valueOf(100));
        book.setStock(10);

        CustomerOrder savedOrder = new CustomerOrder();
        savedOrder.setId(1L);

        CustomerOrderDto resultDto = new CustomerOrderDto();
        resultDto.setId(1L);
        resultDto.setTotalAmount(BigDecimal.valueOf(50));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(discountService.applyTimeBasedDiscount(BigDecimal.valueOf(100))).thenReturn(BigDecimal.valueOf(50));
        when(discountService.applyWholesaleDiscount(BigDecimal.valueOf(50), 1)).thenReturn(BigDecimal.valueOf(50));
        when(customerOrderRepository.save(any(CustomerOrder.class))).thenReturn(savedOrder);
        when(customerOrderMapper.toDto(savedOrder)).thenReturn(resultDto);

        CustomerOrderDto result = customerOrderService.createCustomerOrder(request);

        assertNotNull(result);
        assertEquals(0, BigDecimal.valueOf(50).compareTo(result.getTotalAmount()));
        verify(discountService).applyTimeBasedDiscount(BigDecimal.valueOf(100));
        verify(discountService).applyWholesaleDiscount(BigDecimal.valueOf(50), 1);
    }

    @Test
    void createCustomerOrder_withWholesaleDiscountOnly_shouldSucceed() {
        CreateOrderItemsDto itemDto = new CreateOrderItemsDto();
        itemDto.setBookId(1L);
        itemDto.setQuantity(5);

        CreateCustomerOrderDto request = new CreateCustomerOrderDto();
        request.setCustomerId(1L);
        request.setOrderItems(List.of(itemDto));

        Customer customer = new Customer();
        customer.setId(1L);

        Book book = new Book();
        book.setId(1L);
        book.setPrice(BigDecimal.valueOf(100));
        book.setStock(10);

        CustomerOrder savedOrder = new CustomerOrder();
        savedOrder.setId(1L);

        CustomerOrderDto resultDto = new CustomerOrderDto();
        resultDto.setId(1L);
        resultDto.setTotalAmount(BigDecimal.valueOf(450));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(discountService.applyTimeBasedDiscount(BigDecimal.valueOf(100))).thenReturn(BigDecimal.valueOf(100));
        when(discountService.applyWholesaleDiscount(BigDecimal.valueOf(500), 5)).thenReturn(BigDecimal.valueOf(450));
        when(customerOrderRepository.save(any(CustomerOrder.class))).thenReturn(savedOrder);
        when(customerOrderMapper.toDto(savedOrder)).thenReturn(resultDto);

        CustomerOrderDto result = customerOrderService.createCustomerOrder(request);

        assertNotNull(result);
        assertEquals(0, BigDecimal.valueOf(450).compareTo(result.getTotalAmount()));
        verify(discountService).applyTimeBasedDiscount(BigDecimal.valueOf(100));
        verify(discountService).applyWholesaleDiscount(BigDecimal.valueOf(500), 5);
    }

    @Test
    void createCustomerOrder_withBothDiscounts_shouldSucceed() {
        CreateOrderItemsDto itemDto = new CreateOrderItemsDto();
        itemDto.setBookId(1L);
        itemDto.setQuantity(5);

        CreateCustomerOrderDto request = new CreateCustomerOrderDto();
        request.setCustomerId(1L);
        request.setOrderItems(List.of(itemDto));

        Customer customer = new Customer();
        customer.setId(1L);

        Book book = new Book();
        book.setId(1L);
        book.setPrice(BigDecimal.valueOf(100));
        book.setStock(10);

        CustomerOrder savedOrder = new CustomerOrder();
        savedOrder.setId(1L);

        CustomerOrderDto resultDto = new CustomerOrderDto();
        resultDto.setId(1L);
        resultDto.setTotalAmount(BigDecimal.valueOf(225));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(discountService.applyTimeBasedDiscount(BigDecimal.valueOf(100))).thenReturn(BigDecimal.valueOf(50));
        when(discountService.applyWholesaleDiscount(BigDecimal.valueOf(250), 5)).thenReturn(BigDecimal.valueOf(225));
        when(customerOrderRepository.save(any(CustomerOrder.class))).thenReturn(savedOrder);
        when(customerOrderMapper.toDto(savedOrder)).thenReturn(resultDto);

        CustomerOrderDto result = customerOrderService.createCustomerOrder(request);

        assertNotNull(result);
        assertEquals(0, BigDecimal.valueOf(225).compareTo(result.getTotalAmount()));
        verify(discountService).applyTimeBasedDiscount(BigDecimal.valueOf(100));
        verify(discountService).applyWholesaleDiscount(BigDecimal.valueOf(250), 5);
    }
    @Test
    void createCustomerOrder_customerNotFound_shouldThrowException() {
        CreateOrderItemsDto itemDto = new CreateOrderItemsDto();
        itemDto.setBookId(1L);
        itemDto.setQuantity(1);

        CreateCustomerOrderDto request = new CreateCustomerOrderDto();
        request.setCustomerId(999L);
        request.setOrderItems(List.of(itemDto));

        when(customerRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerOrderService.createCustomerOrder(request);
        });

        assertEquals("Customer not found with id: 999", exception.getMessage());
        verify(bookRepository, never()).findById(any());
    }

    @Test
    void createCustomerOrder_emptyOrderItems_shouldThrowException() {
        CreateCustomerOrderDto request = new CreateCustomerOrderDto();
        request.setCustomerId(1L);
        request.setOrderItems(List.of());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerOrderService.createCustomerOrder(request);
        });

        assertEquals("Order must contain at least one item", exception.getMessage());
        verify(customerRepository, never()).findById(any());
    }

    @Test
    void createCustomerOrder_bookNotFound_shouldThrowException() {
        CreateOrderItemsDto itemDto = new CreateOrderItemsDto();
        itemDto.setBookId(999L);
        itemDto.setQuantity(1);

        CreateCustomerOrderDto request = new CreateCustomerOrderDto();
        request.setCustomerId(1L);
        request.setOrderItems(List.of(itemDto));

        Customer customer = new Customer();
        customer.setId(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerOrderService.createCustomerOrder(request);
        });

        assertEquals("Book not found with id: 999", exception.getMessage());
    }

    @Test
    void createCustomerOrder_insufficientStock_shouldThrowException() {
        CreateOrderItemsDto itemDto = new CreateOrderItemsDto();
        itemDto.setBookId(1L);
        itemDto.setQuantity(100);

        CreateCustomerOrderDto request = new CreateCustomerOrderDto();
        request.setCustomerId(1L);
        request.setOrderItems(List.of(itemDto));

        Customer customer = new Customer();
        customer.setId(1L);

        Book book = new Book();
        book.setId(1L);
        book.setStock(10);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerOrderService.createCustomerOrder(request);
        });

        assertTrue(exception.getMessage().contains("Not enough stock for book with id: 1"));
    }
}

package org.example.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.dto.request.CreateCustomerOrderDto;
import org.example.bookstore.dto.request.CreateOrderItemsDto;
import org.example.bookstore.dto.response.CustomerOrderDto;
import org.example.bookstore.dto.mapper.CustomerOrderMapper;
import org.example.bookstore.entity.Book;
import org.example.bookstore.entity.Customer;
import org.example.bookstore.entity.CustomerOrder;
import org.example.bookstore.entity.OrderItem;
import org.example.bookstore.repository.BookRepository;
import org.example.bookstore.repository.CustomerOrderRepository;
import org.example.bookstore.repository.CustomerRepository;
import org.example.bookstore.service.CustomerOrderService;
import org.example.bookstore.service.DiscountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerOrderServiceImpl implements CustomerOrderService {
    private final CustomerOrderRepository customerOrderRepository;
    private final CustomerOrderMapper customerOrderMapper;
    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;
    private final DiscountService discountService;

    @Override
    public List<CustomerOrderDto> getAllCustomerOrders() {
        log.debug("getAllCustomerOrders() - start");
        List<CustomerOrderDto> result = customerOrderRepository.findAllWithOrderItems()
                .stream()
                .map(customerOrderMapper::toDto)
                .toList();
        log.debug("getAllCustomerOrders() - end, count: {}", result.size());
        return result;
    }

    @Override
    @Transactional
    public CustomerOrderDto createCustomerOrder(CreateCustomerOrderDto request) {
        log.debug("createCustomerOrder() - start, customerId: {}, itemsCount: {}", request.getCustomerId(),
                request.getOrderItems() != null ? request.getOrderItems().size() : 0);

        if (request.getOrderItems() == null || request.getOrderItems().isEmpty()) {
            log.warn("createCustomerOrder() - order must contain at least one item for customerId: {}", request.getCustomerId());
            throw new RuntimeException("Order must contain at least one item");
        }

        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() -> {
            log.warn("createCustomerOrder() - customer not found with id: {}", request.getCustomerId());
            return new RuntimeException("Customer not found with id: " + request.getCustomerId());
        });

        CustomerOrder order = new CustomerOrder();
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());
        order.setStatus("PENDING");

        Set<OrderItem> orderItems = new LinkedHashSet<>();
        int totalQuantity = 0;
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CreateOrderItemsDto itemReq : request.getOrderItems()) {
            if (itemReq.getQuantity() <= 0) {
                log.warn("createCustomerOrder() - invalid quantity {} for book with id: {}", itemReq.getQuantity(), itemReq.getBookId());
                throw new RuntimeException("Quantity must be positive for book with id: " + itemReq.getBookId());
            }

            Book book = bookRepository.findById(itemReq.getBookId()).orElseThrow(() -> {
                log.warn("createCustomerOrder() - book not found with id: {}", itemReq.getBookId());
                return new RuntimeException("Book not found with id: " + itemReq.getBookId());
            });

            if (book.getStock() < itemReq.getQuantity()) {
                log.warn("createCustomerOrder() - insufficient stock for book with id: {}, available: {}, requested: {}", book.getId(), book.getStock(), itemReq.getQuantity());
                throw new RuntimeException("Not enough stock for book with id: " + book.getId() + ". Available: " + book.getStock() + ", requested: " + itemReq.getQuantity());
            }

            book.setStock(book.getStock() - itemReq.getQuantity());

            // цена за единицу с временной скидкой, если она активна
            BigDecimal pricePerUnit = discountService.applyTimeBasedDiscount(book.getPrice());

            // цена за всю позицию
            BigDecimal totalPriceForPosition = pricePerUnit.multiply(BigDecimal.valueOf(itemReq.getQuantity()));

            OrderItem orderItem = new OrderItem();
            orderItem.setCustomerOrder(order);
            orderItem.setBook(book);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setPriceAtOrder(totalPriceForPosition);
            orderItems.add(orderItem);

            totalQuantity += itemReq.getQuantity();
            totalAmount = totalAmount.add(totalPriceForPosition);
        }

        order.setOrderItems(orderItems);

        // оптовая скидка только на финальную сумму заказа
        totalAmount = discountService.applyWholesaleDiscount(totalAmount, totalQuantity);
        order.setTotalAmount(totalAmount);

        CustomerOrder savedOrder = customerOrderRepository.save(order);
        CustomerOrderDto result = customerOrderMapper.toDto(savedOrder);

        log.debug("createCustomerOrder() - end, orderId: {}, totalAmount: {}", result.getId(), result.getTotalAmount());
        return result;
    }
}

package org.example.bookstore.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CustomerOrderDto {
    private Long id;
    private LocalDate orderDate;
    private String status;
    private BigDecimal totalAmount;
    private List<OrderItemDto> orderItems;
}

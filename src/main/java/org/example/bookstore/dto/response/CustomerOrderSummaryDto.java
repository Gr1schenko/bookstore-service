package org.example.bookstore.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CustomerOrderSummaryDto {
    private Long id;
    private LocalDate orderDate;
    private String status;
    private BigDecimal totalAmount;
}

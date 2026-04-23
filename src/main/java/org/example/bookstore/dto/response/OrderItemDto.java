package org.example.bookstore.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemDto {
    private Long id;
    private BookDto book;
    private Integer quantity;
    private BigDecimal priceAtOrder;
}

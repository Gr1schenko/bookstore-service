package org.example.bookstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BookDto {
    private Long id;
    private String title;
    private BigDecimal price;
}
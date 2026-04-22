package org.example.bookstore.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BookDto {
    private Long id;
    private String isbn;
    private String title;
    private String authorFirstName;
    private String authorLastName;
    private String authorMiddleName;
    private String publisher;
    private Integer publicationYear;
    private Integer pages;
    private String genre;
    private BigDecimal price;
    private Integer stock;
    private String description;
}

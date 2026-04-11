package org.example.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 13)
    private String isbn;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(name = "author_first_name", nullable = false, length = 100)
    private String authorFirstName;

    @Column(name = "author_last_name", nullable = false, length = 100)
    private String authorLastName;

    @Column(name = "author_middle_name", length = 100)
    private String authorMiddleName;

    @Column(nullable = false, length = 200)
    private String publisher;

    @Column(name = "publication_year", nullable = false)
    private Integer publicationYear;

    @Column(nullable = false)
    private Integer pages;

    @Column(length = 100)
    private String genre;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    @Column(length = 1000)
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "book")
    private List<OrderItem> orderItems;
}

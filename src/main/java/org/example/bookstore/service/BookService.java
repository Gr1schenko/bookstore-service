package org.example.bookstore.service;

import org.example.bookstore.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> findAllBooks();
}
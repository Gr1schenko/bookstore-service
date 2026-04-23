package org.example.bookstore.service;

import org.example.bookstore.dto.response.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();
}

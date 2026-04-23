package org.example.bookstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.api.BookApi;
import org.example.bookstore.dto.response.BookDto;
import org.example.bookstore.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookController implements BookApi {
    private final BookService bookService;

    @Override
    public ResponseEntity<List<BookDto>> getAllBooks() {
        log.info("getAllBooks() - start");
        List<BookDto> result = bookService.getAllBooks();
        log.info("getAllBooks() - end, count: {}", result.size());
        return ResponseEntity.ok(result);
    }
}

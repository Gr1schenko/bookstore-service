package org.example.bookstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.dto.response.BookDto;
import org.example.bookstore.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping()
    public List<BookDto> getAllBooks() {
        log.info("getAllBooks() - start");
        List<BookDto> result = bookService.getAllBooks();
        log.info("getAllBooks() - end, count: {}", result.size());
        return result;
    }
}

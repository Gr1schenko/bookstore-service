package org.example.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.dto.response.BookDto;
import org.example.bookstore.dto.mapper.BookMapper;
import org.example.bookstore.repository.BookRepository;
import org.example.bookstore.service.BookService;
import org.example.bookstore.service.DiscountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final DiscountService discountService;

    @Override
    public List<BookDto> getAllBooks() {
        log.debug("getAllBooks() - start");
        List<BookDto> result = bookRepository.findAll()
                .stream()
                .map(book -> {
                    BookDto dto = bookMapper.toDto(book);
                    dto.setPrice(discountService.applyTimeBasedDiscount(book.getPrice()));
                    return dto;
                })
                .toList();
        log.debug("getAllBooks() - end, count {}", result.size());
        return result;
    }
}

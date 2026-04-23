package org.example.bookstore;

import org.example.bookstore.dto.response.BookDto;
import org.example.bookstore.dto.mapper.BookMapper;
import org.example.bookstore.entity.Book;
import org.example.bookstore.repository.BookRepository;
import org.example.bookstore.service.DiscountService;
import org.example.bookstore.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private DiscountService discountService;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void getAllBooks_shouldReturnListOfBookDto() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        BookDto dto = new BookDto();
        dto.setId(1L);
        dto.setTitle("Test Book");

        when(bookRepository.findAll()).thenReturn(List.of(book));
        when(bookMapper.toDto(book)).thenReturn(dto);
        when(discountService.applyTimeBasedDiscount(any())).thenAnswer(invocation -> invocation.getArgument(0));

        List<BookDto> result = bookService.getAllBooks();

        assertEquals(1, result.size());
        assertEquals("Test Book", result.getFirst().getTitle());

        verify(bookRepository).findAll();
        verify(bookMapper).toDto(book);
        verify(discountService).applyTimeBasedDiscount(any());
    }
}

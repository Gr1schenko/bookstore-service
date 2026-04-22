package org.example.bookstore;

import org.example.bookstore.controller.BookController;
import org.example.bookstore.dto.response.BookDto;
import org.example.bookstore.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {
    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    @DisplayName("getAllBooks - return list of books")
    void getAllBooks_shouldReturnList() {
        BookDto dto = new BookDto();
        dto.setId(1L);
        dto.setTitle("Test Book");

        when(bookService.getAllBooks()).thenReturn(List.of(dto));

        List<BookDto> result = bookController.getAllBooks();

        assertEquals(1, result.size());
        assertEquals("Test Book", result.getFirst().getTitle());

        verify(bookService, times(1)).getAllBooks();
    }
}

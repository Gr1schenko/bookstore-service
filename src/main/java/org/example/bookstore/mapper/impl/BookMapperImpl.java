package org.example.bookstore.mapper.impl;

import org.example.bookstore.dto.BookDto;
import org.example.bookstore.entity.Book;
import org.example.bookstore.mapper.BookMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements BookMapper {
    @Override
    public BookDto toBookDto(Book book){
        if (book == null) {
            return null;
        }
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setPrice(bookDto.getPrice());
        return bookDto;
    }
}
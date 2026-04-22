package org.example.bookstore.dto.mapper;

import org.example.bookstore.dto.response.BookDto;
import org.example.bookstore.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto toDto(Book book);
}

package org.example.bookstore.mapper;

import org.example.bookstore.dto.BookDto;
import org.example.bookstore.entity.Book;

//Такое не очень хорошо делать, но можно
//В основном мапперы - это уже готовые решения
public interface BookMapper {
    BookDto toBookDto(Book book);
}

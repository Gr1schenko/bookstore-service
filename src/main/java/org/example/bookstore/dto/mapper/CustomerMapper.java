package org.example.bookstore.dto.mapper;

import org.example.bookstore.dto.response.CustomerDto;
import org.example.bookstore.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDto toDto(Customer customer);
}

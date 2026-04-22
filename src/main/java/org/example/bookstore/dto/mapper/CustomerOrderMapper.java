package org.example.bookstore.dto.mapper;

import org.example.bookstore.dto.response.CustomerOrderDto;
import org.example.bookstore.entity.CustomerOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = OrderItemMapper.class)
public interface CustomerOrderMapper {
    CustomerOrderDto toDto(CustomerOrder customerOrder);
}

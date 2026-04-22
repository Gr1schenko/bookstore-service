package org.example.bookstore.dto.mapper;

import org.example.bookstore.dto.response.OrderItemDto;
import org.example.bookstore.entity.OrderItem;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = BookMapper.class)
public interface OrderItemMapper {
    OrderItemDto toDto(OrderItem item);
    List<OrderItemDto> toDtoList(Set<OrderItem> items);
}

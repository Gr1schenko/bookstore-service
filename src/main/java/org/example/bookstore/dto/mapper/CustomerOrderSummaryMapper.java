package org.example.bookstore.dto.mapper;

import org.example.bookstore.dto.response.CustomerOrderSummaryDto;
import org.example.bookstore.entity.CustomerOrder;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface CustomerOrderSummaryMapper {
    CustomerOrderSummaryDto toDto(CustomerOrder customerOrder);
    List<CustomerOrderSummaryDto> toDtoList(Set<CustomerOrder> customerOrders);
}

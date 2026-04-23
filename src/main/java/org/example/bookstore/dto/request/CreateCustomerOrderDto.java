package org.example.bookstore.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateCustomerOrderDto {
    private Long customerId;
    private List<CreateOrderItemsDto> orderItems;
}

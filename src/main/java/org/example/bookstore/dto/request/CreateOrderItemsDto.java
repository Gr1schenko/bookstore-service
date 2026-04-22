package org.example.bookstore.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderItemsDto {
    private Long bookId;
    private Integer quantity;
}

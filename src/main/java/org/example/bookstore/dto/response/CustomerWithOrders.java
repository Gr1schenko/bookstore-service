package org.example.bookstore.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerWithOrders {
    private CustomerDto customer;
    private List<CustomerOrderSummaryDto> customerOrders;
}

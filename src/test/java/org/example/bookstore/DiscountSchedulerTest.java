package org.example.bookstore;

import org.example.bookstore.scheduler.DiscountScheduler;
import org.example.bookstore.service.DiscountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DiscountSchedulerTest {
    @Mock
    private DiscountService discountService;

    @InjectMocks
    private DiscountScheduler scheduler;

    @Test
    void shouldEnableTimeBasedDiscount() {
        scheduler.enableTimeBasedDiscount();

        verify(discountService, times(1)).enableTimeBasedDiscount();
    }

    @Test
    void shouldDisableTimeBasedDiscount() {
        scheduler.disableTimeBasedDiscount();

        verify(discountService, times(1)).disableTimeBasedDiscount();
    }
}

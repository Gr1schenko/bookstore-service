package org.example.bookstore;

import org.example.bookstore.service.DiscountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountServiceTest {
    private DiscountService service;

    @BeforeEach
    void setUp() {
        service = new DiscountService();
        ReflectionTestUtils.setField(service, "timeBasedDiscountRate", BigDecimal.valueOf(0.5));
        ReflectionTestUtils.setField(service, "wholesaleThreshold", 5);
        ReflectionTestUtils.setField(service, "wholesaleRate", BigDecimal.valueOf(0.10));
    }

    @Test
    void applyTimeBasedDiscount_shouldApply50PercentDiscount_whenDiscountIsActive() {
        service.enableTimeBasedDiscount();
        BigDecimal price = BigDecimal.valueOf(100);
        BigDecimal result = service.applyTimeBasedDiscount(price);

        assertEquals(0, BigDecimal.valueOf(50).compareTo(result));
    }

    @Test
    void applyTimeBasedDiscount_shouldReturnSamePrice_whenDiscountIsInactive() {
        service.enableTimeBasedDiscount();
        service.disableTimeBasedDiscount();

        BigDecimal result = service.applyTimeBasedDiscount(BigDecimal.valueOf(100));

        assertEquals(0, BigDecimal.valueOf(100).compareTo(result));
    }

    @Test
    void applyWholesaleDiscount_quantity4_shouldNotApplyDiscount() {
        BigDecimal totalAmount = BigDecimal.valueOf(400);
        int totalQuantity = 4; // threshold = 5, значит скидки нет

        BigDecimal result = service.applyWholesaleDiscount(totalAmount, totalQuantity);

        assertEquals(0, BigDecimal.valueOf(400).compareTo(result));
    }

    @Test
    void applyWholesaleDiscount_quantity5_shouldApplyDiscount() {
        BigDecimal totalAmount = BigDecimal.valueOf(500);
        int totalQuantity = 5; // threshold = 5, скидка 10%

        BigDecimal result = service.applyWholesaleDiscount(totalAmount, totalQuantity);

        assertEquals(0, BigDecimal.valueOf(450).compareTo(result));
    }

    @Test
    void applyWholesaleDiscount_quantity6_shouldApplyDiscount() {
        BigDecimal totalAmount = BigDecimal.valueOf(600);
        int totalQuantity = 6;

        BigDecimal result = service.applyWholesaleDiscount(totalAmount, totalQuantity);

        assertEquals(0, BigDecimal.valueOf(540).compareTo(result));
    }

    @Test
    void applyWholesaleDiscount_quantity0_shouldNotApplyDiscount() {
        BigDecimal totalAmount = BigDecimal.valueOf(0);
        int totalQuantity = 0;

        BigDecimal result = service.applyWholesaleDiscount(totalAmount, totalQuantity);

        assertEquals(0, BigDecimal.valueOf(0).compareTo(result));
    }
}

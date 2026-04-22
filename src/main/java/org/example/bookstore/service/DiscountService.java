package org.example.bookstore.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class DiscountService {
    @Getter
    @Setter
    private volatile boolean isTimeBasedDiscountActive = false;

    @Value("${discount.time-based.rate}")
    private BigDecimal timeBasedDiscountRate;

    @Value("${discount.wholesale.threshold}")
    private int wholesaleThreshold;

    @Value("${discount.wholesale.rate}")
    private BigDecimal wholesaleRate;

    public void enableTimeBasedDiscount() {
        isTimeBasedDiscountActive = true;
        log.debug("enableTimeBasedDiscount() - time-based discount enabled, rate: {}", timeBasedDiscountRate);
    }

    public void disableTimeBasedDiscount() {
        isTimeBasedDiscountActive = false;
        log.debug("disableTimeBasedDiscount() - time-based discount disabled, rate: {}", timeBasedDiscountRate);
    }

    public BigDecimal applyTimeBasedDiscount(BigDecimal price) {
        if (isTimeBasedDiscountActive()) {
            BigDecimal multiplier = BigDecimal.ONE.subtract(timeBasedDiscountRate);
            BigDecimal result = price.multiply(multiplier);
            log.debug("applyTimeBasedDiscount() - time-based discount applied: {} -> {} (rate: {})", price, result, timeBasedDiscountRate);
            return result;
        }
        return price;
    }

    public BigDecimal applyWholesaleDiscount(BigDecimal totalAmount, int totalQuantity) {
        if (totalQuantity >= wholesaleThreshold) {
            BigDecimal result =  totalAmount.multiply(BigDecimal.ONE.subtract(wholesaleRate));
            log.debug("applyWholesaleDiscount() - wholesale discount applied: quantity {}, threshold {}", totalQuantity, wholesaleThreshold);
            return result;
        }
        return totalAmount;
    }
}

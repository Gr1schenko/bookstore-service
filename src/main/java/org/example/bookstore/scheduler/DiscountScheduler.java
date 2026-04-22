package org.example.bookstore.scheduler;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstore.service.DiscountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class DiscountScheduler {
    private final DiscountService discountService;

    @Value("${discount.time-based.start-hour}")
    private int startHour;

    @Value("${discount.time-based.end-hour}")
    private int endHour;

    @PostConstruct
    public void init() {
        int now = LocalTime.now().getHour();

        if (now >= startHour && now < endHour) {
            discountService.enableTimeBasedDiscount();
            log.info("init() - discount enabled on startup");
        } else {
            discountService.disableTimeBasedDiscount();
            log.info("init() - discount disabled on startup");
        }
        log.info("init() - current hour: {}, discount active: {}", now, discountService.isTimeBasedDiscountActive());
    }

    @Scheduled(cron = "0 0 ${discount.time-based.start-hour} * * ?")
    public void enableTimeBasedDiscount() {
        discountService.enableTimeBasedDiscount();
    }

    @Scheduled(cron = "0 0 ${discount.time-based.end-hour} * * ?")
    public void disableTimeBasedDiscount() {
        discountService.disableTimeBasedDiscount();
    }
}

package com.example.Tzms.scheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnExpression("'${app.scheduling.enabled}'=='true' && '${app.scheduling.optimization}'=='true' && ('${spring.profiles.active}' != 'local')")
@Slf4j
public class OptimizedProductPriceScheduling {

    @Scheduled(fixedDelay = 1000)
    public void scheduleFixedDelayTask() {
        log.info(
                "Optimized delay task - " + System.currentTimeMillis() / 1000);
    }

}

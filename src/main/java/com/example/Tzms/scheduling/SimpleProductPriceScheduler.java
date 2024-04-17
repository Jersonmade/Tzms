package com.example.Tzms.scheduling;

import com.example.Tzms.aop.MethodTimer;
import com.example.Tzms.model.Product;
import com.example.Tzms.repository.ProductRepo;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
@Profile("!local")
@ConditionalOnMissingBean(name = "OptimizedProductPriceScheduling")
@Slf4j
@RequiredArgsConstructor
public class SimpleProductPriceScheduler {

    private final ProductRepo productRepo;


    @MethodTimer
    @Scheduled(fixedRateString = "${app.scheduling.period}")
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void updateProductPrice() {
        System.out.println("Start simple scheduling");

        List<Product> products = productRepo.findAll();
        products.forEach(product -> {
            BigDecimal increase = new BigDecimal("1.1");
            product.setPrice(product.getPrice().multiply(increase));
        });

        productRepo.saveAll(products);
    }
    

}

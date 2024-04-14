package com.example.Tzms.scheduling;

import com.example.Tzms.model.Product;
import com.example.Tzms.repository.ProductRepo;
import com.example.Tzms.service.ProductService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnExpression("'${app.scheduling.enabled}'=='true' && '${app.scheduling.optimization}'=='false' && ('${spring.profiles.active}' != 'local')")
@Slf4j
@RequiredArgsConstructor
public class SimpleProductPriceScheduler {

    private final ProductRepo productRepo;

    @PostConstruct
    public void init() {
        log.info("Scheduler started working");
    }

    @PreDestroy
    public void destroy() {
        log.info("Scheduler finished working");
    }

    @Scheduled(fixedDelay = 360000)
    @Transactional
    public void updateProductPrice() {
        List<Product> products = productRepo.findAll();
        List<Product> updatedProducts = products.stream()
                .peek(product -> product.setPrice(product.getPrice() + 10))
                .toList();

        productRepo.saveAll(products);
    }

}

package com.example.Tzms.scheduling;

import com.example.Tzms.aop.MethodTimer;
import com.example.Tzms.model.Product;
import com.example.Tzms.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
@ConditionalOnExpression("'${app.scheduling.enabled}'=='true' && '${app.scheduling.optimization}'=='true'")
@Profile("!local")
@Slf4j
@RequiredArgsConstructor
public class OptimizedProductPriceScheduling {

    private final ProductRepo productRepo;
    private final DataSource dataSource;

    @MethodTimer
    @Scheduled(fixedDelay = 360000)
    @Transactional
    public void scheduleFixedDelayTask() {
        System.out.println("Optimized scheduler start");
        List<Product> products = productRepo.findAll();

        String updateQuery = "UPDATE _product SET price = price * 1.1 WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {

            for (Product product : products) {
                statement.setObject(1, product.getId());
                statement.addBatch();
            }

            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

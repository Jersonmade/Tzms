package com.example.Tzms.scheduling;

import com.example.Tzms.aop.MethodTimer;
import com.example.Tzms.model.Product;
import com.example.Tzms.repository.ProductRepo;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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

    private void writeDataToFile(List<Product> products) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("products.txt"))) {
            for (Product product : products) {
                writer.write(product.toString());
                writer.newLine();
            }
            log.info("Data has been written to the file.");
        } catch (IOException e) {
            log.error("Error occurred while writing data to the file.", e);
        }
    }

    @MethodTimer
    @Scheduled(fixedRateString = "${app.scheduling.period}")
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void scheduleFixedDelayTask() {

        System.out.println("Optimized scheduler start");
        List<Product> products = productRepo.findAll();

        String updateQuery = "UPDATE _product SET price = price * 1.1 WHERE id = ?";

//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(updateQuery)) {
//
//            for (Product product : products) {
//                statement.setObject(1, product.getId());
//                statement.addBatch();
//            }
//
//            statement.executeBatch();
//
//            writeDataToFile(products);
//
//        } catch (SQLException e) {
//            log.error("Error occurred while updating prices.", e);
//        }

        try (Connection connection = dataSource.getConnection();
             PrintWriter writer = new PrintWriter(new FileWriter("products.txt"))) {
            PreparedStatement ps = connection.prepareStatement("UPDATE _product SET price = price * 1.1 WHERE id = ?");
            for (Product product : products) {
                ps.setObject(1, product.getId());
                ps.addBatch();
                writer.println(product.getId() + " "
                        + product.getName() + " "
                        + product.getArticle() + " "
                        + product.getDescription() + " "
                        + (product.getPrice().multiply(new BigDecimal("1.1")))
                        + product.getCategory() + " "
                        + product.getQuantity() + " "
                        + product.getLastQuantityChange() + " "
                        + product.getCreatedAt()
                );
            }
            ps.executeBatch();
        } catch (SQLException e) {
            log.error("Error updating product", e);
        } catch (IOException ex) {
            log.error("Error writing to file", ex);
        }
    }

}

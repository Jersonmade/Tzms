package com.example.Tzms.repository;

import com.example.Tzms.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с объектами типа Product
 */
@Repository
public interface ProductRepo extends JpaRepository<Product, UUID> {
    Optional<Product> findByArticle(String article);
}

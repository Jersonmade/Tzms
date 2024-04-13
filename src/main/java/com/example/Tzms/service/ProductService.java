package com.example.Tzms.service;

import com.example.Tzms.dto.ProductRequest;
import com.example.Tzms.dto.ProductResponse;
import com.example.Tzms.exception.ProductNotFoundException;
import com.example.Tzms.model.Product;
import com.example.Tzms.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Класс, отвечающий за бизнес-логику приложения
 */
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;

    /**
     * Метод для добавления товара в систему
     * @param productRequest
     */
    public void saveProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .id(UUID.randomUUID())
                .name(productRequest.getName())
                .article(productRequest.getArticle())
                .description(productRequest.getDescription())
                .category(productRequest.getCategory())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .lastQuantityChange(new Date())
                .createdAt(new Date())
                .build();

        productRepo.save(product);
    }

    /**
     * Метод для изменения уже существующего товара
     * @param id
     * @param productRequest
     */
    public void updateProduct(UUID id, ProductRequest productRequest) throws ProductNotFoundException {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Товар с артикулом " + id + " не найден"));

        product.setName(productRequest.getName());
        product.setArticle(productRequest.getArticle());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setLastQuantityChange(new Date());

        productRepo.save(product);
    }

    /**
     * Метод для получения всех товаров из бд
     * @return
     */
    public List<ProductResponse> getAllProducts(Integer page) {
        int pageNo = page < 1 ? 0 : page - 1;
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.Direction.DESC, "createdAt");
        List<Product> products = productRepo.findAll(pageable).getContent();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    /**
     * Метод для получения какого-то конкретного товара
     * @param id
     * @return
     */
    public ProductResponse getProductById(UUID id) throws ProductNotFoundException {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Товар с артикулом " + id + " не найден"));

        return mapToProductResponse(product);
    }

    /**
     * Метод для удаления товара из системы
     * @param id
     */
    public void deleteProductByUuid(UUID id) {
        productRepo.deleteById(id);
    }

    /**
     * Метод для преобразования сущности товара в модель ответа
     * @param product
     * @return
     */
    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .article(product.getArticle())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .lastQuantityChange(product.getLastQuantityChange())
                .createdAt(product.getCreatedAt())
                .build();
    }
}

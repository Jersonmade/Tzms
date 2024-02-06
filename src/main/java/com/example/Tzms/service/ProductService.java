package com.example.Tzms.service;

import com.example.Tzms.dto.ProductRequest;
import com.example.Tzms.dto.ProductResponse;
import com.example.Tzms.model.Product;
import com.example.Tzms.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;

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

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepo.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    public ProductResponse getProductById(UUID id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Товар с артикулом " + id + " не найден"));

        return mapToProductResponse(product);
    }

    public void deleteProductByUuid(UUID id) {
        productRepo.deleteById(id);
    }

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

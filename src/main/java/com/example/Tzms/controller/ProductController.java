package com.example.Tzms.controller;

import com.example.Tzms.dto.ProductRequest;
import com.example.Tzms.dto.ProductResponse;
import com.example.Tzms.exception.ProductNotFoundException;
import com.example.Tzms.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Класс, представляющий собой контроллер для обработки запросов
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveProduct(@Valid @RequestBody ProductRequest productRequest) {
        productService.saveProduct(productRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(
            @PathVariable UUID id,
            @Valid @RequestBody ProductRequest productRequest
    ) throws ProductNotFoundException {
        productService.updateProduct(id, productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(@RequestParam(name = "page", defaultValue = "1") Integer page) {
        return productService.getAllProducts(page);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProduct(@PathVariable UUID id) throws ProductNotFoundException {
        return productService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable UUID id) throws ProductNotFoundException {
        productService.deleteProductByUuid(id);
    }
}

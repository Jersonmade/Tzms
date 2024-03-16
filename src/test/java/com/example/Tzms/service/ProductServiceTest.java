package com.example.Tzms.service;

import com.example.Tzms.dto.ProductRequest;
import com.example.Tzms.dto.ProductResponse;
import com.example.Tzms.exception.ProductNotFoundException;
import com.example.Tzms.model.Product;
import com.example.Tzms.repository.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    @Mock
    private ProductRepo productRepo;
    @InjectMocks
    private ProductService productService;
    private Product product1;
    private Product product2;
    private ProductRequest productRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        this.product1 = new Product();
        product1.setId(UUID.randomUUID());
        product1.setName("Product 1");
        product1.setArticle("123456");
        product1.setDescription("Description for Product 1");
        product1.setCategory("Category 1");
        product1.setPrice(100);
        product1.setQuantity(10);
        product1.setLastQuantityChange(new Date());
        product1.setCreatedAt(new Date());

        this.product2 = new Product();
        product2.setId(UUID.randomUUID());
        product2.setName("Product 2");
        product2.setArticle("789012");
        product2.setDescription("Description for Product 2");
        product2.setCategory("Category 2");
        product2.setPrice(200);
        product2.setQuantity(20);
        product2.setLastQuantityChange(new Date());
        product2.setCreatedAt(new Date());

        this.productRequest = new ProductRequest();
        productRequest.setName("Test Product");
        productRequest.setArticle("1234567");
        productRequest.setDescription("Test Description");
        productRequest.setCategory("Test Category");
        productRequest.setPrice(300);
        productRequest.setQuantity(30);
    }

    @Test
    void saveProduct() {
        when(productRepo.save(any(Product.class))).thenAnswer(invocationOnMock -> {
            Product savedProduct = invocationOnMock.getArgument(0);
            assert savedProduct.getId() != null;
            assert savedProduct.getName().equals(productRequest.getName());
            assert savedProduct.getArticle().equals(productRequest.getArticle());
            assert savedProduct.getDescription().equals(productRequest.getDescription());
            assert savedProduct.getCategory().equals(productRequest.getCategory());
            assert savedProduct.getPrice().equals(productRequest.getPrice());
            assert savedProduct.getQuantity().equals(productRequest.getQuantity());
            assert savedProduct.getLastQuantityChange() != null;
            assert savedProduct.getCreatedAt() != null;
            return savedProduct;
        });

        productService.saveProduct(productRequest);

        verify(productRepo).save(any(Product.class));
    }

    @Test
    void updateProduct() throws ProductNotFoundException {
        UUID productId = product1.getId();

        when(productRepo.findById(productId)).thenReturn(Optional.of(product1));

        productService.updateProduct(productId, productRequest);

        verify(productRepo).findById(productId);

        assertEquals(productRequest.getName(), product1.getName());
        assertEquals(productRequest.getArticle(), product1.getArticle());
        assertEquals(productRequest.getDescription(), product1.getDescription());
        assertEquals(productRequest.getCategory(), product1.getCategory());
        assertEquals(productRequest.getPrice(), product1.getPrice());
        assertEquals(productRequest.getQuantity(), product1.getQuantity());

        verify(productRepo).save(product1);
    }

    @Test
    void getAllProducts() {
        List<Product> productList = Arrays.asList(product1, product2);

        when(productRepo.findAll()).thenReturn(productList);

        List<ProductResponse> productResponses = productService.getAllProducts();

        verify(productRepo).findAll();

        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            ProductResponse productResponse = productResponses.get(i);
            assertEquals(product.getName(), productResponse.getName());
            assertEquals(product.getArticle(), productResponse.getArticle());
            assertEquals(product.getDescription(), productResponse.getDescription());
            assertEquals(product.getCategory(), productResponse.getCategory());
            assertEquals(product.getPrice(), productResponse.getPrice());
            assertEquals(product.getQuantity(), productResponse.getQuantity());
        }
    }

    @Test
    void getProductById() throws ProductNotFoundException {
        UUID productId = product1.getId();

        when(productRepo.findById(productId)).thenReturn(Optional.of(product1));

        ProductResponse productResponse = productService.getProductById(productId);

        verify(productRepo).findById(productId);

        assertEquals(product1.getName(), productResponse.getName());
        assertEquals(product1.getArticle(), productResponse.getArticle());
        assertEquals(product1.getDescription(), productResponse.getDescription());
        assertEquals(product1.getCategory(), productResponse.getCategory());
        assertEquals(product1.getPrice(), productResponse.getPrice());
        assertEquals(product1.getQuantity(), productResponse.getQuantity());
    }

    @Test
    void deleteProductByUuid() {
        UUID productId = product1.getId();

        when(productRepo.findById(productId)).thenReturn(Optional.of(product1));

        productService.deleteProductByUuid(productId);

        verify(productRepo).deleteById(productId);
    }
}
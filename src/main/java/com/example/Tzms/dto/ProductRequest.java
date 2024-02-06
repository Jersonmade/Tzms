package com.example.Tzms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Запрос товара")
public class ProductRequest {
    @Schema(description = "Название товара")
    private String name;
    @Schema(description = "Артикул товара")
    private String article;
    @Schema(description = "Описание товара")
    private String description;
    @Schema(description = "Категория товара")
    private String category;
    @Schema(description = "Цена товара")
    private BigDecimal price;
    @Schema(description = "Количество товара")
    private int quantity;
}

package com.example.Tzms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Модель запроса для создания или обновления товара
 */
@Data
@Schema(description = "Запрос товара")
public class ProductRequest {
    @Schema(description = "Название товара")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Schema(description = "Артикул товара")
    @NotBlank(message = "Article is mandatory")
    private String article;

    @Schema(description = "Описание товара")
    @NotNull(message = "Description is mandatory")
    private String description;

    @Schema(description = "Категория товара")
    @NotNull(message = "Category is mandatory")
    private String category;

    @Schema(description = "Цена товара")
    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    private Integer price;

    @Schema(description = "Количество товара")
    @NotNull(message = "Quantity is mandatory")
    @Min(value = 0, message = "Quantity must be positive or zero")
    private Integer quantity;
}

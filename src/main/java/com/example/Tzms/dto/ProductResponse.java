package com.example.Tzms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@Schema(description = "Ответ товара")
public class ProductResponse {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(description = "Название товара")
    private String name;

    @Schema(description = "Артикул товара")
    private String article;

    @Schema(description = "Описание товара")
    private String description;

    @Schema(description = "Категория товара")
    private String category;

    @Schema(description = "Цена товара")
    private Integer price;

    @Schema(description = "Количество товара")
    private Integer quantity;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Date lastQuantityChange;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Date createdAt;
}

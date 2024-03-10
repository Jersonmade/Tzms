package com.example.Tzms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * Модель товара системы
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "_product")
public class Product {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String article;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer quantity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_quantity_change")
    private Date lastQuantityChange;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;
}
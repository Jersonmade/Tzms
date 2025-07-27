package com.example.Tzms.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Настройка Swagger
 */
@OpenAPIDefinition(
        info = @Info(
                title = "CRUD Application",
                description = "Test CRUD Application", version = "1.0.0",
                contact = @Contact(
                        name = "Sergeev Dmitriy",
                        email = "dima.sergeev.0404@inbox.ru"
                )
        )
)
public class OpenApiConfig {
}

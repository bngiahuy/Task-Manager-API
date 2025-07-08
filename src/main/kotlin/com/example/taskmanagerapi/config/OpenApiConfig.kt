package com.example.taskmanagerapi.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
    info = Info(
        title = "Spring Boot Kotlin JWT API",
        version = "1.0.0",
        description = "API documentation for User Management with JWT Authentication",
        contact = Contact(name = "Your Name", email = "your.email@example.com"),
    ),
    servers = [
        Server(url = "http://localhost:8080", description = "Local Development Server")
    ]
)
@SecurityScheme(
    name = "bearerAuth", // Tên của security scheme, dùng trong @SecurityRequirement
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer",
    description = "JWT authentication using a Bearer token"
)
class OpenApiConfig {

}
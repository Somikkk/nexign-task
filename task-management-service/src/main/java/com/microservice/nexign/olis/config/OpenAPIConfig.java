package com.microservice.nexign.olis.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;

public class OpenAPIConfig {

    @Bean
    public OpenAPI openApiConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("Distributed Task Service API")
                        .version("v1.0.0")
                        .description("API для управления задачами и их асинхронной обработкой")
                ).addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("Localhost server"));
    }
}

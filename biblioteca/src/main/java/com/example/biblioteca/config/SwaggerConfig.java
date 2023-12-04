package com.example.biblioteca.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Biblioteca API")
                        .description("API exemplo de uso de Springboot REST API, ")
                        .version("1.0")

                ).externalDocs(
                        new ExternalDocumentation()
                                .description("Gregori Rodrigues Monteiro, Natan Bays, Pedro Sarmento, Fabricio Luis"));
    }
}

package com.api.library.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Swagger (OpenAPI) para documentar a API.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title("Library API")
                .description("Api para gerenciamento de emprestimos de livros para usuários")
                .version("1.0.0")
                .license(new License().name("Apache 2.0").url("https://springdoc.org")));
    }
}

package ru.oleg.configurator.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.ServletContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
@SecurityScheme(
    name = "bearerAuth",
    scheme = "bearer",
    bearerFormat = "JWT",
    type = SecuritySchemeType.HTTP,
    in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {

    private final String appVersion = "0.0.9";
    private final String appDescription = "";

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public OpenAPI openApi(final ServletContext servletContext) {
        return new OpenAPI()
            .info(new Info().title("API серверной части приложения Configurator")
                .version(appVersion)
                .description(appDescription))
            .servers(
                List.of(
                    new Server().url("http://localhost:8080")
                        .description("Local service")));
    }
}

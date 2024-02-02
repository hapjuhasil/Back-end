package com.hapjuhasil.server.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
@Configuration
public class SwaggerConfig {

    private final String devUrl;
    private final String prodUrl;

    public SwaggerConfig(
            @Value("${hapjuhasil.openapi.dev-url}") final String devUrl,
            @Value("${hapjuhasil.openapi.prod-url}") final String prodUrl
    ) {
        this.devUrl = devUrl;
        this.prodUrl = prodUrl;
    }
@Bean
public OpenAPI openAPI() {
    final Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.description("개발 환경 서버 URL");

    final Server prodServer = new Server();
    prodServer.setUrl(prodUrl);
    prodServer.description("운영 환경 서버 URL");

    SecurityScheme securityScheme = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
            .in(SecurityScheme.In.HEADER).name("Authorization");
    SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");
    final Info info = new Info()
            .title("합주하실 API")
            .version("v1.0.0")
            .description("합주하실 API");

    return new OpenAPI()
            .info(info)
            .servers(List.of(devServer, prodServer))
            .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
            .security(Arrays.asList(securityRequirement));
    }
}

package com.youland.marketing.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Default")
                        .description("common user center service")
                        .version("v1.0")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("youland user center api")
                        .url("https://youland.com"));
    }

    @Bean
    public GroupedOpenApi defaultApi(){
        return GroupedOpenApi.builder()
                .group("Default")
                .packagesToScan("com.youland.usercenter.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi operationApi() {
        return GroupedOpenApi.builder()
                .group("Operation")
                .packagesToScan("org.springframework.boot.actuate")
                .build();
    }
}

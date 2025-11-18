package com.challenge.wazejob.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Value("${spring.application.name}")
    public String appName;

    @Bean
    OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title(appName.toUpperCase())
                        .description("Persuit your dreams with WazeJob!")
                        .version("1.0.0")
                );
    }
}
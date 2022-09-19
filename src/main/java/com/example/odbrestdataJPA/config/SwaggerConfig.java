package com.example.odbrestdataJPA.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * Configurcion Swagger para la generacion de documentacion de la API REST
 *
 * HTML: http://localhost:8080/swagger-ui/
 * JSON: http://localhost:8080/v2/api-docs
 */
@Configuration
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiDetails())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiDetails(){
        return new ApiInfo("Spring Boot Books API REST",
                "Library Api rest docs",
                "1.0",
                "http://www.google.com",
                new Contact("Keffren","http://www.google.com","guerrero268@outlook.com"),
                "MIT",
                "http://www.google.com",
                Collections.emptyList());
    }

    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }

}

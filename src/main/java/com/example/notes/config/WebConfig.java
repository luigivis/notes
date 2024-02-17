package com.example.notes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static io.swagger.v3.oas.models.PathItem.HttpMethod.GET;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/v3/api-docs")
        .allowedMethods(GET.name())
        .allowedOrigins("http://localhost:63342");
  }
}

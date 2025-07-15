package com.petthegarden.petthegarden.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.path}pet/")
    String petPath;

    @Value("${file.path}board/")
    String boardPath;

    @Value("${file.path}products/")
    String productsPath;

    @Value("${file.path}reviews/")
    String reviewsPath;

    @Value("${file.path}upload/")
    private String uploadPath;

    @Value("${file.path}f1/")
    private String imagesPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/PTGUpload/products/**")

        registry.addResourceHandler("/PTGUpload/pet/**")
                .addResourceLocations("file:///" + petPath);
        registry.addResourceHandler("/PTGUpload/board/**")
                .addResourceLocations("file:///" + boardPath);
        registry.addResourceHandler("/PTGUpload/products/**")

                .addResourceLocations("file:///" + productsPath);
        registry.addResourceHandler("/PTGUpload/reviews/**")
                .addResourceLocations("file:///" + reviewsPath);
        registry.addResourceHandler("/PTGUpload/upload/**")
                .addResourceLocations("file:///" + uploadPath);
        registry.addResourceHandler("/PTGUpload/images/**")
                .addResourceLocations("file:///" + imagesPath);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/cart/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}

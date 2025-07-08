package com.petthegarden.petthegarden.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.path}products/")
    String productsPath;

    @Value("${file.path}reviews/")
    String reviewsPath;

    @Value("${file.path}upload/")
    private String uploadPath;

    // 기존 파일 업로드 리소스 매핑
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/PTGupload/products/**")
                .addResourceLocations("file:///" + productsPath);
        registry.addResourceHandler("/PTGupload/reviews/**")
                .addResourceLocations("file:///" + reviewsPath);
        registry.addResourceHandler("/PTGupload/upload/**")
                .addResourceLocations("file:///" + uploadPath);
    }

    // ★ 장바구니/결제 페이지 연동을 위한 CORS 설정 추가
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/cart/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}

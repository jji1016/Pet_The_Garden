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

    @Value("${file.path}diary/")
    String diaryPath;

    @Value("${file.path}board/")
    String boardPath;

    @Value("${file.path}show/")
    String showPath;

    @Value("${file.path}stray/")
    String strayPath;

    @Value("C:/PTGUpload/tmp/")
    String tmpPath;

    // 기존 파일 업로드 리소스 매핑
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/PTGUpload/pet/**")
                .addResourceLocations("file:///" + petPath);
        registry.addResourceHandler("/PTGUpload/diary/**")
                .addResourceLocations("file:///" + diaryPath);
        registry.addResourceHandler("/PTGUpload/board/**")
                .addResourceLocations("file:///" + boardPath);
        registry.addResourceHandler("/PTGUpload/show/**")
                .addResourceLocations("file:///" + showPath);
        registry.addResourceHandler("/PTGUpload/stray/**")
                .addResourceLocations("file:///" + strayPath);
        registry.addResourceHandler("/PTGUpload/tmp/**")
                .addResourceLocations("file:///" + tmpPath);
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

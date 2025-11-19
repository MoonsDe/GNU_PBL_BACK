package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 1. "모든" 주소(/api/**, /auth/** 등)에 대해
                // 2. 개발 중에는 "모든" 주소에서의 요청을 허용 (나중에 프론트 주소로 변경)
                .allowedOriginPatterns("*") 
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // 3. 허용할 HTTP 메소드
                .allowedHeaders("*") // 4. 모든 헤더 허용
                .allowCredentials(true) // 5. 인증 정보(쿠키, 토큰 등) 허용
                .maxAge(3600); 
    }
}
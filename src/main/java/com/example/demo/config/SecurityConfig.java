package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer; // ⭐️ 추가됨
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // ⭐️ 1. CORS 설정 활성화 (이게 없으면 'Load Failed' 뜸)
            // (WebConfig에서 설정한 내용을 시큐리티에도 적용합니다)
            .cors(Customizer.withDefaults()) 
            
            // 2. CSRF, HTTP Basic 비활성화
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)

            // 3. 권한 설정
            .authorizeHttpRequests(authorize -> authorize
                // 로그인/회원가입 누구나 가능
                .requestMatchers("/auth/**").permitAll()

                // 품목 조회 누구나 가능
                .requestMatchers(HttpMethod.GET, "/api/items/**").permitAll()
                
                // ⭐️ [핵심] AI 이미지 분석 요청도 누구나 가능하게 허용!
                .requestMatchers("/api/ai/**").permitAll()

                // 나머지는 로그인 필요
                .anyRequest().authenticated()
            );
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 
    }
}
package com.example.demo.config;

// (필요한 import문이 더 있을 수 있습니다)
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // ⭐️ [중요] HttpMethod import
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
            // 1. CSRF, HTTP Basic 인증 비활성화 (API 서버이므로)
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)

            // 2. HTTP 요청 권한 설정
            .authorizeHttpRequests(authorize -> authorize
                
                // ⭐️ [필수] /auth/** (로그인, 회원가입) API는 누구나 허용
                .requestMatchers("/auth/**").permitAll()

                // ⭐️ [이것을 추가!] /api/items/** 주소로 오는 "GET" 요청은 누구나 허용
                // (품목 검색, 품목 상세 조회)
                .requestMatchers(HttpMethod.GET, "/api/items/**").permitAll()
                
                // ⭐️ (나중에 만들 AI API도 허용한다면)
                // .requestMatchers(HttpMethod.POST, "/api/ai/classify-image").permitAll()

                // ⭐️ [필수] 그 외의 모든 요청은 "인증(로그인)"이 필요함
                // (예: POST, PUT, DELETE /api/items/**)
                .anyRequest().authenticated()
            );
        
        // (JWT를 사용한다면 여기에 토큰 필터 설정이 추가됩니다)

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // (이건 이미 있으시죠?)
        return new BCryptPasswordEncoder(); 
    }
}
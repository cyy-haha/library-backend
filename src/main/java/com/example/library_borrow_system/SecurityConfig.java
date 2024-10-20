package com.example.library_borrow_system;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 禁用 CSRF 保護
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/users/login").permitAll() // 允許未登入用戶訪問登入 API
                .requestMatchers("/api/users/register").permitAll()
                .requestMatchers("/api/borrow/borrow", "/api/borrow/return", "/api/borrow/books").permitAll() // 放行借書、還書和查詢書籍的 API
                .anyRequest().authenticated() // 其他請求需要身份驗證
            )
            .cors(Customizer.withDefaults()); // 啟用 CORS 支援

        return http.build();
    }
}






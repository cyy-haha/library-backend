package com.example.library_borrow_system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允許來自前端的跨域請求
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8081") // 使用你 Vue 應用的 URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允許的方法
                .allowedHeaders("*") // 允許所有請求頭
                .allowCredentials(true); // 允許攜帶認證（例如 cookies）
    }
}


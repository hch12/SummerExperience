//package com.example.back.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/login", "/register");
//    }
//
//    @Bean
//    public com.example.back.config.JWTInterceptor jwtInterceptor() {
//        return new com.example.back.config.JWTInterceptor();
//    }
//
//}
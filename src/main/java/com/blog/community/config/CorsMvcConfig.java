package com.blog.community.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsMvcConfig implements WebMvcConfigurer {
// CORS 설정
     @Override
     public void addCorsMappings(CorsRegistry registry) {
         registry.addMapping("/**")
                 .allowedOrigins("http://localhost:3000");
//                 .allowedMethods("GET", "POST", "PUT", "DELETE")
//                 .allowedHeaders("*")
//                 .allowCredentials(true)
//                 .exposedHeaders("Authorization");
     }

}

package com.example.Crawler.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 處理React靜態資源
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        
        // 處理React路由，將所有非API請求導向index.html
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(false);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 將根路徑導向React應用
        registry.addViewController("/").setViewName("forward:/static/index.html");
        
        // 處理React路由，將所有非API路徑導向React應用
        registry.addViewController("/{path:[^\\.]*}").setViewName("forward:/static/index.html");
    }
}

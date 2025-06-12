package com.bcu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 设置URL路径匹配策略
        configurer.setUseSuffixPatternMatch(false)
                 .setUseTrailingSlashMatch(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器，确保文件操作请求不被静态资源处理器处理
        registry.addInterceptor(new FileOperationInterceptor())
                .addPathPatterns("/api/files/**", "/files/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 设置静态资源处理器的优先级为最低
        registry.setOrder(Integer.MAX_VALUE);
    }
} 
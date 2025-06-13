package com.bcu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class FileConfig implements WebMvcConfigurer {

    @Value("${file.upload.signature-dir}")
    private String signatureDir;

    @PostConstruct
    public void init() {
        try {
            // 创建签名存储目录
            Path signaturePath = Paths.get(signatureDir);
            if (!Files.exists(signaturePath)) {
                Files.createDirectories(signaturePath);
            }
            System.out.println("签名存储目录已创建: " + signaturePath.toAbsolutePath());
        } catch (Exception e) {
            throw new RuntimeException("无法创建签名存储目录: " + e.getMessage(), e);
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置静态资源映射
        String location = "file:" + signatureDir + "/";
        System.out.println("配置静态资源映射: " + location);
        registry.addResourceHandler("/api/student/signature/**")
                .addResourceLocations(location);
    }
} 
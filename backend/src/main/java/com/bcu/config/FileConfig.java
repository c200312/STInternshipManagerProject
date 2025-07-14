package com.bcu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import jakarta.annotation.PostConstruct;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class FileConfig implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(FileConfig.class);

    @Value("${file.upload.signature-dir}")
    private String signatureDir;

    @PostConstruct
    public void init() {
        try {
            // 确保目录存在
            File dir = new File(signatureDir);
            if (!dir.exists()) {
                boolean created = dir.mkdirs();
                if (created) {
                    logger.info("成功创建签名目录: {}", signatureDir);
                } else {
                    logger.error("创建签名目录失败: {}", signatureDir);
                }
            } else {
                logger.info("签名目录已存在: {}", signatureDir);
            }
        } catch (Exception e) {
            logger.error("初始化签名目录时发生错误", e);
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        try {
            // 配置静态资源映射
            String uploadPath = "file:" + signatureDir + File.separator;
            registry.addResourceHandler("/uploads/signatures/**")
                    .addResourceLocations(uploadPath);
            logger.info("已配置静态资源映射: /uploads/signatures/** -> {}", uploadPath);
        } catch (Exception e) {
            logger.error("配置静态资源映射时发生错误", e);
        }
    }
}
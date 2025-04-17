package com.bcu.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class MongoConfig {
    @Bean
    public MongoClient mongoClient() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToSocketSettings(builder -> builder
                        .connectTimeout(5000, TimeUnit.MILLISECONDS) // 设置连接超时时间为5秒
                        .readTimeout(5000, TimeUnit.MILLISECONDS)) // 设置读取超时时间为5秒
                .applyToClusterSettings(builder -> builder
                        .serverSelectionTimeout(5000, TimeUnit.MILLISECONDS)) // 设置服务器选择超时时间为5秒
                .build();
        return MongoClients.create(settings);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "duser");
    }
}
package com.bcu.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@PropertySource("classpath:mongo.properties") // 别忘了加 classpath:
public class MongoConfig {

    @Value("${mongo.host}")
    private String host;

    @Value("${mongo.port}")
    private int port;

    @Value("${mongo.database}")
    private String database;

    @Bean
    public MongoClient mongoClient() {
        String connectionString = "mongodb://" + host + ":" + port;
        return MongoClients.create(connectionString);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), database);
    }
}

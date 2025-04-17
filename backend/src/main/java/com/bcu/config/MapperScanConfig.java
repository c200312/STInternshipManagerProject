package com.bcu.config;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.core.type.filter.TypeFilter;
import java.util.Collections;

@Configuration 
@AutoConfigureAfter(MybatisConfig.class)
public class MapperScanConfig {
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("com.bcu.user.dao");
        configurer.setAnnotationClass(org.springframework.stereotype.Repository.class);
        // 排除 DUserRepository 的扫描
        configurer.setExcludeFilters(Collections.singletonList(
            new org.springframework.core.type.filter.RegexPatternTypeFilter(
                java.util.regex.Pattern.compile(".*DUserRepository.*")
            )
        ));
        return configurer;
    }
}
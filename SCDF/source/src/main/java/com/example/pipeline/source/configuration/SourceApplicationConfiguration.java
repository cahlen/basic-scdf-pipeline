package com.example.pipeline.source.configuration;

import com.example.pipeline.source.configuration.properties.SourceApplicationConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class SourceApplicationConfiguration {
    @Bean
    public ClassPathResource classPathResource(SourceApplicationConfigurationProperties sourceApplicationConfigurationProperties) {
        return new ClassPathResource(sourceApplicationConfigurationProperties.getFileName());
    }
}

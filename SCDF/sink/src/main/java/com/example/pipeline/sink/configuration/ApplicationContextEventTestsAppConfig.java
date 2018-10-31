package com.example.pipeline.sink.configuration;

import com.example.pipeline.sink.configuration.properties.MongoConfigurationProperties;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import static java.util.Collections.singletonList;

@Profile("!test")
@Configuration
public class ApplicationContextEventTestsAppConfig extends AbstractMongoConfiguration {

    private MongoConfigurationProperties mongoConfigurationProperties;

    @Autowired
    public ApplicationContextEventTestsAppConfig(MongoConfigurationProperties mongoConfigurationProperties) {
        this.mongoConfigurationProperties = mongoConfigurationProperties;
    }

    @Override
    public String getDatabaseName() {
        return mongoConfigurationProperties.getDatabaseName();
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        return new MongoClient(
                singletonList(
                        new ServerAddress(
                                mongoConfigurationProperties.getHost(),
                                mongoConfigurationProperties.getPort()
                        )),
                singletonList(MongoCredential.createCredential(
                        mongoConfigurationProperties.getUserName(),
                        mongoConfigurationProperties.getDatabaseName(),
                        mongoConfigurationProperties.getPassword().toCharArray()
                )));
    }
}

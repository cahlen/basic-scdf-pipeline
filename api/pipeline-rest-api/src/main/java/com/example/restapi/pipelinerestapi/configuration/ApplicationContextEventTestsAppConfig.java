package com.example.restapi.pipelinerestapi.configuration;

import com.example.restapi.pipelinerestapi.configuration.properties.DatasourceConfigurationProperties;
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

    private DatasourceConfigurationProperties datasourceConfigurationProperties;

    @Autowired
    public ApplicationContextEventTestsAppConfig(DatasourceConfigurationProperties datasourceConfigurationProperties) {
        this.datasourceConfigurationProperties = datasourceConfigurationProperties;
    }

    @Override
    public String getDatabaseName() {
        return datasourceConfigurationProperties.getDatabaseName();
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        return new MongoClient(
                singletonList(
                        new ServerAddress(
                                datasourceConfigurationProperties.getHost(),
                                datasourceConfigurationProperties.getPort()
                        )),
                singletonList(MongoCredential.createCredential(
                        datasourceConfigurationProperties.getUserName(),
                        datasourceConfigurationProperties.getDatabaseName(),
                        datasourceConfigurationProperties.getPassword().toCharArray()
                )));
    }
}


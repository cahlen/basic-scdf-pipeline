package com.example.restapi.pipelinerestapi;

import com.mongodb.MongoClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PipelineRestApiApplicationTests {

    @MockBean
    private MongoClient mongoClient;

    @MockBean
    private MongoDbFactory mongoDbFactory;

    @Test
    public void contextLoads() {
    }

}

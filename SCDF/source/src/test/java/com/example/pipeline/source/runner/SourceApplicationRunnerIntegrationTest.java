package com.example.pipeline.source.runner;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.cloud.stream.test.matcher.MessageQueueMatcher;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.BlockingQueue;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SourceApplicationRunnerIntegrationTest {

    @Autowired
    private Source source;

    @Autowired
    private MessageCollector messageCollector;

    @Test
    public void handle_givenFile_outputsMessages() {
        BlockingQueue<Message<?>> payloads = messageCollector.forChannel(source.output());

        assertThat(payloads, MessageQueueMatcher.receivesPayloadThat(is("{\"address\":\"601 West Street_601 West 5th Avenue_Anchorage\",\"latitude\":-149.8935557,\"storeName\":\"Starbucks - AK - Anchorage  00001\",\"longitude\":61.21759217}")));
        assertThat(payloads, MessageQueueMatcher.receivesPayloadThat(is("{\"address\":\"Carrs-Anchorage #1805_1650 W Northern Lights Blvd_Anchorage\",\"latitude\":-149.9054948,\"storeName\":\"Starbucks - AK - Anchorage  00002\",\"longitude\":61.19533942}")));
    }
}
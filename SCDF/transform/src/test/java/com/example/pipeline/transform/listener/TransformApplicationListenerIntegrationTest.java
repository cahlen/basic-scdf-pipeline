package com.example.pipeline.transform.listener;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransformApplicationListenerIntegrationTest {

    private final String BAKED_INPUT = "{\"address\":\"601 West Street_601 West 5th Avenue_Anchorage\",\"latitude\":-149.8935557,\"storeName\":\"Starbucks - AK - Anchorage  00001\",\"longitude\":61.21759217}";

    @Autowired
    private Processor processor;

    @Autowired
    private MessageCollector messageCollector;

    @Test
    public void handle_givenJsonString_outputsUpperCaseValues() {
        processor.input().send(new GenericMessage<>(BAKED_INPUT));

        GenericMessage<String> result = (GenericMessage<String>) messageCollector.forChannel(processor.output()).poll();
        JSONObject resultJsonObject = createJsonObject(Objects.requireNonNull(result));

        assertThat(resultJsonObject.get("address")).isEqualTo("601 WEST STREET_601 WEST 5TH AVENUE_ANCHORAGE");
        assertThat(resultJsonObject.get("storeName")).isEqualTo("STARBUCKS - AK - ANCHORAGE  00001");
    }

    private JSONObject createJsonObject(GenericMessage<String> message) {
        return new JSONObject(message.getPayload());
    }
}

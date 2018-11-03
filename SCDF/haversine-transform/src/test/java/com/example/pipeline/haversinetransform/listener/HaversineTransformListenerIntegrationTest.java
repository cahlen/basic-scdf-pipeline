package com.example.pipeline.haversinetransform.listener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HaversineTransformListenerIntegrationTest {

    private final String VALID_PAYLOAD = "{\"address\":\"601 WEST STREET_601 WEST 5TH AVENUE_ANCHORAGE\",\"longitude\":-149.8935557,\"storeName\":\"STARBUCKS - AK - ANCHORAGE  00001\",\"latitude\":61.21759217}";
    private final String INVALID_PAYLOAD = "{\"address\":\"01 FREEWAY & UNION HILLS DRIVE_8251 W. UNION HILLS DRIVE_GLENDALE, ARIZONA 853080502_623-566-9202\",\"longitude\":-112.2374114,\"storeName\":\"STARBUCKS - AZ - GLENDALE [WD]  00156\",\"latitude\":33.65280991}";

    @Autowired
    private Processor processor;

    @Autowired
    private MessageCollector messageCollector;

    @Test
    public void handle_givenPayloadWithValidLatLong_returnsPayload() {
        processor.input().send(new GenericMessage<>(VALID_PAYLOAD));
        GenericMessage<String> result = (GenericMessage<String>) messageCollector.forChannel(processor.output()).poll();

        assertThat(result.getPayload()).isEqualTo(VALID_PAYLOAD);
    }

    @Test
    public void handle_givenPayloadWithInvalidLatLong_returnsNull() {
        processor.input().send(new GenericMessage<>(INVALID_PAYLOAD));
        GenericMessage<String> result = (GenericMessage<String>) messageCollector.forChannel(processor.output()).poll();

        assertThat(result).isNull();
    }
}

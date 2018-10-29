package com.example.pipeline.transform.listener;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransformApplicationListenerTest {
    private TransformApplicationListener subject;

    private final String BAKED_INPUT = "{\"address\":\"601 West Street_601 West 5th Avenue_Anchorage\",\"latitude\":-149.8935557,\"storeName\":\"Starbucks - AK - Anchorage  00001\",\"longitude\":61.21759217}";

    @Mock
    private Processor mockProcessor;

    @Mock
    private MessageChannel mockMessageChannel;

    @Captor
    private ArgumentCaptor<GenericMessage<String>> processorOutputCaptor;

    @Test
    public void handle_givenPayloadWithLowerCase_returnsPayloadWithUppercase() {
        given(mockProcessor.output()).willReturn(mockMessageChannel);

        GenericMessage<String> bakedMessage = new GenericMessage<>(BAKED_INPUT);
        subject = new TransformApplicationListener(mockProcessor);
        subject.handle(bakedMessage);

        verify(mockMessageChannel).send(processorOutputCaptor.capture());

        JSONObject resultJsonObject = new JSONObject(processorOutputCaptor.getValue().getPayload());
        assertThat(resultJsonObject.get("address")).isEqualTo("601 WEST STREET_601 WEST 5TH AVENUE_ANCHORAGE");
        assertThat(resultJsonObject.get("storeName")).isEqualTo("STARBUCKS - AK - ANCHORAGE  00001");
    }
}
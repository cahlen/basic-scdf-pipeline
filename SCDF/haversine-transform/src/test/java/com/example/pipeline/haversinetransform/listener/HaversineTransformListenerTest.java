package com.example.pipeline.haversinetransform.listener;

import com.example.pipeline.haversinetransform.service.HaversineFormulaService;
import org.junit.Before;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HaversineTransformListenerTest {
    public HaversineTransformListener subject;

    private final String VALID_PAYLOAD = "{\"address\":\"601 WEST STREET_601 WEST 5TH AVENUE_ANCHORAGE\",\"longitude\":-149.8935557,\"storeName\":\"STARBUCKS - AK - ANCHORAGE  00001\",\"latitude\":61.21759217}";
    private final String INVALID_PAYLOAD = "{\"address\":\"01 FREEWAY & UNION HILLS DRIVE_8251 W. UNION HILLS DRIVE_GLENDALE, ARIZONA 853080502_623-566-9202\",\"longitude\":-112.2374114,\"storeName\":\"STARBUCKS - AZ - GLENDALE [WD]  00156\",\"latitude\":33.65280991}";

    @Mock
    private Processor processor;

    @Mock
    private MessageChannel messageChannel;

    @Mock
    private HaversineFormulaService haversineFormulaService;

    @Captor
    private ArgumentCaptor<GenericMessage<String>> genericMessageArgumentCaptor;

    @Before
    public void setUp() {
        subject = new HaversineTransformListener(processor,haversineFormulaService);
    }

    @Test
    public void handle_givenValidPayload_returnPayload() {
        given(processor.output()).willReturn(messageChannel);
        given(haversineFormulaService.withinRadius(any(Double.class),any(Double.class))).willReturn(true);
        GenericMessage payload = new GenericMessage<>(VALID_PAYLOAD);
        subject.handle(payload);

        verify(messageChannel).send(genericMessageArgumentCaptor.capture());

        assertThat(genericMessageArgumentCaptor.getValue().getPayload()).isEqualTo(VALID_PAYLOAD);
    }

    @Test
    public void handle_givenInvlidPayload_returnNull() {
        given(haversineFormulaService.withinRadius(any(Double.class),any(Double.class))).willReturn(false);
        GenericMessage payload = new GenericMessage<>(INVALID_PAYLOAD);
        subject.handle(payload);

        verify(processor, never()).output();
        verify(messageChannel, never()).send(any());
    }
}
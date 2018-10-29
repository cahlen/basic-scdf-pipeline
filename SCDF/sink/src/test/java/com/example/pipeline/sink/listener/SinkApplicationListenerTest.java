package com.example.pipeline.sink.listener;

import com.example.pipeline.sink.dao.Starbucks;
import com.example.pipeline.sink.repository.SinkRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.messaging.support.GenericMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SinkApplicationListenerTest {
    private SinkApplicationListener subject;

    private final String BAKED_INPUT = "{\"address\":\"601 WEST STREET_601 WEST 5TH AVENUE_ANCHORAGE\",\"latitude\":-149.8935557,\"storeName\":\"STARBUCKS - AK - ANCHORAGE  00001\",\"longitude\":61.21759217}";

    @Mock
    private SinkRepository sinkRepository;

    @Captor
    private ArgumentCaptor<Starbucks> starbucksArgumentCaptor;

    @Test
    public void handle_givenDataFromTransform_savesDataToRepository() {
        GenericMessage<String> payload = new GenericMessage<>(BAKED_INPUT);
        subject = new SinkApplicationListener(sinkRepository);
        subject.handle(payload);

        verify(sinkRepository).save(starbucksArgumentCaptor.capture());

        assertThat(starbucksArgumentCaptor.getValue().getLongitude()).isEqualTo(61.21759217);
        assertThat(starbucksArgumentCaptor.getValue().getLatitude()).isEqualTo(-149.8935557);
        assertThat(starbucksArgumentCaptor.getValue().getStoreName()).isEqualTo("STARBUCKS - AK - ANCHORAGE  00001");
        assertThat(starbucksArgumentCaptor.getValue().getAddress()).isEqualTo("601 WEST STREET_601 WEST 5TH AVENUE_ANCHORAGE");
    }
}

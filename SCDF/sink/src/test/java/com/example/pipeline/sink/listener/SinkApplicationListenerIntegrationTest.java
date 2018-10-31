package com.example.pipeline.sink.listener;

import com.example.pipeline.sink.dao.Starbucks;
import com.example.pipeline.sink.repository.SinkRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SinkApplicationListenerIntegrationTest {

    private final String BAKED_INPUT = "{\"address\":\"601 WEST STREET_601 WEST 5TH AVENUE_ANCHORAGE\",\"latitude\":-149.8935557,\"storeName\":\"STARBUCKS - AK - ANCHORAGE  00001\",\"longitude\":61.21759217}";

    @Autowired
    private Sink sink;

    @Autowired
    private SinkRepository sinkRepository;

    @Test
    public void handle_givenJsonPayloadInput_savesStarbucksDataInRepository() {
        sink.input().send(new GenericMessage<>(BAKED_INPUT));

        List<Starbucks> allResults = sinkRepository.findAll();
        Starbucks starbucksResult = allResults.get(0);

        assertThat(starbucksResult.getLongitude()).isEqualTo(61.21759217);
        assertThat(starbucksResult.getLatitude()).isEqualTo(-149.8935557);
        assertThat(starbucksResult.getStoreName()).isEqualTo("STARBUCKS - AK - ANCHORAGE  00001");
        assertThat(starbucksResult.getAddress()).isEqualTo("601 WEST STREET_601 WEST 5TH AVENUE_ANCHORAGE");
    }
}
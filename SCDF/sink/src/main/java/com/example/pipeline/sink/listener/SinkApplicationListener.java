package com.example.pipeline.sink.listener;

import com.example.pipeline.sink.dao.Starbucks;
import com.example.pipeline.sink.repository.SinkRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Sink.class)
public class SinkApplicationListener {

    private SinkRepository sinkRepository;

    @Autowired
    public SinkApplicationListener(SinkRepository sinkRepository) {
        this.sinkRepository = sinkRepository;
    }

    @StreamListener(Sink.INPUT)
    public void handle(GenericMessage<String> payload) {
        JSONObject jsonPayload = new JSONObject(payload.getPayload());
        Starbucks starbucks = new Starbucks(
                (Double) jsonPayload.get("latitude"),
                (Double) jsonPayload.get("longitude"),
                (String) jsonPayload.get("storeName"),
                (String) jsonPayload.get("address")
        );
        sinkRepository.save(starbucks);
    }
}

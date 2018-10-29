package com.example.pipeline.transform.listener;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Processor.class)
public class TransformApplicationListener {

    private Processor processor;

    @Autowired
    public TransformApplicationListener(Processor processor) {
        this.processor = processor;
    }

    @StreamListener(Processor.INPUT)
    public void handle(GenericMessage<String> message) {
        JSONObject jsonPayload = new JSONObject(message.getPayload());
        jsonPayload.put("address", upperCase((String) jsonPayload.get("address")));
        jsonPayload.put("storeName", upperCase((String) jsonPayload.get("storeName")));

        processor.output().send(new GenericMessage<>(jsonPayload.toString()));
    }

    private String upperCase(String value) {
        return value.toUpperCase();
    }
}

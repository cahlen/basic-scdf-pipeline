package com.example.pipeline.haversinetransform.listener;

import com.example.pipeline.haversinetransform.service.HaversineFormulaService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Processor.class)
public class HaversineTransformListener {

    private Processor processor;
    private HaversineFormulaService haversineFormulaService;

    @Autowired
    public HaversineTransformListener(Processor processor,
                                      HaversineFormulaService haversineFormulaService) {
        this.processor = processor;
        this.haversineFormulaService = haversineFormulaService;
    }

    @StreamListener(Processor.INPUT)
    public void handle(GenericMessage<String> payload) {
        JSONObject jsonPayload = new JSONObject(payload.getPayload());
        double lat = (double) jsonPayload.get("latitude");
        double lon = (double) jsonPayload.get("longitude");

        if(haversineFormulaService.withinRadius(lat,lon)) {
            processor.output().send(payload);
        }
    }
}

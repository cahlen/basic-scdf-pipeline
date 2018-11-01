package com.example.pipeline.source.runner;

import com.example.pipeline.source.service.FileBufferedReaderService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;

@Component
@EnableBinding(Source.class)
public class SourceApplicationRunner {

    private FileBufferedReaderService fileBufferedReaderService;
    private Source source;

    @Autowired
    public SourceApplicationRunner(FileBufferedReaderService fileBufferedReaderService,
                                   Source source) {
        this.fileBufferedReaderService = fileBufferedReaderService;
        this.source = source;
    }

    @InboundChannelAdapter(channel = Source.OUTPUT)
    public String handle() throws IOException {
        BufferedReader br = fileBufferedReaderService.readFile();
        String line;
        while((line = br.readLine()) != null) {
            String[] values = line.replace("\"","").split(",");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("latitude", Double.parseDouble(values[1]));
                jsonObject.put("longitude",Double.parseDouble(values[0]));
                jsonObject.put("storeName", values[2]);
                jsonObject.put("address", values[3]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            source.output().send(new GenericMessage<>(jsonObject.toString()));
        }
        return null;
    }
}

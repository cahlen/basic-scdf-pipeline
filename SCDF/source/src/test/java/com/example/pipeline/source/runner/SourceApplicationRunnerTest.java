package com.example.pipeline.source.runner;

import com.example.pipeline.source.service.FileBufferedReaderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SourceApplicationRunnerTest {
    private SourceApplicationRunner subject;

    private final String TEST_CSV = "-149.8935557,61.21759217,Starbucks - AK - Anchorage  00001,\"601 West Street_601 West 5th Avenue_Anchorage, Alaska 99501_907-277-2477\"";
    private final String EXPECTED_RESULT = "{\"address\":\"601 West Street_601 West 5th Avenue_Anchorage\",\"latitude\":61.21759217,\"storeName\":\"Starbucks - AK - Anchorage  00001\",\"longitude\":-149.8935557}";

    @Mock
    private Source mockSource;

    @Mock
    private FileBufferedReaderService mockFileBufferedReaderService;

    @Mock
    private MessageChannel mockMessageChannel;

    @Captor
    private ArgumentCaptor<GenericMessage<String>> argumentCaptor;

    @Test
    public void handle_createsPayloads() throws IOException {
        InputStream is = new ByteArrayInputStream(TEST_CSV.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        given(mockFileBufferedReaderService.readFile()).willReturn(br);
        given(mockSource.output()).willReturn(mockMessageChannel);
        given(mockMessageChannel.send(any(GenericMessage.class))).willReturn(true);

        subject = new SourceApplicationRunner(mockFileBufferedReaderService, mockSource);
        String result = subject.handle();

        verify(mockSource.output()).send(argumentCaptor.capture());

        assertThat(argumentCaptor.getAllValues().get(0).getPayload()).isEqualTo(EXPECTED_RESULT);
        assertThat(result).isNull();
    }
}
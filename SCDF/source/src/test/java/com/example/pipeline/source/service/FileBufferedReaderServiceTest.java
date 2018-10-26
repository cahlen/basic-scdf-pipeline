package com.example.pipeline.source.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class FileBufferedReaderServiceTest {
    private FileBufferedReaderService subject;

    @Mock
    private ClassPathResource mockClassPathResource;

    @Test
    public void readFile_givenFileClassPathResource_returnsBufferedReader() throws IOException {
        InputStream inputStream = new ByteArrayInputStream("foobarinputstream".getBytes());

        given(mockClassPathResource.getInputStream()).willReturn(inputStream);

        subject = new FileBufferedReaderService(mockClassPathResource);
        BufferedReader result = subject.readFile();

        assertThat(result.readLine()).isEqualTo("foobarinputstream");
    }
}
package com.example.pipeline.source.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class FileBufferedReaderService {

    private ClassPathResource classPathResource;

    @Autowired
    public FileBufferedReaderService(ClassPathResource classPathResource) {
        this.classPathResource = classPathResource;
    }

    public BufferedReader readFile() {
        try {
            InputStream inputStream = classPathResource.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            return new BufferedReader(inputStreamReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

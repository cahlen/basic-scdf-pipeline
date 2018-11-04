package com.example.restapi.pipelinerestapi.services;

import com.example.restapi.pipelinerestapi.dao.Starbucks;

import java.util.List;

public interface PipelineRepositoryService {
    List<Starbucks> getListByState(String state);
}

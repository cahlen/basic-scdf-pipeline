package com.example.restapi.pipelinerestapi.controller;

import com.example.restapi.pipelinerestapi.dao.Starbucks;
import com.example.restapi.pipelinerestapi.services.PipelineRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PipelineController {
    private final PipelineRepositoryService pipelineRepositoryService;

    @Autowired
    public PipelineController(PipelineRepositoryService pipelineRepositoryService) {
        this.pipelineRepositoryService = pipelineRepositoryService;
    }

    @GetMapping("/state/{stateAbbreviation}")
    public List<Starbucks> retrieveStarbucksByState(@PathVariable String stateAbbreviation) {
        return pipelineRepositoryService.getListByState(stateAbbreviation);
    }
}

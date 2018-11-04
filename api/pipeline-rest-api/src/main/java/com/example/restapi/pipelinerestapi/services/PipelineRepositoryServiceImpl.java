package com.example.restapi.pipelinerestapi.services;

import com.example.restapi.pipelinerestapi.dao.Starbucks;
import com.example.restapi.pipelinerestapi.repository.PipelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PipelineRepositoryServiceImpl implements PipelineRepositoryService {

    private PipelineRepository pipelineRepository;

    @Autowired
    public PipelineRepositoryServiceImpl(PipelineRepository pipelineRepository) {
        this.pipelineRepository = pipelineRepository;
    }

    @Override
    public List<Starbucks> getListByState(String state) {
        if(state == null || state.length() > 2 || state.length() == 1)
            throw new IllegalArgumentException("State much be nonnull and two characters long.");

        return pipelineRepository.findStarbucksByStoreNameContains("- " + state + " -");
    }
}

package com.example.restapi.pipelinerestapi.repository;

import com.example.restapi.pipelinerestapi.dao.Starbucks;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PipelineRepository extends MongoRepository<Starbucks, Long> {
    List<Starbucks> findStarbucksByStoreNameContains(String state);
}

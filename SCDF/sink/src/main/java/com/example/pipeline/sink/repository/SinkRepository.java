package com.example.pipeline.sink.repository;

import com.example.pipeline.sink.dao.Starbucks;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SinkRepository extends MongoRepository<Starbucks, Long> {
}

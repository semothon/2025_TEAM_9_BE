package com.trithon.trithon.repository;

import com.trithon.trithon.domain.Interview;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InterviewRepository extends MongoRepository<Interview, String> {
    List<Interview> findByUserId(String userId);
}

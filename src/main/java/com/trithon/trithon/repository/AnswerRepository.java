package com.trithon.trithon.repository;

import com.trithon.trithon.domain.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnswerRepository extends MongoRepository<Answer, String> {
}
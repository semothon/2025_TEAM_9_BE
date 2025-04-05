package com.trithon.trithon.repository;

import com.trithon.trithon.domain.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends MongoRepository<Answer, String> {
    List<Answer> findByUserId(String userId);
    Optional<Answer> findByUserIdAndMissionId(String userId, String missionId);
}
package com.trithon.trithon.repository;

import com.trithon.trithon.domain.MissionCompletion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MissionCompletionRepository extends MongoRepository<MissionCompletion, String> {
    Optional<MissionCompletion> findByUserIdAndInterviewIdAndMissionIdAndDate(String userId, String interviewId, String missionId, LocalDate date);
    List<MissionCompletion> findByUserIdAndInterviewId(String userId, String interviewId);
}
package com.trithon.trithon.repository;

import com.trithon.trithon.domain.MissionCompletion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MissionCompletionRepository extends MongoRepository<MissionCompletion, String> {
    Optional<MissionCompletion> findByUserIdAndMissionIdAndDate(String userId, String missionId, LocalDate date);
    List<MissionCompletion> findByUserId(String userId);
}
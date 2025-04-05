package com.trithon.trithon.repository;

import com.trithon.trithon.domain.DailyMission;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyMissionRepository extends MongoRepository<DailyMission, String> {
    Optional<DailyMission> findByUserIdAndInterviewIdAndDate(String userId, String interviewId, LocalDate date);
}
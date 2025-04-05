package com.trithon.trithon.repository;


import com.trithon.trithon.domain.DailyScore;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyScoreRepository extends MongoRepository<DailyScore, String> {
    Optional<DailyScore> findByUserIdAndDate(String userId, LocalDate date);
    List<DailyScore> findByUserIdInAndDate(List<String> userIds, LocalDate date);
}
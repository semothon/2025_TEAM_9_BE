package com.trithon.trithon.repository;

import com.trithon.trithon.domain.ENUM.MissionType;
import com.trithon.trithon.domain.Mission;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MissionRepository extends MongoRepository<Mission, String> {
    Optional<Mission> findByStageAndIndex(int stage, int index);
    List<Mission> findByType(MissionType type);
}
package com.trithon.trithon.repository;

import com.trithon.trithon.domain.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends MongoRepository<Group, String> {
    Optional<Group> findByJoinCode(String joinCode);
    List<Group> findByMemberIdsContaining(String userId);
}
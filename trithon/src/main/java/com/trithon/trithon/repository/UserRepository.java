package com.trithon.trithon.repository;

import com.trithon.trithon.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

// User에 매칭되는 String은 MongoDB _id field의 type
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}

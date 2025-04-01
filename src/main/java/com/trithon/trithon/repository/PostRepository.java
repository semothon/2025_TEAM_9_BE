package com.trithon.trithon.repository;

import com.trithon.trithon.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

// User에 매칭되는 String은 MongoDB _id field의 type
public interface PostRepository extends MongoRepository<Post, String> {
}

package com.trithon.trithon.repository;


import com.trithon.trithon.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

// User에 매칭되는 String은 MongoDB _id field의 type
public interface PostRepository extends MongoRepository<Post, String> {
    Page<Post> findByTagsContaining(String tag, Pageable pageable);
    @Query("{'$or': [ " +
            "{'title': {$regex: ?0, $options: 'i'}}, " +
            "{'content': {$regex: ?0, $options: 'i'}} ]}")
    Page<Post> findByTitleOrContentIgnoreCase(String keyword, Pageable pageable);
    Optional<Post> findById(String id);
}

package com.trithon.trithon.repository;

import com.trithon.trithon.domain.ENUM.InterviewCategory;
import com.trithon.trithon.domain.TrendQuiz;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TrendQuizRepository extends MongoRepository<TrendQuiz, String> {
    List<TrendQuiz> findByCategory(InterviewCategory category);
}

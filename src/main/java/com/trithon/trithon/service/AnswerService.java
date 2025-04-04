package com.trithon.trithon.service;

import com.trithon.trithon.domain.Answer;
import com.trithon.trithon.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public void submitAnswer(String userId, String missionId, String content) {
        Answer answer = new Answer(userId, missionId, content, LocalDate.now());
        answerRepository.save(answer);
    }
}
package com.trithon.trithon.service;

import com.trithon.trithon.domain.Answer;
import com.trithon.trithon.domain.Mission;
import com.trithon.trithon.domain.dto.response.AnswerWithQuestionDto;
import com.trithon.trithon.repository.AnswerRepository;
import com.trithon.trithon.repository.MissionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final MissionRepository missionRepository;

    public AnswerService(AnswerRepository answerRepository, MissionRepository missionRepository) {
        this.answerRepository = answerRepository;
        this.missionRepository = missionRepository;
    }

    public List<AnswerWithQuestionDto> getUserAnswersWithQuestions(String userId) {
        List<Answer> answers = answerRepository.findByUserId(userId);

        return answers.stream()
                .map(answer -> {
                    String missionId = answer.getMissionId();
                    String[] parts = missionId.split("-");
                    int stage = Integer.parseInt(parts[0]);
                    int index = Integer.parseInt(parts[1]);

                    Mission mission = missionRepository.findByStageAndIndex(stage, index)
                            .orElseThrow(() -> new RuntimeException("Mission not found"));
                    return new AnswerWithQuestionDto(
                            mission.getQuestion(),
                            answer.getContent()
                    );
                })
                .toList();
    }

    public void submitAnswer(String userId, String missionId, String content) {
        Optional<Answer> existingAnswer = answerRepository.findByUserIdAndMissionId(userId, missionId);
        if (existingAnswer.isPresent()) {
            throw new RuntimeException("Answer already exists");
        }

        Answer answer = new Answer(userId, missionId, content, LocalDate.now());
        answerRepository.save(answer);
    }

    public void updateAnswer(String userId, String missionId, String newContent) {
        Answer answer = answerRepository.findByUserIdAndMissionId(userId, missionId)
                .orElseThrow(() -> new RuntimeException("Answer not found"));

        answer.setContent(newContent);
        answerRepository.save(answer);
    }

    public void deleteAnswer(String userId, String missionId) {
        Answer answer = answerRepository.findByUserIdAndMissionId(userId, missionId)
                .orElseThrow(() -> new RuntimeException("Answer not found"));

        answerRepository.delete(answer);
    }

}
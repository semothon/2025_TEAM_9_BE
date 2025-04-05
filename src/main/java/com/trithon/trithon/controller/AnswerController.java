package com.trithon.trithon.controller;

import com.trithon.trithon.domain.dto.response.AnswerWithQuestionDto;
import com.trithon.trithon.service.AnswerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("")
    public List<AnswerWithQuestionDto> getAnswersWithQuestions(@RequestParam String userId) {
        return answerService.getUserAnswersWithQuestions(userId);
    }

    @PostMapping("/submit")
    public String submitAnswer(@RequestParam String userId,
                               @RequestParam String missionId,
                               @RequestParam String content) {
        answerService.submitAnswer(userId, missionId, content);
        return "Answer submitted successfully.";
    }

    @PutMapping("/update")
    public void updateAnswer(@RequestParam String userId,
                             @RequestParam String missionId,
                             @RequestParam String newContent) {
        answerService.updateAnswer(userId, missionId, newContent);
    }

    @DeleteMapping("/delete")
    public void deleteAnswer(@RequestParam String userId,
                             @RequestParam String missionId) {
        answerService.deleteAnswer(userId, missionId);
    }
}
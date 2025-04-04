package com.trithon.trithon.controller;

import com.trithon.trithon.service.AnswerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    // 답변 제출
    @PostMapping("/submit")
    public String submitAnswer(@RequestParam String userId,
                               @RequestParam String missionId,
                               @RequestParam String content) {
        answerService.submitAnswer(userId, missionId, content);
        return "Answer submitted successfully.";
    }
}
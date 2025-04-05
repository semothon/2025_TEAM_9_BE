package com.trithon.trithon.domain.dto.response;

import lombok.Getter;

@Getter
public class AnswerWithQuestionDto {
    private String question;
    private String answer;

    public AnswerWithQuestionDto(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
}

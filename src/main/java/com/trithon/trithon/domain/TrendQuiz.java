package com.trithon.trithon.domain;


import com.trithon.trithon.domain.ENUM.InterviewCategory;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Document(collection = "Trend_quizzes")
public class TrendQuiz {
    @Id
    private String id;

    private InterviewCategory category;
    private String question;
    private List<String> options;
    private int correctIndex;
    private boolean isOX;

    public TrendQuiz(InterviewCategory category, String question, List<String> options, int correctIndex, boolean isOX) {
        this.category = category;
        this.question = question;
        this.options = options;
        this.correctIndex = correctIndex;
        this.isOX = isOX;
    }
}

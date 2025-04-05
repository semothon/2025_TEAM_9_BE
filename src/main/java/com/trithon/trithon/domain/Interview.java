package com.trithon.trithon.domain;

import com.trithon.trithon.domain.ENUM.InterviewCategory;
import com.trithon.trithon.domain.ENUM.QuestDifficulty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@Document(collection = "Interviews")
public class Interview {
    @Id
    private String id;

    private String userId;
    private String name;
    private InterviewCategory category;
    private List<String> tags;

    private LocalDate date;
    private LocalTime time;

    private QuestDifficulty questDifficulty;
    private boolean includeTrendQuiz;

    public Interview(String userId,
                     String name,
                     InterviewCategory category,
                     List<String> tags,
                     LocalDate date,
                     LocalTime time,
                     QuestDifficulty questDifficulty,
                     boolean includeTrendQuiz) {
        this.userId = userId;
        this.name = name;
        this.category = category;
        this.tags = tags;
        this.date = date;
        this.time = time;
        this.questDifficulty = questDifficulty;
        this.includeTrendQuiz = includeTrendQuiz;
    }
}

package com.trithon.trithon.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Setter
@Getter
@Document(collection = "Daily_Scores")
public class DailyScore {
    @Id
    private String id;

    private String userId;
    private LocalDate date;
    private int score;

    public DailyScore(String userId, LocalDate date) {
        this.userId = userId;
        this.date = date;
        this.score = 0;
    }
}

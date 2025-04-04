package com.trithon.trithon.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Document(collection = "Answers")
public class Answer {
    @Id
    private String id;

    private String userId;
    private String missionId;
    private String content;
    private LocalDate createdAt;

    public Answer(String userId, String missionId, String content, LocalDate createdAt) {
        this.userId = userId;
        this.missionId = missionId;
        this.content = content;
        this.createdAt = createdAt;
    }
}

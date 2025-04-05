package com.trithon.trithon.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "Mission_Completions")
@Getter
@Setter
public class MissionCompletion {
    @Id
    private String id;

    private String userId;
    private String interviewId;
    private String missionId;
    private boolean completed;
    private LocalDate date;

    public MissionCompletion(String userId, String interviewId, String missionId, boolean completed, LocalDate date) {
        this.userId = userId;
        this.interviewId = interviewId;
        this.missionId = missionId;
        this.completed = completed;
        this.date = date;
    }
}

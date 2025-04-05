package com.trithon.trithon.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Document(collection = "Daily_missions")
public class DailyMission {
    @Id
    private String id;

    private String userId;
    private String interviewId;
    private LocalDate date;

    private String oddMissionId;
    private String evenMissionId;

    public DailyMission(String userId, String interviewId, LocalDate date, String oddMissionId, String evenMissionId) {
        this.userId = userId;
        this.interviewId = interviewId;
        this.date = date;
        this.oddMissionId = oddMissionId;
        this.evenMissionId = evenMissionId;
    }
}

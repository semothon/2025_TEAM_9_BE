package com.trithon.trithon.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter // 없으면 Jackson에서 json 변환할 때 반환 불가
@Setter
@Document(collection = "Attendances")
public class Attendance {
    @Id
    private String id;

    private String userId;
    private String interviewId;
    private LocalDate date;

    private boolean oddMissionCompleted;
    private boolean evenMissionCompleted;

    public Attendance(String userId, String interviewId, LocalDate date) {
        this.userId = userId;
        this.interviewId = interviewId;
        this.date = date;
        this.oddMissionCompleted = false;
        this.evenMissionCompleted = false;
    }

    public boolean isAttendanceSatisfied() {
        return oddMissionCompleted && evenMissionCompleted;
    }
}

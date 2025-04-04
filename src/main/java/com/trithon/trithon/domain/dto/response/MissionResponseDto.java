package com.trithon.trithon.domain.dto.response;

import com.trithon.trithon.domain.ENUM.MissionType;
import com.trithon.trithon.domain.Mission;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MissionResponseDto {

    private Mission nextOddMission;
    private Mission nextEvenMission;
    private String randomAnsweredOddQuestion;

    public MissionResponseDto(Mission nextOddMission, Mission nextEvenMission, String randomAnsweredOddQuestion) {
        this.nextOddMission = nextOddMission;
        this.nextEvenMission = nextEvenMission;
        this.randomAnsweredOddQuestion = randomAnsweredOddQuestion;
    }
}

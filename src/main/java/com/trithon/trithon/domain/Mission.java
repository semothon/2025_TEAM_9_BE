package com.trithon.trithon.domain;

import com.trithon.trithon.domain.ENUM.MissionType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Missions")
@Getter
@Setter
public class Mission {
    @Id
    private String id;

    private int stage;      // 스테이지 (1부터 시작)
    private int index;      // 스테이지 내 인덱스 (1~8)

    private String question; // 홀수 미션만 보유
    private MissionType type;

    public Mission(int stage, int index, String question, MissionType type) {
        this.stage = stage;
        this.index = index;
        this.question = question;
        this.type = type;
    }
}
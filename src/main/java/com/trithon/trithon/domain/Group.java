package com.trithon.trithon.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "Groups")
public class Group {
    @Id
    private String id;
    private String authorId;
    private String name;
    private String joinCode;
    private List<String> memberIds;
    private List<String> tags;
    private String imageUrl;
    private int score;
    private int level;

    public Group(String authorId, String name, String joinCode, List<String> tags, String imageUrl) {
        this.authorId = authorId;
        this.name = name;
        this.joinCode = joinCode;
        this.memberIds = new ArrayList<>(); // 빈 리스트로 초기화
        this.memberIds.add(authorId); // userId 추가
        this.tags = tags;
        this.imageUrl = imageUrl;
        this.score = 0;
        this.level = 0;
    }
}

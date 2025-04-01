package com.trithon.trithon.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "Posts")
@Getter
@Setter
public class Post {
    @Id
    private String id;
    private String userId;
    private String title;
    private String content;
    private List<String> tags;
    private String imageUrl; // 추가
    private LocalDateTime createdAt;
    private int views;

    public Post(String userId, String title, String content, List<String> tags, String imageUrl) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.imageUrl = imageUrl;
        this.createdAt = LocalDateTime.now();
        this.views = 0;
    }
}
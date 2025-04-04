package com.trithon.trithon.domain.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class PostRequestDto {
    private String userId;
    private String title;
    private String content;
    private List<String> tags;
    private MultipartFile image;
}

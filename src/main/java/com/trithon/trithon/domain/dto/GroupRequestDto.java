package com.trithon.trithon.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class GroupRequestDto {
    private String authorId;
    private String name;
    private List<String> memberIds;
    private List<String> tags;
    private MultipartFile image;
}
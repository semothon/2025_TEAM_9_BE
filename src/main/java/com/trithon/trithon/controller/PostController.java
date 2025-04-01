package com.trithon.trithon.controller;

import com.trithon.trithon.domain.dto.PostRequestDto;
import com.trithon.trithon.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("")
    public ResponseEntity<Void> createPost(PostRequestDto dto) throws IOException {
        postService.createPost(dto);

        return new ResponseEntity(HttpStatus.OK);
    }
}

package com.trithon.trithon.service;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.trithon.trithon.domain.Post;
import com.trithon.trithon.domain.dto.PostRequestDto;
import com.trithon.trithon.repository.PostRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final Storage storage;  // GCS object

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    public PostService(PostRepository postRepository, Storage storage) {
        this.postRepository = postRepository;
        this.storage = storage;
    }

    public void createPost(PostRequestDto dto) {
        String uuid = UUID.randomUUID().toString(); // Google Cloud Storage에 저장될 파일 이름
        String ext = dto.getImage().getContentType(); // 파일의 형식 ex) JPG
        String imageUrl = null;

        // GCP에 이미지 업로드 (확장자 없이 저장)
        try {
            storage.create(
                    BlobInfo.newBuilder(bucketName, uuid)
                            .setContentType(ext) // MIME 타입 설정
                            .build(),
                    dto.getImage().getInputStream()
            );

            imageUrl = String.format("https://storage.googleapis.com/%s/%s", bucketName, uuid);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload image", e);
        }

        // 게시글 저장
        Post post = new Post(dto.getUserId(), dto.getTitle(), dto.getContent(), dto.getTags(), imageUrl);
        postRepository.save(post); // MongoDB 저장
    }
}

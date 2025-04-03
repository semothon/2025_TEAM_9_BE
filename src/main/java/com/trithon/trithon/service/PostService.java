package com.trithon.trithon.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.trithon.trithon.domain.Post;
import com.trithon.trithon.domain.dto.PostRequestDto;
import com.trithon.trithon.repository.PostRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
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
        String imageUrl = null;

        // GCP에 이미지 업로드 (확장자 없이 저장)
        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            String ext = dto.getImage().getContentType();
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
        }

        // 게시글 저장
        Post post = new Post(dto.getUserId(), dto.getTitle(), dto.getContent(), dto.getTags(), imageUrl);
        postRepository.save(post); // MongoDB 저장
    }

    public List<Post> getPosts(String sort, String tag, int page, int size) {
        Pageable pageable;

        if ("popular".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("views")));
        } else {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        }

        Page<Post> postPage;
        if (tag != null) {
            postPage = postRepository.findByTagsContaining(tag, pageable);
        } else {
            postPage = postRepository.findAll(pageable);
        }

        return postPage.getContent();
    }

    // 게시글 검색 기능
    public List<Post> searchPosts(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        Page<Post> postPage = postRepository.findByTitleOrContentIgnoreCase(query, pageable);
        return postPage.getContent();
    }

    // 특정 게시글 조회 (조회수 증가)
    public Post getPostById(String postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setViews(post.getViews() + 1);  // 조회수 증가
            postRepository.save(post);  // 업데이트
            return post;
        } else {
            throw new RuntimeException("Post not found");
        }
    }

    public void deletePost(String id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            // GCS에서 이미지 삭제
            String imageUrl = post.get().getImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                storage.delete(BlobId.of(bucketName, fileName));
            }

            postRepository.deleteById(id);
        } else {
            throw new RuntimeException("Post not found");
        }
    }

    public Post updatePost(String id, Post updatedPost) {
        Optional<Post> existingPost = postRepository.findById(id);
        if (existingPost.isPresent()) {
            Post post = existingPost.get();
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
            post.setTags(updatedPost.getTags());
            post.setImageUrl(updatedPost.getImageUrl());
            return postRepository.save(post);
        } else {
            throw new RuntimeException("Post not found");
        }
    }
}

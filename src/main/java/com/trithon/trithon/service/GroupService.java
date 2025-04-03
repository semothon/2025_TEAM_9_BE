package com.trithon.trithon.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.trithon.trithon.domain.Group;
import com.trithon.trithon.domain.dto.GroupRequestDto;
import com.trithon.trithon.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final Storage storage;  // GCS object

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    public GroupService(GroupRepository groupRepository, Storage storage) {
        this.groupRepository = groupRepository;
        this.storage = storage;
    }

    public Group createGroup(GroupRequestDto dto) {
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
        Group group = new Group(dto.getAuthorId(), dto.getName(), generateJoinCode(), dto.getTags(), imageUrl);
        groupRepository.save(group); // MongoDB 저장
        return group;
    }

    private String generateJoinCode() {
        String code;
        do {
            code = String.valueOf(new Random().nextInt(9000) + 1000);  // 1000~9999 범위
        } while (groupRepository.findByJoinCode(code).isPresent());
        return code;
    }

    public Group getGroupById(String groupId) {
        return groupRepository.findById(groupId).get();
    }

    public List<Group> getGroups() {
        return groupRepository.findAll();
    }


    public void joinGroup(String joinCode, String userId) {
        Group group = groupRepository.findByJoinCode(joinCode).get();

        if (group == null) {
            throw new RuntimeException("Group not found");
        }

        if (!group.getMemberIds().contains(userId)) {
            group.getMemberIds().add(userId);
            groupRepository.save(group);
        } else {
            throw new RuntimeException("User is already a member of this group.");
        }
    }

    public void leaveGroup(String joinCode, String userId) {
        Group group = groupRepository.findByJoinCode(joinCode).get();

        if (group == null) {
            throw new RuntimeException("Group not found");
        }

        if (group.getMemberIds().contains(userId)) {
            group.getMemberIds().remove(userId);
            groupRepository.save(group);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void deleteGroup(String id) {
        Optional<Group> group = groupRepository.findById(id);
        if (group.isPresent()) {
            // GCS에서 이미지 삭제
            String imageUrl = group.get().getImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                storage.delete(BlobId.of(bucketName, fileName));
            }

            groupRepository.deleteById(id);
        } else {
            throw new RuntimeException("Group not found");
        }
    }
}

package com.trithon.trithon.controller;

import com.trithon.trithon.domain.Group;
import com.trithon.trithon.domain.dto.GroupRequestDto;
import com.trithon.trithon.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public Group createGroup(GroupRequestDto dto) throws IOException {
        return groupService.createGroup(dto);
    }

    @GetMapping
    public List<Group> getAllGroups() {
        return groupService.getGroups();
    }

    @GetMapping("/{groupId}")
    public Group getGroup(@PathVariable String groupId) {
        return groupService.getGroupById(groupId);
    }

    @PutMapping("/{groupId}/join/{userId}")
    public ResponseEntity<Void> joinGroup(@PathVariable String groupId, @PathVariable String userId) {
        groupService.joinGroup(groupId, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{groupId}/leave/{userId}")
    public ResponseEntity<Void> leaveGroup(@PathVariable String groupId, @PathVariable String userId) {
        groupService.leaveGroup(groupId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable String groupId) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.noContent().build();
    }
}

package com.trithon.trithon.controller;

import com.trithon.trithon.domain.Group;
import com.trithon.trithon.domain.dto.request.GroupRequestDto;
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

    @GetMapping("/userId/{userId}")
    public List<Group> getGroupsByUserId(@PathVariable String userId) {
        return groupService.getGroupsByUserId(userId);
    }

    @GetMapping("/groupId/{groupId}")
    public Group getGroupById(@PathVariable String groupId) {
        return groupService.getGroupById(groupId);
    }

    @GetMapping("/joinCode/{joinCode}")
    public Group getGroupByJoinCode(@PathVariable String joinCode) {
        return groupService.getGroupByJoinCode(joinCode);
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

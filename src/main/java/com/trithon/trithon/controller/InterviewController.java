package com.trithon.trithon.controller;

import com.trithon.trithon.domain.Interview;
import com.trithon.trithon.service.InterviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interviews")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    // 면접 정보 생성
    @PostMapping
    public ResponseEntity<Interview> createInterview(@RequestBody Interview interview) {
        return ResponseEntity.ok(interviewService.createInterview(interview));
    }

    // 특정 유저의 면접 목록 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Interview>> getUserInterviews(@PathVariable String userId) {
        return ResponseEntity.ok(interviewService.getInterviewsByUserId(userId));
    }

    // 특정 인터뷰 조회
    @GetMapping("/{interviewId}")
    public ResponseEntity<Interview> getInterviewById(@PathVariable String interviewId) {
        return ResponseEntity.ok(interviewService.getInterviewById(interviewId));
    }

    // 인터뷰 수정
    @PutMapping("/{interviewId}")
    public ResponseEntity<Interview> updateInterview(@PathVariable String interviewId, @RequestBody Interview updatedInterview) {
        return ResponseEntity.ok(interviewService.updateInterview(interviewId, updatedInterview));
    }

    // 인터뷰 삭제
    @DeleteMapping("/{interviewId}")
    public ResponseEntity<Void> deleteInterview(@PathVariable String interviewId) {
        interviewService.deleteInterview(interviewId);
        return ResponseEntity.noContent().build();
    }

    // 면접 D-Day 계산 API
    @GetMapping("/dday/{interviewId}")
    public ResponseEntity<Integer> getDday(@PathVariable String interviewId) {
        return ResponseEntity.ok(interviewService.getInterviewDDay(interviewId));
    }
}

package com.trithon.trithon.service;

import com.trithon.trithon.domain.Interview;
import com.trithon.trithon.repository.InterviewRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@org.springframework.stereotype.Service
public class InterviewService {

    private final InterviewRepository interviewRepository;

    public InterviewService(InterviewRepository interviewRepository) {
        this.interviewRepository = interviewRepository;
    }

    public Interview createInterview(Interview interview) {
        return interviewRepository.save(interview);
    }

    // 특정 유저의 인터뷰 목록 조회
    public List<Interview> getInterviewsByUserId(String userId) {
        return interviewRepository.findByUserId(userId);
    }

    // 특정 인터뷰 조회
    public Interview getInterviewById(String interviewId) {
        return interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found"));
    }

    // 인터뷰 수정
    public Interview updateInterview(String interviewId, Interview updatedInterview) {
        Interview existingInterview = getInterviewById(interviewId);

        existingInterview.setName(updatedInterview.getName());
        existingInterview.setCategory(updatedInterview.getCategory());
        existingInterview.setTags(updatedInterview.getTags());
        existingInterview.setDate(updatedInterview.getDate());
        existingInterview.setTime(updatedInterview.getTime());
        existingInterview.setQuestDifficulty(updatedInterview.getQuestDifficulty());
        existingInterview.setIncludeTrendQuiz(updatedInterview.isIncludeTrendQuiz());

        return interviewRepository.save(existingInterview);
    }

    // 인터뷰 삭제
    public void deleteInterview(String interviewId) {
        interviewRepository.deleteById(interviewId);
    }

    // D-Day 계산
    public Integer getInterviewDDay(String interviewId) {
        Interview interview = getInterviewById(interviewId);
        long daysBetween = ChronoUnit.DAYS.between(LocalDate.now(), interview.getDate());
        return (int) daysBetween;
    }
}

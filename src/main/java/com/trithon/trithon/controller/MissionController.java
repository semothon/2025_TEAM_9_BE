package com.trithon.trithon.controller;

import com.trithon.trithon.domain.DailyScore;
import com.trithon.trithon.domain.ENUM.MissionType;
import com.trithon.trithon.domain.Mission;
import com.trithon.trithon.domain.dto.response.MissionResponseDto;
import com.trithon.trithon.domain.dto.response.UserDailyRankingResponse;
import com.trithon.trithon.service.MissionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/missions")
public class MissionController {

    private final MissionService missionService;

    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    // ODD & EVEN 미션 한 번에 조회
    @GetMapping("/next")
    public MissionResponseDto getNextMissions(@RequestParam String userId) {
        Mission oddMission = missionService.getNextMissionByType(userId, MissionType.ODD);
        Mission evenMission = missionService.getNextMissionByType(userId, MissionType.EVEN);
        String question = missionService.getRandomQuestion(userId);

        return new MissionResponseDto(oddMission, evenMission, question);
    }

    // 특정 미션 완료
    @PostMapping("/complete")
    public String completeMission(@RequestParam String userId,
                                  @RequestParam int stage,
                                  @RequestParam int index) {
        missionService.completeMission(userId, stage, index);
        return "Mission completed successfully.";
    }

    @PostMapping("/score/{userId}/{interviewId}")
    public ResponseEntity<Void> increaseScore(
            @PathVariable String userId,
            @PathVariable String interviewId,
            @RequestParam int points) {
        missionService.increaseScore(userId, interviewId, points);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/score/{userId}")
    public int getTodayScore(@PathVariable String userId) {
        LocalDate date = LocalDate.now();
        return missionService.getUserScoreByDate(userId, date);
    }

    @GetMapping("/score/{groupId}/daily-scores")
    public ResponseEntity<List<DailyScore>> getGroupMembersTodayScores(@PathVariable String groupId) {
        List<DailyScore> scores = missionService.getTodayScoresByGroup(groupId);
        return ResponseEntity.ok(scores);
    }

    @GetMapping("/groups/{groupId}/daily-ranking")
    public ResponseEntity<List<UserDailyRankingResponse>> getGroupDailyRanking(@PathVariable String groupId) {
        List<UserDailyRankingResponse> ranking = missionService.getTodayRankingByGroup(groupId);
        return ResponseEntity.ok(ranking);
    }
}

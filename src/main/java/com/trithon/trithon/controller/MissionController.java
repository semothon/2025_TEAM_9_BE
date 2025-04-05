package com.trithon.trithon.controller;

import com.trithon.trithon.domain.DailyScore;
import com.trithon.trithon.domain.ENUM.MissionType;
import com.trithon.trithon.domain.Mission;
import com.trithon.trithon.domain.TrendQuiz;
import com.trithon.trithon.domain.dto.response.MissionResponseDto;
import com.trithon.trithon.domain.dto.response.UserDailyRankingResponse;
import com.trithon.trithon.service.MissionService;
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
    public MissionResponseDto getNextMissions(@RequestParam String userId, @RequestParam String interviewId) {
        Mission oddMission = missionService.getNextMissionByType(userId, interviewId, MissionType.ODD);
        Mission evenMission = missionService.getNextMissionByType(userId, interviewId, MissionType.EVEN);
        String question = missionService.getRandomQuestion(userId, interviewId);

        return new MissionResponseDto(oddMission, evenMission, question);
    }

    @GetMapping("/daily")
    public MissionResponseDto getTodayMissions(@RequestParam String userId, @RequestParam String interviewId) {
        return missionService.getTodayMissions(userId, interviewId);
    }

    // 특정 미션 완료
    @PostMapping("/complete")
    public String completeMission(@RequestParam String userId,
                                  @RequestParam String interviewId,
                                  @RequestParam int stage,
                                  @RequestParam int index) {
        missionService.completeMission(userId, interviewId, stage, index);
        return "Mission completed successfully.";
    }

    @GetMapping("/trend-quiz/{interviewId}")
    public TrendQuiz getTrendQuiz(@PathVariable String interviewId) {
        return missionService.getQuizByInterviewCategory(interviewId);
    }

    @PostMapping("/trend-quiz")
    public String completeTrendQuiz(@RequestParam String userId,
                                    @RequestParam String interviewId) {
        missionService.completeTrendQuiz(userId, interviewId);
        return "Trend quiz completed successfully.";
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

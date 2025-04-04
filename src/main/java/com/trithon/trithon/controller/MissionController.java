package com.trithon.trithon.controller;

import com.trithon.trithon.domain.ENUM.MissionType;
import com.trithon.trithon.domain.Mission;
import com.trithon.trithon.domain.dto.response.MissionResponseDto;
import com.trithon.trithon.service.MissionService;
import org.springframework.web.bind.annotation.*;

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
}

package com.trithon.trithon.service;

import com.trithon.trithon.domain.Attendance;
import com.trithon.trithon.domain.ENUM.MissionType;
import com.trithon.trithon.domain.Mission;
import com.trithon.trithon.domain.MissionCompletion;
import com.trithon.trithon.repository.AttendanceRepository;
import com.trithon.trithon.repository.MissionCompletionRepository;
import com.trithon.trithon.repository.MissionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MissionService {

    private final MissionRepository missionRepository;
    private final MissionCompletionRepository missionCompletionRepository;
    private final AttendanceRepository attendanceRepository;

    public MissionService(MissionRepository missionRepository,
                          MissionCompletionRepository missionCompletionRepository,
                          AttendanceRepository attendanceRepository) {
        this.missionRepository = missionRepository;
        this.missionCompletionRepository = missionCompletionRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public Mission getMission(int stage, int index) {
        return missionRepository.findByStageAndIndex(stage, index)
                .orElseThrow(() -> new RuntimeException("Mission not found"));
    }

    public void completeMission(String userId, int stage, int index) {
        Mission mission = getMission(stage, index);
        String missionId = mission.getId();
        LocalDate today = LocalDate.now();

        // 이미 완료한 경우 중복 저장 방지
        Optional<MissionCompletion> existingCompletion = missionCompletionRepository.findByUserIdAndMissionIdAndDate(userId, missionId, today);
        if (existingCompletion.isPresent()) return;

        // MissionCompletion 저장
        MissionCompletion completion = new MissionCompletion(userId, missionId, true, today);
        missionCompletionRepository.save(completion);

        // Attendance 업데이트
        Attendance attendance = attendanceRepository.findByUserIdAndDate(userId, today)
                .orElseGet(() -> new Attendance(userId, today));

        if (mission.getType() == MissionType.ODD) {
            attendance.setOddMissionCompleted(true);
        } else {
            attendance.setEvenMissionCompleted(true);
        }

        attendanceRepository.save(attendance);
    }

    public String getRandomQuestion(String userId) {
        List<MissionCompletion> completions = missionCompletionRepository.findByUserId(userId);
        List<String> completedMissionIds = completions.stream()
                .filter(MissionCompletion::isCompleted)
                .map(MissionCompletion::getMissionId)
                .toList();

        List<Mission> oddMissions = missionRepository.findByType(MissionType.ODD).stream()
                .filter(m -> completedMissionIds.contains(m.getId()))
                .toList();

        if (oddMissions.isEmpty()) return null;

        return oddMissions.get((int)(Math.random() * oddMissions.size())).getQuestion();
    }

    public Mission getNextOddMission(String userId) {
        List<Mission> oddMissions = missionRepository.findByType(MissionType.ODD);
        List<MissionCompletion> completions = missionCompletionRepository.findByUserId(userId);

        List<String> completedIds = completions.stream()
                .map(MissionCompletion::getMissionId)
                .toList();

        return oddMissions.stream()
                .filter(m -> !completedIds.contains(m.getId()))
                .sorted((a, b) -> {
                    if (a.getStage() == b.getStage()) {
                        return Integer.compare(a.getIndex(), b.getIndex());
                    } else {
                        return Integer.compare(a.getStage(), b.getStage());
                    }
                })
                .findFirst()
                .orElse(null);
    }

    public Mission getNextMissionByType(String userId, MissionType type) {
        List<Mission> missions = missionRepository.findByType(type);
        List<MissionCompletion> completions = missionCompletionRepository.findByUserId(userId);

        List<String> completedIds = completions.stream()
                .map(MissionCompletion::getMissionId)
                .toList();

        return missions.stream()
                .filter(m -> !completedIds.contains(m.getId()))
                .sorted((a, b) -> {
                    if (a.getStage() == b.getStage()) {
                        return Integer.compare(a.getIndex(), b.getIndex());
                    } else {
                        return Integer.compare(a.getStage(), b.getStage());
                    }
                })
                .findFirst()
                .orElse(null);
    }
}
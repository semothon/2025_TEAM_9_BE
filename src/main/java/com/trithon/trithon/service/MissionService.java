package com.trithon.trithon.service;

import com.trithon.trithon.domain.*;
import com.trithon.trithon.domain.ENUM.MissionType;
import com.trithon.trithon.domain.dto.response.MissionResponseDto;
import com.trithon.trithon.domain.dto.response.UserDailyRankingResponse;
import com.trithon.trithon.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MissionService {

    private final MissionRepository missionRepository;
    private final MissionCompletionRepository missionCompletionRepository;
    private final AttendanceRepository attendanceRepository;
    private final InterviewRepository interviewRepository;
    private final GroupRepository groupRepository;
    private final DailyScoreRepository dailyScoreRepository;
    private final UserRepository userRepository;
    private final DailyMissionRepository dailyMissionRepository;
//    private final TrendQuizRepository trendQuizRepository;

    public MissionService(MissionRepository missionRepository,
                          MissionCompletionRepository missionCompletionRepository,
                          AttendanceRepository attendanceRepository,
                          InterviewRepository interviewRepository,
                          GroupRepository groupRepository,
                          DailyScoreRepository dailyScoreRepository,
                          UserRepository userRepository,
                          DailyMissionRepository dailyMissionRepository) {
        this.missionRepository = missionRepository;
        this.missionCompletionRepository = missionCompletionRepository;
        this.attendanceRepository = attendanceRepository;
        this.interviewRepository = interviewRepository;
        this.groupRepository = groupRepository;
        this.dailyScoreRepository = dailyScoreRepository;
        this.userRepository = userRepository;
        this.dailyMissionRepository = dailyMissionRepository;
//        this.trendQuizRepository = trendQuizRepository;
    }

    public Mission getMission(int stage, int index) {
        return missionRepository.findByStageAndIndex(stage, index)
                .orElseThrow(() -> new RuntimeException("Mission not found"));
    }

    public void completeMission(String userId, String interviewId, int stage, int index) {
        Mission mission = getMission(stage, index);
        String missionId = mission.getId();
        LocalDate today = LocalDate.now();

        // 이미 완료한 경우 중복 저장 방지
        Optional<MissionCompletion> existingCompletion = missionCompletionRepository.findByUserIdAndInterviewIdAndMissionIdAndDate(userId, interviewId, missionId, today);
        if (existingCompletion.isPresent()) return;

        // MissionCompletion 저장
        MissionCompletion completion = new MissionCompletion(userId, interviewId, missionId, true, today);
        missionCompletionRepository.save(completion);

        // Attendance 업데이트
        Attendance attendance = attendanceRepository.findByUserIdAndInterviewIdAndDate(userId, interviewId, today)
                .orElseGet(() -> new Attendance(userId, interviewId, today));

        if (mission.getType() == MissionType.ODD) {
            attendance.setOddMissionCompleted(true);
        } else {
            attendance.setEvenMissionCompleted(true);
        }

        attendanceRepository.save(attendance);
    }

    public String getRandomQuestion(String userId, String interviewId) {
        List<MissionCompletion> completions = missionCompletionRepository.findByUserIdAndInterviewId(userId, interviewId);
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

    public Mission getNextMissionByType(String userId, String interviewId, MissionType type) {
        List<Mission> missions = missionRepository.findByType(type);
        List<MissionCompletion> completions = missionCompletionRepository.findByUserIdAndInterviewId(userId, interviewId);

        List<String> completedIds = completions.stream()
                .filter(MissionCompletion::isCompleted)
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

    public MissionResponseDto getTodayMissions(String userId, String interviewId) {
        LocalDate today = LocalDate.now();

        DailyMission dailyMission = dailyMissionRepository.findByUserIdAndInterviewIdAndDate(userId, interviewId, today)
                .orElseGet(() -> {
                    Mission odd = getNextMissionByType(userId, interviewId, MissionType.ODD);
                    Mission even = getNextMissionByType(userId, interviewId, MissionType.EVEN);

                    DailyMission newMission = new DailyMission(userId, interviewId, today, odd.getId(), even.getId());
                    return dailyMissionRepository.save(newMission);
                });

        Mission oddMission = missionRepository.findById(dailyMission.getOddMissionId())
                .orElseThrow(() -> new RuntimeException("Odd mission not found"));
        Mission evenMission = missionRepository.findById(dailyMission.getEvenMissionId())
                .orElseThrow(() -> new RuntimeException("Even mission not found"));
        String question = oddMission.getQuestion();

        return new MissionResponseDto(oddMission, evenMission, question);
    }

//    public void completeTrendQuiz(String userId, String interviewId) {
//        LocalDate today = LocalDate.now();
//
//        // Attendance 업데이트
//        Attendance attendance = attendanceRepository.findByUserIdAndInterviewIdAndDate(userId, interviewId, today)
//                .orElseGet(() -> new Attendance(userId, interviewId, today));
//
//        attendance.setTrendQuizCompleted(true);
//        attendanceRepository.save(attendance);
//    }

//    public TrendQuiz getQuizByInterviewCategory(String interviewId) {
//        Interview interview = interviewRepository.findById(interviewId)
//                .orElseThrow(() -> new RuntimeException("Interview not found"));
//
//        List<TrendQuiz> quizzes = trendQuizRepository.findByCategory(interview.getCategory());
//
//        if (quizzes.isEmpty()) {
//            throw new RuntimeException("No trend quiz available for this category");
//        }
//
//        TrendQuiz quiz = quizzes.get(new Random().nextInt(quizzes.size()));
//        return quiz;
//    }

    public void increaseScore(String userId, String interviewId, int points) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        int newScore = interview.getScore() + points;
        interview.setScore(newScore);
        interview.setLevel(calculateLevel(newScore));
        interviewRepository.save(interview);

        List<Group> userGroups = groupRepository.findByMemberIdsContaining(userId);
        for (Group group : userGroups) {
            int newGroupScore = group.getScore() + points;
            group.setScore(newGroupScore);
            group.setLevel(calculateLevel(newGroupScore));
            groupRepository.save(group);
        }

        DailyScore todayScore = dailyScoreRepository.findByUserIdAndDate(userId, LocalDate.now())
                .orElse(new DailyScore(userId, LocalDate.now()));
        todayScore.setScore(todayScore.getScore() + points);
        dailyScoreRepository.save(todayScore);
    }

    public int calculateLevel(int score) {
        if (score >= 300) return 4;
        if (score >= 200) return 3;
        if (score >= 100) return 2;
        if (score >= 50) return 1;
        return 0;
    }

    public int getUserScoreByDate(String userId, LocalDate date) {
        return dailyScoreRepository.findByUserIdAndDate(userId, date)
                .map(DailyScore::getScore)
                .orElse(0);
    }

    public List<DailyScore> getTodayScoresByGroup(String groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        LocalDate today = LocalDate.now();
        List<String> memberIds = group.getMemberIds();

        return dailyScoreRepository.findByUserIdInAndDate(memberIds, today);
    }

    public List<UserDailyRankingResponse> getTodayRankingByGroup(String groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        LocalDate today = LocalDate.now();
        List<String> memberIds = group.getMemberIds();

        // 유저 정보 불러오기
        Map<String, User> userMap = userRepository.findAllById(memberIds).stream()
                .collect(Collectors.toMap(User::getId, user -> user));

        // 오늘 점수 불러오기
        Map<String, Integer> scoreMap = dailyScoreRepository.findByUserIdInAndDate(memberIds, today).stream()
                .collect(Collectors.toMap(DailyScore::getUserId, DailyScore::getScore));

        // 모든 멤버에 대해 점수 채워넣기 (없으면 0점)
        List<Map.Entry<String, Integer>> sortedEntries = memberIds.stream()
                .map(userId -> Map.entry(userId, scoreMap.getOrDefault(userId, 0)))
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue())) // 내림차순 정렬
                .collect(Collectors.toList());

        // 랭킹 처리 (동점 순위 유지)
        List<UserDailyRankingResponse> result = new ArrayList<>();
        int rank = 1;
        int prevScore = -1;
        int actualRank = 1;

        for (int i = 0; i < sortedEntries.size(); i++) {
            String userId = sortedEntries.get(i).getKey();
            int score = sortedEntries.get(i).getValue();

            if (score != prevScore) {
                rank = actualRank;
                prevScore = score;
            }

            User user = userMap.get(userId);
            result.add(new UserDailyRankingResponse(user.getId(), user.getUsername(), score, rank));
            actualRank++;
        }

        return result;
    }
}
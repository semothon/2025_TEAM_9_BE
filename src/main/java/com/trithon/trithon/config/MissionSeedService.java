//package com.trithon.trithon.config;
//
//import com.trithon.trithon.domain.Mission;
//import com.trithon.trithon.domain.ENUM.MissionType;
//
//import com.trithon.trithon.repository.MissionRepository;
//import jakarta.annotation.PostConstruct;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class MissionSeedService {
//
//    private final MissionRepository missionRepository;
//
//    public MissionSeedService(MissionRepository missionRepository) {
//        this.missionRepository = missionRepository;
//    }
//
//    private static final Map<Integer, List<String>> QUESTIONS = Map.of(
//            1, List.of(
//                    "자기소개를 해보세요.",
//                    "지원 동기를 말해보세요.",
//                    "본인의 장점을 말해보세요.",
//                    "최근에 했던 프로젝트에 대해 설명해주세요."
//            ),
//            2, List.of(
//                    "단점은 무엇인가요?",
//                    "협업 경험을 말해주세요.",
//                    "실패 경험과 그것을 어떻게 극복했나요?",
//                    "스트레스를 어떻게 관리하나요?"
//            ),
//            3, List.of(
//                    "가장 기억에 남는 경험은 무엇인가요?",
//                    "목표를 이루기 위해 노력한 경험이 있나요?",
//                    "본인의 가치관은 무엇인가요?",
//                    "1년 후 자신의 모습을 상상해본 적이 있나요?"
//            )
//    );
//
//    @PostConstruct
//    public void seedMissions() {
//        if (missionRepository.count() > 0) return;
//
//        QUESTIONS.forEach((stage, questionList) -> {
//            for (int i = 1; i <= 8; i++) {
//                MissionType type = (i % 2 == 1) ? MissionType.ODD : MissionType.EVEN;
//                String question = (type == MissionType.ODD) ? questionList.get((i - 1) / 2) : null;
//
//                missionRepository.save(new Mission(stage, i, question, type));
//            }
//        });
//
//        System.out.println("Stage 1~3 미션 삽입 완료");
//    }
//}
//package com.trithon.trithon.config;
//
//import com.trithon.trithon.domain.ENUM.InterviewCategory;
//import com.trithon.trithon.domain.TrendQuiz;
//import com.trithon.trithon.repository.TrendQuizRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//public class TrendQuizDataInitializer {
//
//    @Bean
//    CommandLineRunner initTrendQuizzes(TrendQuizRepository trendQuizRepository) {
//        return args -> {
//            // ❗ 중복 저장 방지 (이미 있으면 무시)
//            if (trendQuizRepository.count() > 0) return;
//
//            List<TrendQuiz> quizzes = List.of(
//                    // === VOLUNTEERGROUP === 객관식 5 ===
//                    new TrendQuiz(InterviewCategory.VOLUNTEERGROUP, "UN이 정한 국제 자원봉사자의 날은?", Arrays.asList("1월 15일", "4월 21일", "12월 5일", "6월 14일"), 2, false),
//                    new TrendQuiz(InterviewCategory.VOLUNTEERGROUP, "사회적 기업의 주된 목적은?", Arrays.asList("영리 창출", "환경 파괴", "사회적 가치 창출", "무역 확대"), 2, false),
//                    new TrendQuiz(InterviewCategory.VOLUNTEERGROUP, "자원봉사 활동을 촉진하기 위해 설립된 기관은?", Arrays.asList("환경부", "고용노동부", "한국자원봉사센터", "국세청"), 2, false),
//                    new TrendQuiz(InterviewCategory.VOLUNTEERGROUP, "자원봉사 시간 인증 시스템은?", Arrays.asList("Naver", "1365", "Hancom", "Minwon24"), 1, false),
//                    new TrendQuiz(InterviewCategory.VOLUNTEERGROUP, "2024년 서울시 청소년 봉사 주제는?", Arrays.asList("기후위기", "AI교육", "사회통합", "기술혁신"), 0, false),
//
//                    // === VOLUNTEERGROUP === OX 5 ===
//                    new TrendQuiz(InterviewCategory.VOLUNTEERGROUP, "자원봉사는 법적으로 의무 활동이다.", List.of("X", "O"), 0, true),
//                    new TrendQuiz(InterviewCategory.VOLUNTEERGROUP, "자원봉사는 보수를 받고 수행하는 활동이다.", List.of("X", "O"), 0, true),
//                    new TrendQuiz(InterviewCategory.VOLUNTEERGROUP, "모든 봉사활동은 1365에 등록되지 않아도 된다.", List.of("X", "O"), 1, true),
//                    new TrendQuiz(InterviewCategory.VOLUNTEERGROUP, "자원봉사는 개인의 역량 강화에도 도움이 된다.", List.of("X", "O"), 1, true),
//                    new TrendQuiz(InterviewCategory.VOLUNTEERGROUP, "자원봉사는 공동체 의식을 높인다.", List.of("X", "O"), 1, true),
//
//                    // === SCHOOLCLUB === 객관식 5 ===
//                    new TrendQuiz(InterviewCategory.SCHOOLCLUB, "동아리 활동을 비교과로 인정하는 시스템은?", Arrays.asList("K-MOOC", "마일리지 시스템", "에듀테크", "스마트러닝"), 1, false),
//                    new TrendQuiz(InterviewCategory.SCHOOLCLUB, "캡스톤 디자인의 주요 목적은?", Arrays.asList("학점 채우기", "취업 보장", "문제 해결 기반 프로젝트 수행", "예산 관리"), 2, false),
//                    new TrendQuiz(InterviewCategory.SCHOOLCLUB, "학생자치 동아리의 장점은?", Arrays.asList("리더십 향상", "출석 점수 감점", "시험 대체", "출결 면제"), 0, false),
//                    new TrendQuiz(InterviewCategory.SCHOOLCLUB, "2025년 대학 창업 지원 예산은?", Arrays.asList("1,200억", "2,000억", "500억", "3,500억"), 1, false),
//                    new TrendQuiz(InterviewCategory.SCHOOLCLUB, "SOPT, LIKE LION은 어떤 유형의 동아리인가?", Arrays.asList("봉사 동아리", "종교 동아리", "IT 연합 동아리", "운동 동아리"), 2, false),
//
//                    // === SCHOOLCLUB === OX 5 ===
//                    new TrendQuiz(InterviewCategory.SCHOOLCLUB, "대학생 연합 동아리는 학교 소속이 아니다.", List.of("O", "X"), 0, true),
//                    new TrendQuiz(InterviewCategory.SCHOOLCLUB, "대외활동은 졸업 학점에 반영된다.", List.of("O", "X"), 1, true),
//                    new TrendQuiz(InterviewCategory.SCHOOLCLUB, "전공과 무관한 동아리 활동도 취업에 도움될 수 있다.", List.of("O", "X"), 1, true),
//                    new TrendQuiz(InterviewCategory.SCHOOLCLUB, "AI 동아리는 최근 가장 인기 있는 분야 중 하나다.", List.of("O", "X"), 1, true),
//                    new TrendQuiz(InterviewCategory.SCHOOLCLUB, "동아리 활동은 포트폴리오로 활용될 수 있다.", List.of("O", "X"), 1, true)
//            );
//
//            trendQuizRepository.saveAll(quizzes);
//        };
//    }
//}
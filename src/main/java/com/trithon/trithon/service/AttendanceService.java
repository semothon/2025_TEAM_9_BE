package com.trithon.trithon.service;

import com.trithon.trithon.domain.Attendance;
import com.trithon.trithon.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public boolean isAttendanceCompletedToday(String userId, String interviewId) {
        return attendanceRepository.findByUserIdAndInterviewIdAndDate(userId, interviewId, LocalDate.now())
                .map(Attendance::isAttendanceSatisfied)
                .orElse(false);
    }

    public List<Attendance> getAttendanceHistory(String userId, String interviewId) {
        return attendanceRepository.findByUserIdAndInterviewId(userId, interviewId);
    }
}
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

    public boolean isAttendanceCompletedToday(String userId) {
        return attendanceRepository.findByUserIdAndDate(userId, LocalDate.now())
                .map(Attendance::isAttendanceSatisfied)
                .orElse(false);
    }

    public List<Attendance> getAttendanceHistory(String userId) {
        return attendanceRepository.findByUserId(userId);
    }
}
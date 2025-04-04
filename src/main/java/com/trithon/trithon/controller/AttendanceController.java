package com.trithon.trithon.controller;

import com.trithon.trithon.domain.Attendance;
import com.trithon.trithon.service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendances")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/today")
    public boolean checkTodayAttendance(@RequestParam String userId) {
        return attendanceService.isAttendanceCompletedToday(userId);
    }

    // 출석 기록 전체 조회
    @GetMapping("/history")
    public List<Attendance> getAttendanceHistory(@RequestParam String userId) {
        return attendanceService.getAttendanceHistory(userId);
    }
}

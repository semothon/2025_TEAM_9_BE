package com.trithon.trithon.repository;

import com.trithon.trithon.domain.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends MongoRepository<Attendance, String> {
    Optional<Attendance> findByUserIdAndDate(String userId, LocalDate date);
    List<Attendance> findByUserId(String userId);
}

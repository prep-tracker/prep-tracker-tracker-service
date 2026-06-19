package com.prept.tracker.repository;

import com.prept.tracker.domain.entity.StudySession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface StudySessionRepository extends JpaRepository<StudySession, Long> {

    Page<StudySession> findByUserId(Long userId, Pageable pageable);

    Page<StudySession> findByUserIdAndSessionDateBetween(Long userId, LocalDate start, LocalDate end, Pageable pageable);

    Page<StudySession> findByUserIdAndResourceId(Long userId, Long resourceId, Pageable pageable);

    @Query("SELECT COALESCE(SUM(s.durationMinutes), 0) FROM StudySession s WHERE s.userId = :userId AND s.sessionDate BETWEEN :start AND :end")
    Long sumDurationMinutesByUserIdAndSessionDateBetween(
            @Param("userId") Long userId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end);
}

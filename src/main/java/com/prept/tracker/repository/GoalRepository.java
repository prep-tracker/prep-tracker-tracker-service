package com.prept.tracker.repository;

import com.prept.tracker.domain.entity.Goal;
import com.prept.tracker.domain.enums.GoalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

    Page<Goal> findByUserId(Long userId, Pageable pageable);

    Page<Goal> findByUserIdAndStatus(Long userId, GoalStatus status, Pageable pageable);

    List<Goal> findByTargetDateBeforeAndStatusNot(LocalDate date, GoalStatus status);

    long countByUserId(Long userId);
}

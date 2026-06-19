package com.prept.tracker.repository;

import com.prept.tracker.domain.entity.PreparationPlan;
import com.prept.tracker.domain.enums.PlanPriority;
import com.prept.tracker.domain.enums.PlanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PreparationPlanRepository extends JpaRepository<PreparationPlan, Long> {

    Page<PreparationPlan> findByUserId(Long userId, Pageable pageable);

    Page<PreparationPlan> findByUserIdAndStatus(Long userId, PlanStatus status, Pageable pageable);

    Page<PreparationPlan> findByUserIdAndPriority(Long userId, PlanPriority priority, Pageable pageable);

    List<PreparationPlan> findByTargetCompletionDateBeforeAndStatusNot(LocalDate date, PlanStatus status);
}

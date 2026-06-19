package com.prept.tracker.repository;

import com.prept.tracker.domain.entity.Revision;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RevisionRepository extends JpaRepository<Revision, Long> {

    Page<Revision> findByUserId(Long userId, Pageable pageable);

    Page<Revision> findByUserIdAndNextReminderBefore(Long userId, LocalDate date, Pageable pageable);
}

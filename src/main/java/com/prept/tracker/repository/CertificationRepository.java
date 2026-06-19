package com.prept.tracker.repository;

import com.prept.tracker.domain.entity.Certification;
import com.prept.tracker.domain.enums.CertificationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {

    Page<Certification> findByUserId(Long userId, Pageable pageable);

    Page<Certification> findByUserIdAndStatus(Long userId, CertificationStatus status, Pageable pageable);

    List<Certification> findByTargetDateBeforeAndStatusNot(LocalDate date, CertificationStatus status);

    long countByUserId(Long userId);
}

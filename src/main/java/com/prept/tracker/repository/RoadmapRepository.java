package com.prept.tracker.repository;

import com.prept.tracker.domain.entity.Roadmap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoadmapRepository extends JpaRepository<Roadmap, Long> {

    Page<Roadmap> findByUserId(Long userId, Pageable pageable);

    long countByUserId(Long userId);
}

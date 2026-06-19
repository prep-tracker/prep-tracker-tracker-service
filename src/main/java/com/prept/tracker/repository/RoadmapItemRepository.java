package com.prept.tracker.repository;

import com.prept.tracker.domain.entity.RoadmapItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoadmapItemRepository extends JpaRepository<RoadmapItem, Long> {

    List<RoadmapItem> findByRoadmapId(Long roadmapId);

    List<RoadmapItem> findByRoadmapIdAndCompleted(Long roadmapId, boolean completed);
}

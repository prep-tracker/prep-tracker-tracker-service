package com.prept.tracker.repository;

import com.prept.tracker.domain.entity.InterviewTopic;
import com.prept.tracker.domain.enums.Difficulty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewTopicRepository extends JpaRepository<InterviewTopic, Long> {

    Page<InterviewTopic> findByUserId(Long userId, Pageable pageable);

    Page<InterviewTopic> findByUserIdAndDifficulty(Long userId, Difficulty difficulty, Pageable pageable);
}

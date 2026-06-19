package com.prept.tracker.repository;

import com.prept.tracker.domain.entity.Resource;
import com.prept.tracker.domain.enums.ResourceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    Page<Resource> findByUserId(Long userId, Pageable pageable);

    Page<Resource> findByUserIdAndType(Long userId, ResourceType type, Pageable pageable);

    Page<Resource> findByUserIdAndCategoryId(Long userId, Long categoryId, Pageable pageable);

    Page<Resource> findByUserIdAndTagsContaining(Long userId, String tag, Pageable pageable);

    Page<Resource> findByUserIdAndNameContainingIgnoreCase(Long userId, String name, Pageable pageable);

    long countByUserId(Long userId);
}

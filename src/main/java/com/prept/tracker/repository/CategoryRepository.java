package com.prept.tracker.repository;

import com.prept.tracker.domain.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    Page<Category> findByUserId(Long userId, Pageable pageable);

    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);
}

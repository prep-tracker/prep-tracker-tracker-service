package com.prept.tracker.repository;

import com.prept.tracker.domain.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    Page<Note> findByUserId(Long userId, Pageable pageable);

    Page<Note> findByUserIdAndTagsContaining(Long userId, String tag, Pageable pageable);

    Page<Note> findByUserIdAndTitleContainingIgnoreCase(Long userId, String query, Pageable pageable);
}

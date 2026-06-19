package com.prept.tracker.service;

import com.prept.tracker.domain.entity.Note;
import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.request.NoteRequest;
import com.prept.tracker.dto.response.NoteResponse;
import com.prept.tracker.exception.EntityNotFoundException;
import com.prept.tracker.mapper.NoteMapper;
import com.prept.tracker.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    @Transactional
    public NoteResponse create(Long userId, NoteRequest request) {
        Note note = noteMapper.toEntity(request);
        note.setUserId(userId);
        Note saved = noteRepository.save(note);
        return noteMapper.toResponse(saved);
    }

    @Transactional
    public NoteResponse update(Long userId, Long id, NoteRequest request) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note not found"));
        if (!note.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Note not found");
        }
        noteMapper.updateEntity(request, note);
        Note saved = noteRepository.save(note);
        return noteMapper.toResponse(saved);
    }

    @Transactional
    public void delete(Long userId, Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note not found"));
        if (!note.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Note not found");
        }
        noteRepository.delete(note);
    }

    public NoteResponse getById(Long userId, Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note not found"));
        if (!note.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Note not found");
        }
        return noteMapper.toResponse(note);
    }

    public PageResponse<NoteResponse> getByUser(Long userId, Pageable pageable) {
        Page<Note> page = noteRepository.findByUserId(userId, pageable);
        return toPageResponse(page);
    }

    public PageResponse<NoteResponse> getByUserAndTag(Long userId, String tag, Pageable pageable) {
        Page<Note> page = noteRepository.findByUserIdAndTagsContaining(userId, tag, pageable);
        return toPageResponse(page);
    }

    public PageResponse<NoteResponse> search(Long userId, String query, Pageable pageable) {
        Page<Note> page = noteRepository.findByUserIdAndTitleContainingIgnoreCase(userId, query, pageable);
        return toPageResponse(page);
    }

    private PageResponse<NoteResponse> toPageResponse(Page<Note> page) {
        return PageResponse.<NoteResponse>builder()
                .content(page.getContent().stream().map(noteMapper::toResponse).toList())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}

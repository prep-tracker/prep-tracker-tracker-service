package com.prept.tracker.service;

import com.prept.tracker.domain.entity.Resource;
import com.prept.tracker.domain.entity.StudySession;
import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.request.StudySessionRequest;
import com.prept.tracker.dto.response.StudySessionResponse;
import com.prept.tracker.exception.EntityNotFoundException;
import com.prept.tracker.mapper.StudySessionMapper;
import com.prept.tracker.repository.ResourceRepository;
import com.prept.tracker.repository.StudySessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StudySessionService {

    private final StudySessionRepository studySessionRepository;
    private final StudySessionMapper studySessionMapper;
    private final ResourceRepository resourceRepository;

    @Transactional
    public StudySessionResponse create(Long userId, StudySessionRequest request) {
        StudySession session = studySessionMapper.toEntity(request);
        session.setUserId(userId);
        if (request.getResourceId() != null) {
            Resource resource = resourceRepository.findById(request.getResourceId())
                    .orElseThrow(() -> new EntityNotFoundException("Resource not found"));
            session.setResource(resource);
        }
        StudySession saved = studySessionRepository.save(session);
        return studySessionMapper.toResponse(saved);
    }

    @Transactional
    public StudySessionResponse update(Long userId, Long id, StudySessionRequest request) {
        StudySession session = studySessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Study session not found"));
        if (!session.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Study session not found");
        }
        studySessionMapper.updateEntity(request, session);
        if (request.getResourceId() != null) {
            Resource resource = resourceRepository.findById(request.getResourceId())
                    .orElseThrow(() -> new EntityNotFoundException("Resource not found"));
            session.setResource(resource);
        } else {
            session.setResource(null);
        }
        StudySession saved = studySessionRepository.save(session);
        return studySessionMapper.toResponse(saved);
    }

    @Transactional
    public void delete(Long userId, Long id) {
        StudySession session = studySessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Study session not found"));
        if (!session.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Study session not found");
        }
        studySessionRepository.delete(session);
    }

    public StudySessionResponse getById(Long userId, Long id) {
        StudySession session = studySessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Study session not found"));
        if (!session.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Study session not found");
        }
        return studySessionMapper.toResponse(session);
    }

    public PageResponse<StudySessionResponse> getByUser(Long userId, Pageable pageable) {
        Page<StudySession> page = studySessionRepository.findByUserId(userId, pageable);
        return toPageResponse(page);
    }

    public PageResponse<StudySessionResponse> getByUserAndDateRange(Long userId, LocalDate start, LocalDate end, Pageable pageable) {
        Page<StudySession> page = studySessionRepository.findByUserIdAndSessionDateBetween(userId, start, end, pageable);
        return toPageResponse(page);
    }

    public Integer getTotalHoursForUser(Long userId, LocalDate start, LocalDate end) {
        Long totalMinutes = studySessionRepository.sumDurationMinutesByUserIdAndSessionDateBetween(userId, start, end);
        return totalMinutes != null ? (int) (totalMinutes / 60) : 0;
    }

    private PageResponse<StudySessionResponse> toPageResponse(Page<StudySession> page) {
        return PageResponse.<StudySessionResponse>builder()
                .content(page.getContent().stream().map(studySessionMapper::toResponse).toList())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}

package com.prept.tracker.service;

import com.prept.tracker.domain.entity.Revision;
import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.request.RevisionRequest;
import com.prept.tracker.dto.response.RevisionResponse;
import com.prept.tracker.exception.EntityNotFoundException;
import com.prept.tracker.mapper.RevisionMapper;
import com.prept.tracker.repository.RevisionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RevisionService {

    private final RevisionRepository revisionRepository;
    private final RevisionMapper revisionMapper;

    @Transactional
    public RevisionResponse create(Long userId, RevisionRequest request) {
        Revision revision = revisionMapper.toEntity(request);
        revision.setUserId(userId);
        Revision saved = revisionRepository.save(revision);
        return revisionMapper.toResponse(saved);
    }

    @Transactional
    public RevisionResponse update(Long userId, Long id, RevisionRequest request) {
        Revision revision = revisionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Revision not found"));
        if (!revision.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Revision not found");
        }
        revisionMapper.updateEntity(request, revision);
        Revision saved = revisionRepository.save(revision);
        return revisionMapper.toResponse(saved);
    }

    @Transactional
    public void delete(Long userId, Long id) {
        Revision revision = revisionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Revision not found"));
        if (!revision.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Revision not found");
        }
        revisionRepository.delete(revision);
    }

    public RevisionResponse getById(Long userId, Long id) {
        Revision revision = revisionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Revision not found"));
        if (!revision.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Revision not found");
        }
        return revisionMapper.toResponse(revision);
    }

    public PageResponse<RevisionResponse> getByUser(Long userId, Pageable pageable) {
        Page<Revision> page = revisionRepository.findByUserId(userId, pageable);
        return toPageResponse(page);
    }

    public PageResponse<RevisionResponse> getUpcomingReminders(Long userId, LocalDate date) {
        Page<Revision> page = revisionRepository.findByUserIdAndNextReminderBefore(userId, date, Pageable.unpaged());
        return toPageResponse(page);
    }

    private PageResponse<RevisionResponse> toPageResponse(Page<Revision> page) {
        return PageResponse.<RevisionResponse>builder()
                .content(page.getContent().stream().map(revisionMapper::toResponse).toList())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}

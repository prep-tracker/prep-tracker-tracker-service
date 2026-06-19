package com.prept.tracker.service;

import com.prept.tracker.context.UserContext;
import com.prept.tracker.domain.entity.Roadmap;
import com.prept.tracker.domain.entity.RoadmapItem;
import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.request.RoadmapItemRequest;
import com.prept.tracker.dto.request.RoadmapRequest;
import com.prept.tracker.dto.response.RoadmapResponse;
import com.prept.tracker.exception.EntityNotFoundException;
import com.prept.tracker.mapper.RoadmapMapper;
import com.prept.tracker.repository.RoadmapItemRepository;
import com.prept.tracker.repository.RoadmapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class RoadmapService {

    private final RoadmapRepository roadmapRepository;
    private final RoadmapItemRepository roadmapItemRepository;
    private final RoadmapMapper roadmapMapper;

    @Transactional
    public RoadmapResponse create(Long userId, RoadmapRequest request) {
        validateRoadmapLimit(userId);
        Roadmap roadmap = roadmapMapper.toEntity(request);
        roadmap.setUserId(userId);
        roadmap.setItems(new ArrayList<>());
        Roadmap savedRoadmap = roadmapRepository.save(roadmap);
        if (request.getItems() != null) {
            for (RoadmapItemRequest itemRequest : request.getItems()) {
                RoadmapItem item = roadmapMapper.toItemEntity(itemRequest, savedRoadmap);
                savedRoadmap.getItems().add(item);
            }
        }
        Roadmap saved = roadmapRepository.save(savedRoadmap);
        return roadmapMapper.toResponse(saved);
    }

    @Transactional
    public RoadmapResponse update(Long userId, Long id, RoadmapRequest request) {
        Roadmap roadmap = roadmapRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Roadmap not found"));
        if (!roadmap.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Roadmap not found");
        }
        roadmapMapper.updateEntity(request, roadmap);
        roadmap.getItems().clear();
        if (request.getItems() != null) {
            for (RoadmapItemRequest itemRequest : request.getItems()) {
                RoadmapItem item = roadmapMapper.toItemEntity(itemRequest, roadmap);
                roadmap.getItems().add(item);
            }
        }
        Roadmap saved = roadmapRepository.save(roadmap);
        return roadmapMapper.toResponse(saved);
    }

    @Transactional
    public void delete(Long userId, Long id) {
        Roadmap roadmap = roadmapRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Roadmap not found"));
        if (!roadmap.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Roadmap not found");
        }
        roadmapRepository.delete(roadmap);
    }

    public RoadmapResponse getById(Long userId, Long id) {
        Roadmap roadmap = roadmapRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Roadmap not found"));
        if (!roadmap.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Roadmap not found");
        }
        return roadmapMapper.toResponse(roadmap);
    }

    public PageResponse<RoadmapResponse> getByUser(Long userId, Pageable pageable) {
        Page<Roadmap> page = roadmapRepository.findByUserId(userId, pageable);
        return toPageResponse(page);
    }

    private PageResponse<RoadmapResponse> toPageResponse(Page<Roadmap> page) {
        return PageResponse.<RoadmapResponse>builder()
                .content(page.getContent().stream().map(roadmapMapper::toResponse).toList())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    private void validateRoadmapLimit(Long userId) {
        String plan = UserContext.getPlan();
        if ("FREE".equals(plan)) {
            long count = roadmapRepository.countByUserId(userId);
            if (count >= 2) {
                throw new IllegalStateException("Roadmap limit reached for FREE plan (Max 2 roadmaps). Upgrade to Premium for unlimited roadmaps.");
            }
        }
    }
}

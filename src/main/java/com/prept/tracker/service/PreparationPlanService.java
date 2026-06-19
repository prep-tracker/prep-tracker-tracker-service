package com.prept.tracker.service;

import com.prept.tracker.domain.entity.PreparationPlan;
import com.prept.tracker.domain.entity.Resource;
import com.prept.tracker.domain.enums.PlanStatus;
import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.request.PreparationPlanRequest;
import com.prept.tracker.dto.response.PreparationPlanResponse;
import com.prept.tracker.exception.EntityNotFoundException;
import com.prept.tracker.mapper.PreparationPlanMapper;
import com.prept.tracker.repository.PreparationPlanRepository;
import com.prept.tracker.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PreparationPlanService {

    private final PreparationPlanRepository preparationPlanRepository;
    private final PreparationPlanMapper preparationPlanMapper;
    private final ResourceRepository resourceRepository;

    @Transactional
    public PreparationPlanResponse create(Long userId, PreparationPlanRequest request) {
        PreparationPlan plan = preparationPlanMapper.toEntity(request);
        plan.setUserId(userId);
        if (request.getResourceId() != null) {
            Resource resource = resourceRepository.findById(request.getResourceId())
                    .orElseThrow(() -> new EntityNotFoundException("Resource not found"));
            plan.setResource(resource);
        }
        PreparationPlan saved = preparationPlanRepository.save(plan);
        return preparationPlanMapper.toResponse(saved);
    }

    @Transactional
    public PreparationPlanResponse update(Long userId, Long id, PreparationPlanRequest request) {
        PreparationPlan plan = preparationPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Preparation plan not found"));
        if (!plan.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Preparation plan not found");
        }
        preparationPlanMapper.updateEntity(request, plan);
        if (request.getResourceId() != null) {
            Resource resource = resourceRepository.findById(request.getResourceId())
                    .orElseThrow(() -> new EntityNotFoundException("Resource not found"));
            plan.setResource(resource);
        } else {
            plan.setResource(null);
        }
        PreparationPlan saved = preparationPlanRepository.save(plan);
        return preparationPlanMapper.toResponse(saved);
    }

    @Transactional
    public void delete(Long userId, Long id) {
        PreparationPlan plan = preparationPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Preparation plan not found"));
        if (!plan.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Preparation plan not found");
        }
        preparationPlanRepository.delete(plan);
    }

    public PreparationPlanResponse getById(Long userId, Long id) {
        PreparationPlan plan = preparationPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Preparation plan not found"));
        if (!plan.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Preparation plan not found");
        }
        return preparationPlanMapper.toResponse(plan);
    }

    public PageResponse<PreparationPlanResponse> getByUser(Long userId, Pageable pageable) {
        Page<PreparationPlan> page = preparationPlanRepository.findByUserId(userId, pageable);
        return toPageResponse(page);
    }

    public PageResponse<PreparationPlanResponse> getByUserAndStatus(Long userId, PlanStatus status, Pageable pageable) {
        Page<PreparationPlan> page = preparationPlanRepository.findByUserIdAndStatus(userId, status, pageable);
        return toPageResponse(page);
    }

    public List<PreparationPlanResponse> getOverduePlans(Long userId, LocalDate now) {
        List<PreparationPlan> plans = preparationPlanRepository.findByTargetCompletionDateBeforeAndStatusNot(now, PlanStatus.COMPLETED);
        return plans.stream()
                .filter(p -> p.getUserId().equals(userId))
                .map(preparationPlanMapper::toResponse)
                .toList();
    }

    private PageResponse<PreparationPlanResponse> toPageResponse(Page<PreparationPlan> page) {
        return PageResponse.<PreparationPlanResponse>builder()
                .content(page.getContent().stream().map(preparationPlanMapper::toResponse).toList())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}

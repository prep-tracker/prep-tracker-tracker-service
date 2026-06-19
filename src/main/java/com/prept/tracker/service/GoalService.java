package com.prept.tracker.service;

import com.prept.tracker.context.UserContext;
import com.prept.tracker.domain.entity.Goal;
import com.prept.tracker.domain.enums.GoalStatus;
import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.request.GoalRequest;
import com.prept.tracker.dto.response.GoalResponse;
import com.prept.tracker.exception.EntityNotFoundException;
import com.prept.tracker.mapper.GoalMapper;
import com.prept.tracker.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final GoalMapper goalMapper;

    @Transactional
    public GoalResponse create(Long userId, GoalRequest request) {
        validateGoalLimit(userId);
        Goal goal = goalMapper.toEntity(request);
        goal.setUserId(userId);
        Goal saved = goalRepository.save(goal);
        return goalMapper.toResponse(saved);
    }

    @Transactional
    public GoalResponse update(Long userId, Long id, GoalRequest request) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Goal not found"));
        if (!goal.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Goal not found");
        }
        goalMapper.updateEntity(request, goal);
        Goal saved = goalRepository.save(goal);
        return goalMapper.toResponse(saved);
    }

    @Transactional
    public void delete(Long userId, Long id) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Goal not found"));
        if (!goal.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Goal not found");
        }
        goalRepository.delete(goal);
    }

    public GoalResponse getById(Long userId, Long id) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Goal not found"));
        if (!goal.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Goal not found");
        }
        return goalMapper.toResponse(goal);
    }

    public PageResponse<GoalResponse> getByUser(Long userId, Pageable pageable) {
        Page<Goal> page = goalRepository.findByUserId(userId, pageable);
        return toPageResponse(page);
    }

    public PageResponse<GoalResponse> getByUserAndStatus(Long userId, GoalStatus status, Pageable pageable) {
        Page<Goal> page = goalRepository.findByUserIdAndStatus(userId, status, pageable);
        return toPageResponse(page);
    }

    public List<GoalResponse> getOverdue(Long userId, LocalDate now) {
        List<Goal> goals = goalRepository.findByTargetDateBeforeAndStatusNot(now, GoalStatus.COMPLETED);
        return goals.stream()
                .filter(g -> g.getUserId().equals(userId))
                .map(goalMapper::toResponse)
                .toList();
    }

    private PageResponse<GoalResponse> toPageResponse(Page<Goal> page) {
        return PageResponse.<GoalResponse>builder()
                .content(page.getContent().stream().map(goalMapper::toResponse).toList())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    private void validateGoalLimit(Long userId) {
        String plan = UserContext.getPlan();
        if ("FREE".equals(plan)) {
            long count = goalRepository.countByUserId(userId);
            if (count >= 3) {
                throw new IllegalStateException("Goal limit reached for FREE plan (Max 3 goals). Upgrade to Premium for unlimited goals.");
            }
        }
    }
}

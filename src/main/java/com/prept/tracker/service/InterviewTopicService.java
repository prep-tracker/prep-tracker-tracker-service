package com.prept.tracker.service;

import com.prept.tracker.domain.entity.InterviewTopic;
import com.prept.tracker.domain.enums.Difficulty;
import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.request.InterviewTopicRequest;
import com.prept.tracker.dto.response.InterviewTopicResponse;
import com.prept.tracker.exception.EntityNotFoundException;
import com.prept.tracker.mapper.InterviewTopicMapper;
import com.prept.tracker.repository.InterviewTopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InterviewTopicService {

    private final InterviewTopicRepository interviewTopicRepository;
    private final InterviewTopicMapper interviewTopicMapper;

    @Transactional
    public InterviewTopicResponse create(Long userId, InterviewTopicRequest request) {
        InterviewTopic topic = interviewTopicMapper.toEntity(request);
        topic.setUserId(userId);
        InterviewTopic saved = interviewTopicRepository.save(topic);
        return interviewTopicMapper.toResponse(saved);
    }

    @Transactional
    public InterviewTopicResponse update(Long userId, Long id, InterviewTopicRequest request) {
        InterviewTopic topic = interviewTopicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Interview topic not found"));
        if (!topic.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Interview topic not found");
        }
        interviewTopicMapper.updateEntity(request, topic);
        InterviewTopic saved = interviewTopicRepository.save(topic);
        return interviewTopicMapper.toResponse(saved);
    }

    @Transactional
    public void delete(Long userId, Long id) {
        InterviewTopic topic = interviewTopicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Interview topic not found"));
        if (!topic.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Interview topic not found");
        }
        interviewTopicRepository.delete(topic);
    }

    public InterviewTopicResponse getById(Long userId, Long id) {
        InterviewTopic topic = interviewTopicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Interview topic not found"));
        if (!topic.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Interview topic not found");
        }
        return interviewTopicMapper.toResponse(topic);
    }

    public PageResponse<InterviewTopicResponse> getByUser(Long userId, Pageable pageable) {
        Page<InterviewTopic> page = interviewTopicRepository.findByUserId(userId, pageable);
        return toPageResponse(page);
    }

    public PageResponse<InterviewTopicResponse> getByUserAndDifficulty(Long userId, Difficulty difficulty, Pageable pageable) {
        Page<InterviewTopic> page = interviewTopicRepository.findByUserIdAndDifficulty(userId, difficulty, pageable);
        return toPageResponse(page);
    }

    private PageResponse<InterviewTopicResponse> toPageResponse(Page<InterviewTopic> page) {
        return PageResponse.<InterviewTopicResponse>builder()
                .content(page.getContent().stream().map(interviewTopicMapper::toResponse).toList())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}

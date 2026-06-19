package com.prept.tracker.service;

import com.prept.tracker.context.UserContext;
import com.prept.tracker.domain.entity.Category;
import com.prept.tracker.domain.entity.Resource;
import com.prept.tracker.domain.enums.ResourceType;
import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.request.ResourceRequest;
import com.prept.tracker.dto.response.ResourceResponse;
import com.prept.tracker.exception.EntityNotFoundException;
import com.prept.tracker.mapper.ResourceMapper;
import com.prept.tracker.repository.CategoryRepository;
import com.prept.tracker.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ResourceResponse create(Long userId, ResourceRequest request) {
        validateResourceLimit(userId);
        Resource resource = resourceMapper.toEntity(request);
        resource.setUserId(userId);
        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        } else if (request.getCategoryName() != null && !request.getCategoryName().isBlank()) {
            final String catName = request.getCategoryName().trim();
            category = categoryRepository.findByName(catName)
                    .orElseGet(() -> {
                        Category newCat = Category.builder()
                                .name(catName)
                                .description("Auto-created category: " + catName)
                                .userId(userId)
                                .build();
                        return categoryRepository.save(newCat);
                    });
        }
        resource.setCategory(category);
        Resource saved = resourceRepository.save(resource);
        return resourceMapper.toResponse(saved);
    }

    @Transactional
    public ResourceResponse update(Long userId, Long id, ResourceRequest request) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resource not found"));
        if (!resource.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Resource not found");
        }
        resourceMapper.updateEntity(request, resource);
        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        } else if (request.getCategoryName() != null && !request.getCategoryName().isBlank()) {
            final String catName = request.getCategoryName().trim();
            category = categoryRepository.findByName(catName)
                    .orElseGet(() -> {
                        Category newCat = Category.builder()
                                .name(catName)
                                .description("Auto-created category: " + catName)
                                .userId(userId)
                                .build();
                        return categoryRepository.save(newCat);
                    });
        }
        resource.setCategory(category);
        Resource saved = resourceRepository.save(resource);
        return resourceMapper.toResponse(saved);
    }

    @Transactional
    public void delete(Long userId, Long id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resource not found"));
        if (!resource.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Resource not found");
        }
        resourceRepository.delete(resource);
    }

    public ResourceResponse getById(Long userId, Long id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resource not found"));
        if (!resource.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Resource not found");
        }
        return resourceMapper.toResponse(resource);
    }

    public PageResponse<ResourceResponse> getByUser(Long userId, Pageable pageable) {
        Page<Resource> page = resourceRepository.findByUserId(userId, pageable);
        return toPageResponse(page);
    }

    public PageResponse<ResourceResponse> getByUserAndType(Long userId, ResourceType type, Pageable pageable) {
        Page<Resource> page = resourceRepository.findByUserIdAndType(userId, type, pageable);
        return toPageResponse(page);
    }

    public PageResponse<ResourceResponse> getByUserAndCategory(Long userId, Long categoryId, Pageable pageable) {
        Page<Resource> page = resourceRepository.findByUserIdAndCategoryId(userId, categoryId, pageable);
        return toPageResponse(page);
    }

    public PageResponse<ResourceResponse> search(Long userId, String query, Pageable pageable) {
        Page<Resource> page = resourceRepository.findByUserIdAndNameContainingIgnoreCase(userId, query, pageable);
        return toPageResponse(page);
    }

    private PageResponse<ResourceResponse> toPageResponse(Page<Resource> page) {
        return PageResponse.<ResourceResponse>builder()
                .content(page.getContent().stream().map(resourceMapper::toResponse).toList())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    private void validateResourceLimit(Long userId) {
        String plan = UserContext.getPlan();
        if ("FREE".equals(plan)) {
            long count = resourceRepository.countByUserId(userId);
            if (count >= 3) {
                throw new IllegalStateException("Resource limit reached for FREE plan (Max 3 resources). Upgrade to Premium for unlimited resources.");
            }
        }
    }
}

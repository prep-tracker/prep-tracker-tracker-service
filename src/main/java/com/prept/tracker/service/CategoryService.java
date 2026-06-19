package com.prept.tracker.service;

import com.prept.tracker.domain.entity.Category;
import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.request.CategoryRequest;
import com.prept.tracker.dto.response.CategoryResponse;
import com.prept.tracker.exception.EntityNotFoundException;
import com.prept.tracker.mapper.CategoryMapper;
import com.prept.tracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryResponse create(Long userId, CategoryRequest request) {
        Category category = categoryMapper.toEntity(request);
        category.setUserId(userId);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    @Transactional
    public CategoryResponse update(Long userId, Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        if (!category.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Category not found");
        }
        categoryMapper.updateEntity(request, category);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    @Transactional
    public void delete(Long userId, Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        if (!category.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Category not found");
        }
        categoryRepository.delete(category);
    }

    public CategoryResponse getById(Long userId, Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        if (!category.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Category not found");
        }
        return categoryMapper.toResponse(category);
    }

    public PageResponse<CategoryResponse> getByUser(Long userId, Pageable pageable) {
        Page<Category> page = categoryRepository.findByUserId(userId, pageable);
        return toPageResponse(page);
    }

    private PageResponse<CategoryResponse> toPageResponse(Page<Category> page) {
        return PageResponse.<CategoryResponse>builder()
                .content(page.getContent().stream().map(categoryMapper::toResponse).toList())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}

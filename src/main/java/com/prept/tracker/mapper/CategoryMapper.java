package com.prept.tracker.mapper;

import com.prept.tracker.domain.entity.Category;
import com.prept.tracker.dto.request.CategoryRequest;
import com.prept.tracker.dto.response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    Category toEntity(CategoryRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    void updateEntity(CategoryRequest request, @MappingTarget Category category);
}

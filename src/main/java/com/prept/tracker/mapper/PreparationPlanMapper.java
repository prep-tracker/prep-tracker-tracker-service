package com.prept.tracker.mapper;

import com.prept.tracker.domain.entity.PreparationPlan;
import com.prept.tracker.dto.request.PreparationPlanRequest;
import com.prept.tracker.dto.response.PreparationPlanResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {ResourceMapper.class})
public interface PreparationPlanMapper {

    PreparationPlanResponse toResponse(PreparationPlan plan);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "resource", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    PreparationPlan toEntity(PreparationPlanRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    void updateEntity(PreparationPlanRequest request, @MappingTarget PreparationPlan plan);
}

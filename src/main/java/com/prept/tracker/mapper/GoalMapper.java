package com.prept.tracker.mapper;

import com.prept.tracker.domain.entity.Goal;
import com.prept.tracker.dto.request.GoalRequest;
import com.prept.tracker.dto.response.GoalResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GoalMapper {

    GoalResponse toResponse(Goal goal);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    Goal toEntity(GoalRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    void updateEntity(GoalRequest request, @MappingTarget Goal goal);
}

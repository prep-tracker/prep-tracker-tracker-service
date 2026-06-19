package com.prept.tracker.mapper;

import com.prept.tracker.domain.entity.Roadmap;
import com.prept.tracker.domain.entity.RoadmapItem;
import com.prept.tracker.dto.request.RoadmapItemRequest;
import com.prept.tracker.dto.request.RoadmapRequest;
import com.prept.tracker.dto.response.RoadmapItemResponse;
import com.prept.tracker.dto.response.RoadmapResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoadmapMapper {

    @Mapping(target = "items", source = "items", qualifiedByName = "toItemResponses")
    RoadmapResponse toResponse(Roadmap roadmap);

    List<RoadmapItemResponse> toItemResponses(List<RoadmapItem> items);

    RoadmapItemResponse toItemResponse(RoadmapItem item);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    Roadmap toEntity(RoadmapRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    void updateEntity(RoadmapRequest request, @MappingTarget Roadmap roadmap);

    @Named("toItemResponses")
    default List<RoadmapItemResponse> mapItems(List<RoadmapItem> items) {
        if (items == null) {
            return null;
        }
        return items.stream()
                .map(this::toItemResponse)
                .collect(Collectors.toList());
    }

    default RoadmapItem toItemEntity(RoadmapItemRequest request, Roadmap roadmap) {
        if (request == null) {
            return null;
        }
        return RoadmapItem.builder()
                .title(request.getTitle())
                .orderIndex(request.getOrderIndex() != null ? request.getOrderIndex() : 0)
                .completed(request.getCompleted() != null ? request.getCompleted() : false)
                .roadmap(roadmap)
                .build();
    }
}

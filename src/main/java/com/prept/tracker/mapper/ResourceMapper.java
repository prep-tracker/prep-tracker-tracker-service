package com.prept.tracker.mapper;

import com.prept.tracker.domain.entity.Resource;
import com.prept.tracker.dto.request.ResourceRequest;
import com.prept.tracker.dto.response.ResourceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ResourceMapper {

    ResourceResponse toResponse(Resource resource);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    Resource toEntity(ResourceRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    void updateEntity(ResourceRequest request, @MappingTarget Resource resource);
}

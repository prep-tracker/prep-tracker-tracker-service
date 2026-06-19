package com.prept.tracker.mapper;

import com.prept.tracker.domain.entity.Revision;
import com.prept.tracker.dto.request.RevisionRequest;
import com.prept.tracker.dto.response.RevisionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RevisionMapper {

    RevisionResponse toResponse(Revision revision);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    Revision toEntity(RevisionRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    void updateEntity(RevisionRequest request, @MappingTarget Revision revision);
}

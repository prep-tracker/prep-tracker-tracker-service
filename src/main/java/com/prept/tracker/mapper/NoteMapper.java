package com.prept.tracker.mapper;

import com.prept.tracker.domain.entity.Note;
import com.prept.tracker.dto.request.NoteRequest;
import com.prept.tracker.dto.response.NoteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    NoteResponse toResponse(Note note);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    Note toEntity(NoteRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    void updateEntity(NoteRequest request, @MappingTarget Note note);
}

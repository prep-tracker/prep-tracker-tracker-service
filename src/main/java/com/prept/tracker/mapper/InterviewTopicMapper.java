package com.prept.tracker.mapper;

import com.prept.tracker.domain.entity.InterviewTopic;
import com.prept.tracker.dto.request.InterviewTopicRequest;
import com.prept.tracker.dto.response.InterviewTopicResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InterviewTopicMapper {

    InterviewTopicResponse toResponse(InterviewTopic topic);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    InterviewTopic toEntity(InterviewTopicRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    void updateEntity(InterviewTopicRequest request, @MappingTarget InterviewTopic topic);
}

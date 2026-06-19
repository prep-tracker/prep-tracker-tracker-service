package com.prept.tracker.mapper;

import com.prept.tracker.domain.entity.Certification;
import com.prept.tracker.dto.request.CertificationRequest;
import com.prept.tracker.dto.response.CertificationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CertificationMapper {

    CertificationResponse toResponse(Certification certification);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    Certification toEntity(CertificationRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    void updateEntity(CertificationRequest request, @MappingTarget Certification certification);
}

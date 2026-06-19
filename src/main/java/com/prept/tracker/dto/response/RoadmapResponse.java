package com.prept.tracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoadmapResponse {

    private Long id;
    private String title;
    private String description;
    private List<RoadmapItemResponse> items;
    private Instant createdAt;
    private Instant modifiedAt;
}
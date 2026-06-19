package com.prept.tracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoadmapItemResponse {

    private Long id;
    private String title;
    private Integer orderIndex;
    private Boolean completed;
}
package com.prept.tracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoadmapRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    private List<RoadmapItemRequest> items;
}
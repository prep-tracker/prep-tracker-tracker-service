package com.prept.tracker.dto.response;

import com.prept.tracker.domain.enums.Difficulty;
import com.prept.tracker.domain.enums.ResourceType;
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
public class ResourceResponse {

    private Long id;
    private String name;
    private ResourceType type;
    private String url;
    private String author;
    private Integer estimatedHours;
    private Difficulty difficulty;
    private List<String> tags;
    private String notes;
    private CategoryResponse category;
    private Instant createdAt;
    private Instant modifiedAt;
}
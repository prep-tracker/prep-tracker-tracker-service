package com.prept.tracker.dto.request;

import com.prept.tracker.domain.enums.Difficulty;
import com.prept.tracker.domain.enums.ResourceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Type is required")
    private ResourceType type;

    private String url;
    private String author;
    private Integer estimatedHours;
    private Difficulty difficulty;
    private List<String> tags;
    private String notes;
    private Long categoryId;
    private String categoryName;
}
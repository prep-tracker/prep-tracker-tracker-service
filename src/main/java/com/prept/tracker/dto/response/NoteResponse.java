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
public class NoteResponse {

    private Long id;
    private String title;
    private String content;
    private List<String> tags;
    private String attachmentUrl;
    private Instant createdAt;
    private Instant modifiedAt;
}
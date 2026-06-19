package com.prept.tracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private Integer totalStudyHoursWeek;
    private Long activePlansCount;
    private Long certificationsCount;
    private Long goalsCount;
    private List<CertificationResponse> upcomingCertifications;
    private List<StudySessionResponse> recentStudySessions;
    private Long unreadNotificationsCount;
    private Integer learningStreak;
}

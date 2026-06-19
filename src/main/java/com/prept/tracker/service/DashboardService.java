package com.prept.tracker.service;

import com.prept.tracker.domain.enums.GoalStatus;
import com.prept.tracker.domain.enums.PlanStatus;
import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.response.CertificationResponse;
import com.prept.tracker.dto.response.DashboardResponse;
import com.prept.tracker.dto.response.StudySessionResponse;
import com.prept.tracker.repository.CertificationRepository;
import com.prept.tracker.repository.GoalRepository;
import com.prept.tracker.repository.PreparationPlanRepository;
import com.prept.tracker.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final PreparationPlanRepository preparationPlanRepository;
    private final CertificationRepository certificationRepository;
    private final GoalRepository goalRepository;
    private final ResourceRepository resourceRepository;
    private final StudySessionService studySessionService;
    private final CertificationService certificationService;
    private final RestTemplate restTemplate;
    private final DemoDataSeederService demoDataSeederService;

    public DashboardResponse getDashboardData(Long userId) {
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate weekEnd = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        long activePlansCount = preparationPlanRepository.findByUserIdAndStatus(userId, PlanStatus.IN_PROGRESS, Pageable.unpaged()).getTotalElements();
        long certificationsCount = certificationRepository.findByUserId(userId, Pageable.unpaged()).getTotalElements();
        long goalsCount = goalRepository.findByUserIdAndStatus(userId, GoalStatus.ACTIVE, Pageable.unpaged()).getTotalElements();

        // Auto-seed demo data if the user has absolutely no goals, certifications, or resources
        if (goalsCount == 0 && activePlansCount == 0 && certificationsCount == 0) {
            long resourcesCount = resourceRepository.countByUserId(userId);
            if (resourcesCount == 0) {
                demoDataSeederService.seedDemoData(userId);
                // Re-fetch counts
                activePlansCount = preparationPlanRepository.findByUserIdAndStatus(userId, PlanStatus.IN_PROGRESS, Pageable.unpaged()).getTotalElements();
                certificationsCount = certificationRepository.findByUserId(userId, Pageable.unpaged()).getTotalElements();
                goalsCount = goalRepository.findByUserIdAndStatus(userId, GoalStatus.ACTIVE, Pageable.unpaged()).getTotalElements();
            }
        }

        Integer totalStudyHoursWeek = studySessionService.getTotalHoursForUser(userId, weekStart, weekEnd);

        List<CertificationResponse> upcomingCertifications = certificationService.getUpcoming(userId, today.plusMonths(3));

        Pageable recentSessionsPageable = PageRequest.of(0, 5, Sort.by("sessionDate").descending());
        PageResponse<StudySessionResponse> recentSessionsPage = studySessionService.getByUser(userId, recentSessionsPageable);
        List<StudySessionResponse> recentStudySessions = recentSessionsPage.getContent() != null ? recentSessionsPage.getContent() : Collections.emptyList();

        long unreadNotificationsCount = 0;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-User-Id", String.valueOf(userId));
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<Long> response = restTemplate.exchange(
                "http://notification-service/api/v1/notifications/count/unread",
                HttpMethod.GET,
                requestEntity,
                Long.class
            );
            if (response.getBody() != null) {
                unreadNotificationsCount = response.getBody();
            }
        } catch (Exception e) {
            // fallback to 0
        }

        Integer learningStreak = 0;

        return DashboardResponse.builder()
                .totalStudyHoursWeek(totalStudyHoursWeek)
                .activePlansCount(activePlansCount)
                .certificationsCount(certificationsCount)
                .goalsCount(goalsCount)
                .upcomingCertifications(upcomingCertifications)
                .recentStudySessions(recentStudySessions)
                .unreadNotificationsCount(unreadNotificationsCount)
                .learningStreak(learningStreak)
                .build();
    }
}

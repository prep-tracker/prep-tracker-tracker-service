package com.prept.tracker.service;

import com.prept.tracker.context.UserContext;
import com.prept.tracker.domain.entity.Certification;
import com.prept.tracker.domain.enums.CertificationStatus;
import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.request.CertificationRequest;
import com.prept.tracker.dto.response.CertificationResponse;
import com.prept.tracker.exception.EntityNotFoundException;
import com.prept.tracker.mapper.CertificationMapper;
import com.prept.tracker.repository.CertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final CertificationRepository certificationRepository;
    private final CertificationMapper certificationMapper;

    @Transactional
    public CertificationResponse create(Long userId, CertificationRequest request) {
        validateCertificationLimit(userId);
        Certification certification = certificationMapper.toEntity(request);
        certification.setUserId(userId);
        Certification saved = certificationRepository.save(certification);
        return certificationMapper.toResponse(saved);
    }

    @Transactional
    public CertificationResponse update(Long userId, Long id, CertificationRequest request) {
        Certification certification = certificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Certification not found"));
        if (!certification.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Certification not found");
        }
        certificationMapper.updateEntity(request, certification);
        Certification saved = certificationRepository.save(certification);
        return certificationMapper.toResponse(saved);
    }

    @Transactional
    public void delete(Long userId, Long id) {
        Certification certification = certificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Certification not found"));
        if (!certification.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Certification not found");
        }
        certificationRepository.delete(certification);
    }

    public CertificationResponse getById(Long userId, Long id) {
        Certification certification = certificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Certification not found"));
        if (!certification.getUserId().equals(userId)) {
            throw new EntityNotFoundException("Certification not found");
        }
        return certificationMapper.toResponse(certification);
    }

    public PageResponse<CertificationResponse> getByUser(Long userId, Pageable pageable) {
        Page<Certification> page = certificationRepository.findByUserId(userId, pageable);
        return toPageResponse(page);
    }

    public PageResponse<CertificationResponse> getByUserAndStatus(Long userId, CertificationStatus status, Pageable pageable) {
        Page<Certification> page = certificationRepository.findByUserIdAndStatus(userId, status, pageable);
        return toPageResponse(page);
    }

    public List<CertificationResponse> getUpcoming(Long userId, LocalDate beforeDate) {
        List<Certification> certifications = certificationRepository.findByTargetDateBeforeAndStatusNot(beforeDate, CertificationStatus.PASSED);
        return certifications.stream()
                .filter(c -> c.getUserId().equals(userId))
                .map(certificationMapper::toResponse)
                .toList();
    }

    private PageResponse<CertificationResponse> toPageResponse(Page<Certification> page) {
        return PageResponse.<CertificationResponse>builder()
                .content(page.getContent().stream().map(certificationMapper::toResponse).toList())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    private void validateCertificationLimit(Long userId) {
        String plan = UserContext.getPlan();
        if ("FREE".equals(plan)) {
            long count = certificationRepository.countByUserId(userId);
            if (count >= 1) {
                throw new IllegalStateException("Certification limit reached for FREE plan (Max 1 certification). Upgrade to Premium for up to 5 certifications.");
            }
        } else if ("PREMIUM".equals(plan)) {
            long count = certificationRepository.countByUserId(userId);
            if (count >= 5) {
                throw new IllegalStateException("Certification limit reached for PREMIUM plan (Max 5 certifications). Upgrade to Pro for unlimited certifications.");
            }
        }
    }
}

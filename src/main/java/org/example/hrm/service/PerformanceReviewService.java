package org.example.hrm.service;

import org.example.hrm.dto.ReviewDto;
import org.example.hrm.model.PerformanceReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface PerformanceReviewService {
    PerformanceReview create(ReviewDto dto, Authentication authentication);
    PerformanceReview update(Long id, ReviewDto dto);
    PerformanceReview getById(Long id);
    Page<PerformanceReview> search(Long employeeId, Pageable pageable, Authentication authentication);
    Page<PerformanceReview> getAll(Pageable pageable);
    void delete(Long id);
}

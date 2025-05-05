package org.example.hrm.controller;

import org.example.hrm.dto.ReviewDto;
import org.example.hrm.dto.response.ResponseFactory;
import org.example.hrm.dto.response.ReviewResponse;
import org.example.hrm.model.PerformanceReview;
import org.example.hrm.service.PerformanceReviewService;
import org.example.hrm.util.CommonUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class PerformanceReviewController {
    private final PerformanceReviewService performanceReviewService;

    public PerformanceReviewController(PerformanceReviewService performanceReviewService) {
        this.performanceReviewService = performanceReviewService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReviewDto dto, Authentication authentication) {
        CommonUtils.validateAuthentication(authentication);
        PerformanceReview performanceReview = performanceReviewService.create(dto, authentication);
        return ResponseFactory.success(performanceReview);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) Long employeeId,
                                    Authentication authentication,
                                    Pageable pageable) {
        CommonUtils.validateAuthentication(authentication);
        Page<ReviewResponse> page = performanceReviewService.search(employeeId, pageable, authentication);
        return ResponseFactory.paginationSuccess(page, pageable);
    }

    @GetMapping("/all")
    public ResponseEntity<?> search(Pageable pageable) {
        Page<ReviewResponse> page = performanceReviewService.getAll(pageable);
        return ResponseFactory.paginationSuccess(page, pageable);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        PerformanceReview performanceReview = performanceReviewService.getById(id);
        return ResponseFactory.success(performanceReview);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        performanceReviewService.delete(id);
        return ResponseFactory.success("Xóa thành công");
    }

}

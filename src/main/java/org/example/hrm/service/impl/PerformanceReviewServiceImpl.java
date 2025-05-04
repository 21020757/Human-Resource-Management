package org.example.hrm.service.impl;

import org.example.hrm.dto.ReviewDto;
import org.example.hrm.model.Employee;
import org.example.hrm.model.PerformanceReview;
import org.example.hrm.repository.PerformanceReviewRepository;
import org.example.hrm.service.EmployeeService;
import org.example.hrm.service.PerformanceReviewService;
import org.example.hrm.util.CommonUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class PerformanceReviewServiceImpl implements PerformanceReviewService {
    private final PerformanceReviewRepository repository;
    private final EmployeeService employeeService;

    public PerformanceReviewServiceImpl(PerformanceReviewRepository repository, EmployeeService employeeService) {
        this.repository = repository;
        this.employeeService = employeeService;
    }


    @Override
    public PerformanceReview create(ReviewDto dto, Authentication authentication) {
        PerformanceReview review = new PerformanceReview();
        CommonUtils.copyPropertiesIgnoreNull(dto, review);
        Employee employee = employeeService.findByEmployeeId(dto.getEmployeeId());
        review.setEmployee(employee);
        review.setReviewer(CommonUtils.getCurrentUser(authentication));
        return repository.save(review);
    }

    @Override
    public PerformanceReview update(Long id, ReviewDto dto) {
        return null;
    }

    @Override
    public PerformanceReview getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Page<PerformanceReview> search(Long employeeId, Pageable pageable, Authentication authentication) {
        return repository.search(employeeId, CommonUtils.getCurrentUser(authentication).getId(), pageable);
    }

    @Override
    public Page<PerformanceReview> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}

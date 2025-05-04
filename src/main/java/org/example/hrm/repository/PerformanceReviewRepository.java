package org.example.hrm.repository;

import org.example.hrm.model.PerformanceReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {
    @Query(value = "SELECT r FROM PerformanceReview r WHERE (:employeeId IS NULL OR r.employee.id = :employeeId) " +
            "AND (:reviewerId IS NULL OR r.reviewer.id = :reviewerId)",
    countQuery = "SELECT r FROM PerformanceReview r WHERE (:employeeId IS NULL OR r.employee.id = :employeeId) " +
            "AND (:reviewerId IS NULL OR r.reviewer.id = :reviewerId)",
    nativeQuery = true)
    Page<PerformanceReview> search(
            @Param("employeeId") Long employeeId,
            @Param("reviewerId") Long reviewerId,
            Pageable pageable);
}

package org.example.hrm.repository;

import org.example.hrm.model.Report;
import org.example.hrm.model.enumeration.RequestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query(value = "SELECT r FROM Report r WHERE ((:keyword IS NULL OR LOWER(r.title) LIKE LOWER(CONCAT('%', :keyword, '%')))" +
            "OR (:keyword IS NULL OR LOWER(r.content) LIKE LOWER(CONCAT('%', :keyword, '%'))))" +
            "AND (:requestType IS NULL OR r.reportType = :requestType)",
    countQuery = "SELECT r FROM Report r WHERE ((:keyword IS NULL OR LOWER(r.title) LIKE LOWER(CONCAT('%', :keyword, '%')))" +
            "OR (:keyword IS NULL OR LOWER(r.content) LIKE LOWER(CONCAT('%', :keyword, '%'))))" +
            "AND (:requestType IS NULL OR r.reportType = :requestType)",
    nativeQuery = true)
    Page<Report> search(
            @Param("keyword") String keyword,
            @Param("requestType") RequestType requestType,
            Pageable pageable);
}

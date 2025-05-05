package org.example.hrm.repository;

import org.example.hrm.model.Report;
import org.example.hrm.model.enumeration.ReportType;
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
            "AND (:reportType IS NULL OR r.reportType = :reportType)",
    countQuery = "SELECT r FROM Report r WHERE ((:keyword IS NULL OR LOWER(r.title) LIKE LOWER(CONCAT('%', :keyword, '%')))" +
            "OR (:keyword IS NULL OR LOWER(r.content) LIKE LOWER(CONCAT('%', :keyword, '%'))))" +
            "AND (:reportType IS NULL OR r.reportType = :reportType)")
    Page<Report> search(
            @Param("keyword") String keyword,
            @Param("reportType") ReportType reportType,
            Pageable pageable);

    @Query(value = "SELECT r FROM Report r WHERE ((:keyword IS NULL OR LOWER(r.title) LIKE LOWER(CONCAT('%', :keyword, '%')))" +
            "OR (:keyword IS NULL OR LOWER(r.content) LIKE LOWER(CONCAT('%', :keyword, '%'))))" +
            "AND (:employeeId IS NULL OR r.reporter.id = :employeeId)" +
            "AND (:reportType IS NULL OR r.reportType = :reportType)",
            countQuery = "SELECT r FROM Report r WHERE ((:keyword IS NULL OR LOWER(r.title) LIKE LOWER(CONCAT('%', :keyword, '%')))" +
                    "OR (:keyword IS NULL OR LOWER(r.content) LIKE LOWER(CONCAT('%', :keyword, '%'))))" +
                    "AND (:employeeId IS NULL OR r.reporter.id = :employeeId)" +
                    "AND (:reportType IS NULL OR r.reportType = :reportType)")
    Page<Report> searchByEmployeeId(
            @Param("employeeId") Long employeeId,
            @Param("keyword") String keyword,
            @Param("reportType") ReportType reportType,
            Pageable pageable);
}

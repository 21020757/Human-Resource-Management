package org.example.hrm.repository;

import org.example.hrm.model.Interview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {
    @Query(value = """
    SELECT * FROM interview 
    WHERE (:interviewerId IS NULL OR interviewer_id = :interviewerId)
      AND (:startDate IS NULL OR date >= :startDate)
      AND (:endDate IS NULL OR date <= :endDate)
    """,
            countQuery = """
    SELECT COUNT(*) FROM interview 
    WHERE (:interviewerId IS NULL OR interviewer_id = :interviewerId)
      AND (:startDate IS NULL OR date >= :startDate)
      AND (:endDate IS NULL OR date <= :endDate)
    """,
            nativeQuery = true)
    Page<Interview> search(@Param("interviewerId") Long interviewerId,
                           @Param("startDate") LocalDate startDate,
                           @Param("endDate") LocalDate endDate,
                           Pageable pageable);
}

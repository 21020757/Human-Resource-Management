package org.example.hrm.repository;

import org.example.hrm.model.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    @Query(value = """
    SELECT DISTINCT c.* FROM candidate c
    LEFT JOIN candidate_interview ci ON c.id = ci.candidate_id
    WHERE (:keyword IS NULL OR LOWER(c.full_name) LIKE LOWER(CONCAT('%', :keyword, '%')))
      AND (
        :hasInterview IS NULL
        OR (:hasInterview = true AND ci.interview_id IS NOT NULL)
        OR (:hasInterview = false AND ci.interview_id IS NULL)
      )
    """,
            countQuery = """
    SELECT COUNT(DISTINCT c.id) FROM candidate c
    LEFT JOIN candidate_interview ci ON c.id = ci.candidate_id
    WHERE (:keyword IS NULL OR LOWER(c.full_name) LIKE LOWER(CONCAT('%', :keyword, '%')))
      AND (
        :hasInterview IS NULL
        OR (:hasInterview = true AND ci.interview_id IS NOT NULL)
        OR (:hasInterview = false AND ci.interview_id IS NULL)
      )
    """,
            nativeQuery = true)
    Page<Candidate> search(@Param("keyword") String keyword,
                           @Param("hasInterview") Boolean hasInterview,
                           Pageable pageable);



    Candidate findByEmail(String email);
    @Query("""
    SELECT c FROM Candidate c
    JOIN c.jobs j
    WHERE c.email = :email AND j.id = :jobId
    """)
    Candidate findByEmailAndJobId(@Param("email") String email, @Param("jobId") Long jobId);


    Set<Candidate> findAllByIdIn(Set<Long> ids);
}

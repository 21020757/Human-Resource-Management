package org.example.hrm.repository;

import org.example.hrm.model.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    @Query(value = """
        SELECT * FROM candidate c
        WHERE MATCH(full_name) AGAINST(:keyword IN BOOLEAN MODE)"""
        , countQuery = """
SELECT * FROM candidate c
        WHERE MATCH(full_name) AGAINST(:keyword IN BOOLEAN MODE)
""", nativeQuery = true
    )
    Page<Candidate> search(@Param("full_name") String keyword, Pageable pageable);
}

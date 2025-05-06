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
        SELECT * FROM candidate c
        WHERE (:keyword IS NULL OR full_name LIKE CONCAT('%', :keyword, '%')
        """,
            countQuery = """
        SELECT COUNT(*) FROM candidate c
        WHERE (:keyword IS NULL OR full_name LIKE CONCAT('%', :keyword, '%')
        """,
            nativeQuery = true
    )
    Page<Candidate> search(@Param("keyword") String keyword, Pageable pageable);


    Candidate findByEmail(String email);

    Set<Candidate> findAllByIdIn(Set<Long> ids);
}
